/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.staff;

import entity.ClinicUser;
import entity.ClinicUserFacade;
import exception.InvalidLoginException;
import validator.ClinicUserValidator;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

import static constant.EndpointConstant.STAFF_HOME;
import static constant.EndpointConstant.STAFF_LOGIN;

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

        ClinicUserValidator validator = new ClinicUserValidator(clinicUserFacade);
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Map<String, String> errorMessages = validator.validateLogin(email, password);
        try {
            if (!errorMessages.isEmpty()) {
                errorMessages.entrySet().forEach((entry) -> {
                    request.setAttribute(entry.getKey(), entry.getValue());
                });
                request.getRequestDispatcher(STAFF_LOGIN + ".jsp").forward(request, response);
                return;
            }
            ClinicUser clinicUser = validator.validateLoginCredential(email, password);
            HttpSession session = request.getSession();
            session.setAttribute("clinicUser", clinicUser);
            response.sendRedirect(request.getContextPath() + STAFF_HOME);
        } catch (InvalidLoginException e) {
             request.setAttribute("invalidLoginError", e.getMessage());
            request.getRequestDispatcher(STAFF_LOGIN + ".jsp").forward(request, response);
        }
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
