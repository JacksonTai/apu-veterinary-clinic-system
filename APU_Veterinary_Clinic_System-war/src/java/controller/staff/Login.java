/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.staff;

import entity.ClinicUser;
import entity.ClinicUserFacade;
import org.mindrot.jbcrypt.BCrypt;

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

import static constant.EndpointConstant.STAFF_HOME;
import static constant.EndpointConstant.STAFF_LOGIN;
import static constant.GlobalConstant.STAFF_EMAIL_REGEX;
import static constant.i18n.En.*;

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
        List<String> errorMessages = new ArrayList<>();

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
        }

        if (!errorMessages.isEmpty()) {
            request.getRequestDispatcher(STAFF_LOGIN + ".jsp").forward(request, response);
            return;
        }

        ClinicUser found = clinicUserFacade.findByEmail(email);
        if (found == null || !BCrypt.checkpw(password, found.getPassword())) {
            errorMessages.add(INVALID_LOGIN_MESSAGE);
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
