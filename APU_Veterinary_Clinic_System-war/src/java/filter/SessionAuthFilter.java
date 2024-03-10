package filter;

import entity.ClinicUser;
import repository.ClinicUserFacade;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

import static constant.EndpointConstant.*;
import static constant.UserRole.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@WebFilter(filterName = "SessionAuthFilter", urlPatterns = "/*")
public class SessionAuthFilter implements Filter {

    ClinicUserFacade clinicUserFacade = lookupClinicUserFacadeBean();
    private Map<String, Set<String>> roleToEndpointMap;
    private List<String> excludedUris;
    private static boolean isLoggedIn = false;

    @Override
    public void init(FilterConfig filterConfig) {
        excludedUris = Arrays.asList(STAFF_LOGIN, STAFF_REGISTER);
        roleToEndpointMap = new HashMap<>();
        roleToEndpointMap.put(VET, new HashSet<>(Arrays.asList(STAFF_HOME, APPOINTMENT, CUSTOMER, PET, PROFILE, STAFF)));
        roleToEndpointMap.put(RECEPTIONIST, new HashSet<>(Arrays.asList(STAFF_HOME, APPOINTMENT, CUSTOMER, PET, PROFILE, STAFF)));
        roleToEndpointMap.put(MANAGING_STAFF, new HashSet<>(Arrays.asList(STAFF_HOME, PROFILE, STAFF, WORKING_ROTA, EXPERTISE)));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpRequest.getSession(false);

        String requestURI = httpRequest.getRequestURI();
        String normalizedURI = requestURI.replace(WAR_ROOT, "");

        ClinicUser clinicUser = null;
        if (session == null) {
            isLoggedIn = false;
        }
        if (session != null) {
            clinicUser = (ClinicUser) session.getAttribute("clinicUser");
            isLoggedIn = clinicUser != null && clinicUserFacade.find(clinicUser.getClinicUserId()) != null;
        }

        // Ignore http requests for assets
        if (normalizedURI.startsWith(ASSET)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (isExcludedURI(normalizedURI)) {
            if (isLoggedIn) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + STAFF_HOME);
                return;
            }
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (isLoggedIn) {
            String userRole = clinicUser.getUserRole();
            if (isAuthorized(normalizedURI, userRole)) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                if (isProtectedURI(normalizedURI)) {
                    httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
                } else {
                    filterChain.doFilter(servletRequest, servletResponse);
                }
            }
        } else {
            if (isProtectedURI(normalizedURI)) {
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    private boolean isAuthorized(String normalizedURI, String userRole) {
        return roleToEndpointMap.get(userRole).stream().anyMatch(endpoint -> normalizedURI.startsWith(endpoint) ||
                normalizedURI.startsWith(LOGOUT));
    }

    private boolean isExcludedURI(String normalizedURI) {
        return excludedUris.stream().anyMatch(excludedUri -> normalizedURI.startsWith(excludedUri) ||
                normalizedURI.equals("/"));
    }

    private boolean isProtectedURI(String normalizedURI) {
        return roleToEndpointMap.values().stream()
                .anyMatch(endpoints -> endpoints.stream().anyMatch(normalizedURI::startsWith));
    }

    @Override
    public void destroy() {

    }

    private ClinicUserFacade lookupClinicUserFacadeBean() {
        try {
            Context c = new InitialContext();
            return (ClinicUserFacade) c.lookup("java:global/APU_Veterinary_Clinic_System/APU_Veterinary_Clinic_System-ejb/ClinicUserFacade!repository.ClinicUserFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
