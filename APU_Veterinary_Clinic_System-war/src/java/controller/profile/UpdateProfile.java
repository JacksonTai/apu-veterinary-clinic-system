/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.profile;

import com.fasterxml.jackson.databind.ObjectMapper;
import constant.MakeChecker;
import entity.ClinicUser;
import entity.Expertise;
import entity.MakerChecker;
import entity.Vet;
import lombok.SneakyThrows;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.ClinicUserFacade;
import repository.ExpertiseFacade;
import repository.MakerCheckerFacade;
import validator.ClinicUserValidator;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

import static constant.EndpointConstant.UPDATE_PROFILE;
import static constant.EndpointConstant.VIEW_PROFILE;
import static constant.UserRole.VET;
import static constant.i18n.En.ERROR_UPDATING_PROFILE;
import static constant.i18n.En.NO_CHANGES_MESSAGE;

/**
 * @author Jackson Tai
 */
@WebServlet(name = "UpdateProfile", urlPatterns = {UPDATE_PROFILE})
public class UpdateProfile extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(UpdateProfile.class);

    @EJB
    private MakerCheckerFacade makerCheckerFacade;

    @EJB
    private ExpertiseFacade expertiseFacade;

    @EJB
    private ClinicUserFacade clinicUserFacade;

    private static MakerChecker mc;
    private static ClinicUser clinicUser;
    private static ClinicUser mcClinicUser;
    private static Vet vet;
    private static Vet mcVet;
    private static boolean pendingMcExist;
    private static boolean isVet;
    private static List<Expertise> expertises = new ArrayList<>();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        clinicUser = (ClinicUser) session.getAttribute("clinicUser");
        clinicUser = clinicUserFacade.find(clinicUser.getClinicUserId());

        isVet = clinicUser.getUserRole().equals(VET);
        mc = makerCheckerFacade.findByMakerIdAndStatusAndModuleAndActionType(clinicUser.getClinicUserId(),
                MakeChecker.Status.PENDING.toString(), MakeChecker.Module.PROFILE.toString(),
                MakeChecker.ActionType.UPDATE.toString());

        request.setAttribute("pendingMcExist", pendingMcExist = mc != null);

        try {
            if (isVet) {
                request.setAttribute("expertises", expertises = expertiseFacade.findAll());
                vet = (Vet) clinicUser;
                if (pendingMcExist) {
                    mcVet = objectMapper.readValue(mc.getNewValue(), Vet.class);
                    request.setAttribute("clinicUser", mcVet);
                    request.setAttribute("selectedExpertises", mcVet.getExpertises());
                }
                if (!pendingMcExist) {
                    request.setAttribute("clinicUser", vet);
                    request.setAttribute("selectedExpertises", vet.getExpertises());
                }
            } else {
                if (pendingMcExist) {
                    mcClinicUser = objectMapper.readValue(mc.getNewValue(), ClinicUser.class);
                    request.setAttribute("clinicUser", mcClinicUser);
                }
                if (!pendingMcExist) {
                    request.setAttribute("clinicUser", clinicUser);
                }
            }
            request.getRequestDispatcher(UPDATE_PROFILE + ".jsp").forward(request, response);
        } catch (Exception e) {
            logger.error(ERROR_UPDATING_PROFILE, e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ERROR_UPDATING_PROFILE);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     *                 594064
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = request.getParameter("fullName").trim();
        String email = request.getParameter("email").trim();
        String password = request.getParameter("password").trim();
        List<Expertise> selectedExpertises = new ArrayList<>();

        request.setAttribute("pendingMcExist", pendingMcExist);
        if (isVet) {
            request.setAttribute("expertises", expertises = expertiseFacade.findAll());

            // Get the selected expertises
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                if (paramName.startsWith("expertise_")) {
                    String expertiseId = paramName.substring("expertise_".length());
                    expertises.stream()
                            .filter(expertise -> expertise.getExpertiseId().equals(expertiseId))
                            .findFirst()
                            .ifPresent(selectedExpertises::add);
                }
            }
            request.setAttribute("selectedExpertises", selectedExpertises);
        }

        Map<String, String> errorMessages = new HashMap<>();
        ClinicUserValidator clinicUserValidator = new ClinicUserValidator(clinicUserFacade);
        errorMessages.putAll(ClinicUserValidator.validateEmail(email));
        errorMessages.putAll(ClinicUserValidator.validateFullName(fullName));

        // Check if the user has made any changes to the actual profile or the one in the maker checker.
        boolean mcChanged = false;
        boolean profileEmailChanged = !email.equalsIgnoreCase(clinicUser.getEmail());
        boolean profileFullNameChanged = !fullName.equalsIgnoreCase(clinicUser.getFullName());
        boolean profileExpertisesChanged = false;
        if (isVet) {
            if (pendingMcExist) {
                mcChanged = !email.equalsIgnoreCase(mcVet.getEmail()) ||
                        !fullName.equalsIgnoreCase(mcVet.getFullName()) ||
                        !(new HashSet<>(selectedExpertises).containsAll(mcVet.getExpertises()) &&
                                new HashSet<>(mcVet.getExpertises()).containsAll(selectedExpertises));
            }
            profileExpertisesChanged = !(new HashSet<>(selectedExpertises).containsAll(vet.getExpertises()) &&
                    new HashSet<>(vet.getExpertises()).containsAll(selectedExpertises));
        } else {
            if (pendingMcExist) {
                mcChanged = !email.equalsIgnoreCase(mcClinicUser.getEmail()) ||
                        !fullName.equalsIgnoreCase(mcClinicUser.getFullName());
            }
        }
        boolean profileChanged = profileEmailChanged || profileFullNameChanged || profileExpertisesChanged;
        if ((!pendingMcExist && !profileChanged) || (pendingMcExist && !mcChanged)) {
            request.setAttribute("noChanges", true);
            request.setAttribute("noChangesMessage", NO_CHANGES_MESSAGE);
            request.getRequestDispatcher(UPDATE_PROFILE + ".jsp").forward(request, response);
            return;
        }

        errorMessages.putAll(clinicUserValidator.validateCredentialDetails(clinicUser.getEmail(), password));
        if (profileEmailChanged) {
            errorMessages.putAll(clinicUserValidator.validateDuplicateEmail(email));
        }
        if (profileFullNameChanged) {
            errorMessages.putAll(clinicUserValidator.validateDuplicateFullName(fullName));
        }
        if (!errorMessages.isEmpty()) {
            errorMessages.forEach(request::setAttribute);
            request.getRequestDispatcher(UPDATE_PROFILE + ".jsp").forward(request, response);
            return;
        }

        try {
            // If the maker checker exist and the user update the profile to the same value, remove the maker checker
            if (pendingMcExist && !profileChanged) {
                makerCheckerFacade.remove(mc);
                response.sendRedirect(request.getContextPath() + VIEW_PROFILE);
                return;
            }

            // Make new object with the new value for maker checker
            Vet newMcVet = null;
            ClinicUser newMcClinicUser = null;
            if (isVet) {
                newMcVet = SerializationUtils.clone(pendingMcExist ? mcVet : vet);
                newMcVet.setEmail(email);
                newMcVet.setFullName(fullName);
                newMcVet.setExpertises(selectedExpertises);
            } else {
                newMcClinicUser = SerializationUtils.clone(pendingMcExist ? mcClinicUser : clinicUser);
                newMcClinicUser.setEmail(email);
                newMcClinicUser.setFullName(fullName);
            }

            // Make current value and new value in JSON format for maker checker
            String currentValue = isVet ? objectMapper.writeValueAsString(pendingMcExist ? mcVet : vet) :
                    objectMapper.writeValueAsString(pendingMcExist ? mcClinicUser : clinicUser);
            String newValue = isVet ? objectMapper.writeValueAsString(newMcVet) :
                    objectMapper.writeValueAsString(newMcClinicUser);

            if (pendingMcExist) {
                mc.setNewValue(newValue);
                makerCheckerFacade.edit(mc);
            }
            if (!pendingMcExist) {
                makerCheckerFacade.create(new MakerChecker(this.clinicUser.getClinicUserId(), null,
                        MakeChecker.Module.PROFILE.toString(), MakeChecker.ActionType.UPDATE.toString(), currentValue,
                        newValue, MakeChecker.Status.PENDING.toString()));
            }
        } catch (Exception e) {
            logger.error(ERROR_UPDATING_PROFILE, e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ERROR_UPDATING_PROFILE);
        }
        response.sendRedirect(request.getContextPath() + VIEW_PROFILE);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
