/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.staff;

import entity.ClinicUser;
import entity.ClinicUserFacade;

import org.mindrot.jbcrypt.BCrypt;
import validation.ClinicUserValidationSequence;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static constant.EndpointConstant.STAFF_HOME;
import static constant.EndpointConstant.STAFF_LOGIN;
import static constant.i18n.En.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * @author Jackson Tai
 */
@WebServlet(name = "Staff Login", urlPatterns = {STAFF_LOGIN})
public class Login extends HttpServlet {

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
        request.getRequestDispatcher(STAFF_LOGIN + ".jsp").forward(request, response);
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

        String email = request.getParameter("email").trim();
        String password = request.getParameter("password").trim();
        ClinicUser clinicUser = new ClinicUser(email, password);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ClinicUser>> violations = validator.validate(clinicUser);

        if (!violations.isEmpty()) {
            violations.forEach((violation) -> {
                String fieldName = violation.getPropertyPath().toString();
                String errorMessage = violation.getMessage();
                System.out.println(fieldName + " " + errorMessage);
                request.setAttribute(fieldName + "Error", errorMessage);
            });
            request.getRequestDispatcher(STAFF_LOGIN + ".jsp").forward(request, response);
            return;
        }

        ClinicUser found = clinicUserFacade.findByEmail(email);
        if (found == null || !BCrypt.checkpw(password, found.getPassword())) {
            request.setAttribute("invalidLoginError", INVALID_LOGIN_MESSAGE);
            request.getRequestDispatcher(STAFF_LOGIN + ".jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("clinicUser", found);
        response.sendRedirect(request.getContextPath() + STAFF_HOME);
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
