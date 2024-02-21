/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.staff;

import entity.*;
import org.mindrot.jbcrypt.BCrypt;
import validator.ClinicUserValidator;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static constant.EndpointConstant.*;

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

        String fullName = request.getParameter("fullName").trim();
        String phoneNumber = request.getParameter("phoneNumber").trim();
        String email = request.getParameter("email").trim();
        String password = request.getParameter("password").trim();
        String userType = Vet.class.getSimpleName();

        ClinicUser clinicUser = new ClinicUser(fullName, email, password, phoneNumber, userType);

        ClinicUserValidator clinicUserValidatorValidator = new ClinicUserValidator(clinicUserFacade);
        Map<String, String> errorMessages = clinicUserValidatorValidator.validateClinicUserDetails(clinicUser);

        if (!errorMessages.isEmpty()) {
            errorMessages.forEach(request::setAttribute);
            request.getRequestDispatcher(STAFF_REGISTER + ".jsp").forward(request, response);
        } else {
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            clinicUserFacade.create(new Vet(fullName, email.toLowerCase(), hashedPassword, phoneNumber, userType));
//            clinicUserFacade.create(new ManagingStaff(fullName, email.toLowerCase(), hashedPassword, phoneNumber, userType));
//            clinicUserFacade.create(new Receptionist(fullName, email.toLowerCase(), hashedPassword, phoneNumber, userType));
            request.getSession().setAttribute("clinicUser", clinicUser);
            response.sendRedirect(request.getContextPath() + REGISTRATION_SUCCESS + ".jsp");
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
