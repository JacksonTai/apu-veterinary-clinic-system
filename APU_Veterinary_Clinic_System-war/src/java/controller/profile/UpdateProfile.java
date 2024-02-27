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
import filter.SessionAuthFilter;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static final Logger logger = LoggerFactory.getLogger(SessionAuthFilter.class);

    @EJB
    private MakerCheckerFacade makerCheckerFacade;

    @EJB
    private ExpertiseFacade expertiseFacade;

    @EJB
    private ClinicUserFacade clinicUserFacade;

    private ClinicUser clinicUser;
    private ClinicUser updatedClinicUser;
    private Vet vet;
    private Vet updatedVet;
    private List<Expertise> expertises = new ArrayList<>();
    private MakerChecker makerChecker;
    private boolean isMakerCheckerExist;
    private ObjectMapper objectMapper = new ObjectMapper();

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

        makerChecker = makerCheckerFacade.findByMakerIdAndModuleAndActionType(clinicUser.getClinicUserId(),
                MakeChecker.Module.PROFILE.toString(), MakeChecker.ActionType.UPDATE.toString());
        request.setAttribute("isPending", isMakerCheckerExist = makerChecker != null);

        try {
            if (isMakerCheckerExist) {
                if (clinicUser.getUserRole().equals(VET)) {
                    updatedVet = objectMapper.readValue(makerChecker.getNewValue(), Vet.class);
                    request.setAttribute("clinicUser", updatedVet);
                    request.setAttribute("expertises", expertises = expertiseFacade.findAll());
                } else {
                    updatedClinicUser = objectMapper.readValue(makerChecker.getNewValue(), ClinicUser.class);
                    request.setAttribute("clinicUser", updatedClinicUser);
                }
            }
            if (!isMakerCheckerExist) {
                if (clinicUser.getUserRole().equals(VET)) {
                    vet = (Vet) clinicUser;
                    request.setAttribute("clinicUser", vet);
                    request.setAttribute("expertises", expertises = expertiseFacade.findAll());
                } else {
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
        request.setAttribute("expertises", expertises);
        request.setAttribute("isPending", isMakerCheckerExist);

        Map<String, String> errorMessages = new HashMap<>();
        ClinicUserValidator clinicUserValidator = new ClinicUserValidator(clinicUserFacade);
        errorMessages.putAll(ClinicUserValidator.validateEmail(email));
        errorMessages.putAll(ClinicUserValidator.validateFullName(fullName));

        boolean emailChanged;
        boolean fullNameChanged;
        if (clinicUser.getUserRole().equals(VET)) {
            emailChanged = !email.equalsIgnoreCase(isMakerCheckerExist ? updatedVet.getEmail()
                    : vet.getEmail());
            fullNameChanged = !fullName.equalsIgnoreCase(isMakerCheckerExist ? updatedVet.getFullName()
                    : vet.getFullName());
        } else {
            emailChanged = !email.equalsIgnoreCase(isMakerCheckerExist ? updatedClinicUser.getEmail()
                    : clinicUser.getEmail());
            fullNameChanged = !fullName.equalsIgnoreCase(isMakerCheckerExist ? updatedClinicUser.getFullName()
                    : clinicUser.getFullName());
        }
        boolean noChanges = !emailChanged && !fullNameChanged;
        if (noChanges) {
            request.setAttribute("noChanges", true);
            request.setAttribute("noChangesMessage", NO_CHANGES_MESSAGE);
            request.getRequestDispatcher(UPDATE_PROFILE + ".jsp").forward(request, response);
            return;
        }

        errorMessages.putAll(clinicUserValidator.validateCredentialDetails(clinicUser.getEmail(), password));
        if (emailChanged) {
            errorMessages.putAll(clinicUserValidator.validateDuplicateEmail(email));
        }
        if (fullNameChanged) {
            errorMessages.putAll(clinicUserValidator.validateDuplicateFullName(fullName));
        }
        if (!errorMessages.isEmpty()) {
            errorMessages.forEach(request::setAttribute);
            request.getRequestDispatcher(UPDATE_PROFILE + ".jsp").forward(request, response);
            return;
        }

        try {
            String currentValue = clinicUser.getUserRole().equals(VET) ? objectMapper.writeValueAsString(vet) :
                    objectMapper.writeValueAsString(clinicUser);

            ClinicUser clinicUser = null;
            Vet vet = null;
            if (this.clinicUser.getUserRole().equals(VET)) {
                vet = SerializationUtils.clone(this.vet);
                vet.setEmail(email);
                vet.setFullName(fullName);
            } else {
                clinicUser = SerializationUtils.clone(this.clinicUser);
                clinicUser.setEmail(email);
                clinicUser.setFullName(fullName);
            }

            String newValue = this.clinicUser.getUserRole().equals(VET) ? objectMapper.writeValueAsString(vet) :
                    objectMapper.writeValueAsString(clinicUser);

            if (isMakerCheckerExist) {
                makerChecker.setNewValue(newValue);
                makerCheckerFacade.edit(makerChecker);
            }

            if (!isMakerCheckerExist) {
                makerCheckerFacade.create(new MakerChecker(this.clinicUser.getClinicUserId(), null,
                        MakeChecker.Module.PROFILE.toString(), MakeChecker.ActionType.UPDATE.toString(), currentValue,
                        newValue, MakeChecker.Status.PENDING.toString()));
            }
        } catch (Exception e) {
            logger.error(ERROR_UPDATING_PROFILE, e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ERROR_UPDATING_PROFILE);
        }
        response.sendRedirect(request.getContextPath() + VIEW_PROFILE + ".jsp");
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
