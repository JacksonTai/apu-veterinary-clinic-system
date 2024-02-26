package filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static constant.EndpointConstant.*;

//, PET + "/*", CUSTOMER + "/*", WORKING_ROTA + "/*"
@WebFilter(filterName = "SessionAuthFilter", urlPatterns = {
        STAFF_HOME, PROFILE + "/*", APPOINTMENT + "/*"})
public class SessionAuthFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(SessionAuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpRequest.getSession(false);

        if (session != null && session.getAttribute("clinicUser") != null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            logger.warn("Unauthorized access attempt for request to {} from unauthenticated user. Redirecting...", httpRequest.getRequestURI());
            ((HttpServletResponse) servletResponse).sendRedirect(httpRequest.getContextPath() + STAFF_LOGIN);
        }
    }

    @Override
    public void destroy() {

    }

}
