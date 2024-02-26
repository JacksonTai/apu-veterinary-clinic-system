/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.profile;

import com.fasterxml.jackson.databind.ObjectMapper;
import constant.MakeChecker;
import entity.ClinicUser;
import entity.MakerChecker;
import entity.Vet;
import filter.SessionAuthFilter;
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
import java.util.HashMap;
import java.util.Map;

import static constant.EndpointConstant.UPDATE_PROFILE;
import static constant.EndpointConstant.VIEW_PROFILE;
import static constant.UserRole.VET;
import static constant.i18n.En.ERROR_UPDATING_PROFILE;

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
    private Vet vet;

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        this.clinicUser = (ClinicUser) session.getAttribute("clinicUser");
        if (clinicUser.getUserRole().equals(VET)) {
            this.vet = (Vet) clinicUser;
            request.setAttribute("expertises", expertiseFacade.findAll());
            request.setAttribute("clinicUser", vet);
        } else {
            request.setAttribute("clinicUser", clinicUser);
        }
        request.getRequestDispatcher(UPDATE_PROFILE + ".jsp").forward(request, response);
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

        Map<String, String> errorMessages = new HashMap<>();
        ClinicUserValidator clinicUserValidator = new ClinicUserValidator(clinicUserFacade);
        errorMessages.putAll(ClinicUserValidator.validateEmail(email));
        errorMessages.putAll(ClinicUserValidator.validateFullName(fullName));
        errorMessages.putAll(clinicUserValidator.validateCredentialDetails(clinicUser.getEmail(), password));

        if (!email.equals(clinicUser.getEmail())) {
            errorMessages.putAll(clinicUserValidator.validateDuplicateEmail(email));
        }
        if (!fullName.equals(clinicUser.getFullName())) {
            errorMessages.putAll(clinicUserValidator.validateDuplicateFullName(fullName));
        }
        if (!errorMessages.isEmpty()) {
            errorMessages.forEach(request::setAttribute);
            request.getRequestDispatcher(UPDATE_PROFILE + ".jsp").forward(request, response);
            return;
        }

        if (clinicUser.getUserRole().equals(VET)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String currentVet = objectMapper.writeValueAsString(vet);
                vet.setEmail(email);
                vet.setFullName(fullName);
                String updatedVet = objectMapper.writeValueAsString(vet);

                makerCheckerFacade.create(new MakerChecker(clinicUser.getClinicUserId(), null,
                        MakeChecker.Module.PROFILE.toString(), MakeChecker.ActionType.UPDATE.toString(),
                        currentVet, updatedVet, MakeChecker.Status.PENDING.toString()));

            } catch (Exception e) {
                logger.error(ERROR_UPDATING_PROFILE, e);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ERROR_UPDATING_PROFILE);
            }
        }

//        clinicUser.setEmail(email.toLowerCase());
//        clinicUser.setFullName(fullName);
//        clinicUserFacade.edit(clinicUser);
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
