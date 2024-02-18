/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.staff;

import entity.ClinicUser;
import entity.ClinicUserFacade;
import entity.Vet;
import org.mindrot.jbcrypt.BCrypt;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static constant.EndpointConstant.*;
import static constant.GlobalConstant.*;
import static constant.i18n.En.*;

/**
 * @author Jackson Tai
 */
@WebServlet(name = "Staff Register", urlPatterns = {STAFF_REGISTER})
public class Register extends HttpServlet {

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
        request.getRequestDispatcher(STAFF_REGISTER + ".jsp").forward(request, response);
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
        response.setContentType("text/html;charset=UTF-8");

        List<String> errorMessages = new ArrayList<>();

        String fullName = request.getParameter("fullName").trim();
        if (fullName.isEmpty()) {
            errorMessages.add(EMPTY_FULL_NAME_MESSAGE);
            request.setAttribute("fullNameError", EMPTY_FULL_NAME_MESSAGE);
        } else if (!fullName.matches(FULL_NAME_REGEX)) {
            errorMessages.add(INVALID_FULL_NAME_MESSAGE);
            request.setAttribute("fullNameError", INVALID_FULL_NAME_MESSAGE);
        }

        String phoneNumber = request.getParameter("phoneNumber").trim();
        if (phoneNumber.isEmpty()) {
            errorMessages.add(EMPTY_PHONE_NUMBER_MESSAGE);
            request.setAttribute("phoneNumberError", EMPTY_PHONE_NUMBER_MESSAGE);
        } else if (!Pattern.matches(MY_PHONE_REGEX, phoneNumber)) {
            errorMessages.add(INVALID_PHONE_NUMBER_MESSAGE);
            request.setAttribute("phoneNumberError", INVALID_PHONE_NUMBER_MESSAGE);
        }

        String email = request.getParameter("email").trim();
        if (email.isEmpty()) {
            errorMessages.add(EMPTY_EMAIL_MESSAGE);
            request.setAttribute("emailError", EMPTY_EMAIL_MESSAGE);
        } else if (!email.matches(STAFF_EMAIL_REGEX)) {
            errorMessages.add(INVALID_STAFF_EMAIL_MESSAGE);
            request.setAttribute("emailError", INVALID_STAFF_EMAIL_MESSAGE);
        }

        String password = request.getParameter("password").trim();
        if (password.isEmpty()) {
            errorMessages.add(EMPTY_PASSWORD_MESSAGE);
            request.setAttribute("passwordError", EMPTY_PASSWORD_MESSAGE);
        } else if (!password.matches(PASSWORD_REGEX)) {
            errorMessages.add(INVALID_PASSWORD_MESSAGE);
            request.setAttribute("passwordError", INVALID_PASSWORD_MESSAGE);
        }

        if (errorMessages.isEmpty()) {
            ClinicUser clinicUser = clinicUserFacade.findByFullName(fullName);
            if (clinicUser != null) {
                errorMessages.add(DUPLICATE_FULL_NAME_MESSAGE);
                request.setAttribute("fullNameError", DUPLICATE_FULL_NAME_MESSAGE);
            }
            clinicUser = clinicUserFacade.findByPhoneNumber(phoneNumber);
            if (clinicUser != null) {
                errorMessages.add(DUPLICATE_PHONE_NUMBER_MESSAGE);
                request.setAttribute("phoneNumberError", DUPLICATE_PHONE_NUMBER_MESSAGE);
            }
            clinicUser = clinicUserFacade.findByEmail(email);
            if (clinicUser != null) {
                errorMessages.add(DUPLICATE_EMAIL_MESSAGE);
                request.setAttribute("emailError", DUPLICATE_EMAIL_MESSAGE);
            }
        }

        if (!errorMessages.isEmpty()) {
            request.getRequestDispatcher(STAFF_REGISTER).forward(request, response);
            return;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        clinicUserFacade.create(new Vet(fullName, email, hashedPassword, phoneNumber));
//        clinicUserFacade.create(new ManagingStaff(fullName, email, hashedPassword, phoneNumber));
//        clinicUserFacade.create(new Receptionist(fullName, email, hashedPassword, phoneNumber));
        response.sendRedirect(request.getContextPath() + REGISTRATION_SUCCESS + ".jsp");
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
