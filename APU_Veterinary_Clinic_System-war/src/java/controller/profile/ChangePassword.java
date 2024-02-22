/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.profile;

import entity.ClinicUser;
import repository.ClinicUserFacade;
import org.mindrot.jbcrypt.BCrypt;
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

import static constant.EndpointConstant.CHANGE_PASSWORD;
import static constant.EndpointConstant.VIEW_PROFILE;
import static constant.i18n.En.*;

/**
 * @author Jackson Tai
 */
@WebServlet(name = "ChangePassword", urlPatterns = {CHANGE_PASSWORD})
public class ChangePassword extends HttpServlet {

    @EJB
    private ClinicUserFacade clinicUserFacade;

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
        request.getRequestDispatcher(CHANGE_PASSWORD + ".jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        ClinicUser clinicUser = (ClinicUser) session.getAttribute("clinicUser");

        String currentPassword = request.getParameter("currentPassword").trim();
        String newPassword = request.getParameter("newPassword").trim();
        String confirmPassword = request.getParameter("confirmPassword").trim();

        ClinicUserValidator clinicUserValidator = new ClinicUserValidator(clinicUserFacade);
        Map<String, String> errorMessages = new HashMap<>();

        errorMessages.putAll(clinicUserValidator.validateCredentialDetails(clinicUser.getEmail(), currentPassword));
        Map<String, String> passwordErrorMessages = ClinicUserValidator.validatePassword(newPassword);
        if (!passwordErrorMessages.isEmpty()) {
            errorMessages.putAll(passwordErrorMessages);
        } else if (newPassword.equals(currentPassword)) {
            errorMessages.put("passwordError", NEW_PASSWORD_SAME_AS_CURRENT_MESSAGE);
        }
        if (confirmPassword.isEmpty()) {
            errorMessages.put("confirmPasswordError", EMPTY_CONFIRM_PASSWORD_MESSAGE);
        } else if (!newPassword.equals(confirmPassword)) {
            errorMessages.put("confirmPasswordError", UNMATCHED_NEW_PASSWORD_MESSAGE);
        }

        if (!errorMessages.isEmpty()) {
            errorMessages.forEach(request::setAttribute);
            request.getRequestDispatcher(CHANGE_PASSWORD + ".jsp").forward(request, response);
            return;
        }

        clinicUser.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
        session.setAttribute("clinicUser", clinicUser);
        clinicUserFacade.edit(clinicUser);
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
