/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.staff;

import entity.ClinicUser;
import entity.Customer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static constant.EndpointConstant.*;
import static constant.GlobalConstant.ISO_DATE_FORMAT;
import static constant.UserRole.MANAGING_STAFF;
import static constant.UserRole.RECEPTIONIST;
import static constant.UserRole.VET;

import javax.ejb.EJB;

import org.mindrot.jbcrypt.BCrypt;
import repository.ClinicUserFacade;
import util.StringUtil;
import validator.ClinicUserValidator;
import validator.CustomerValidator;

/**
 *
 * @author Jackson Tai
 */
@WebServlet(name = "UpdateStaff", urlPatterns = {UPDATE_STAFF})
public class UpdateStaff extends HttpServlet {

    @EJB
    private ClinicUserFacade clinicUserFacade;

    List<String> roleList = new ArrayList<>(Arrays.asList(VET, RECEPTIONIST, MANAGING_STAFF));
    String formType = "UPDATE";

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("roleList", roleList);
        request.setAttribute("formType", formType);
        ClinicUser staff = clinicUserFacade.find(request.getParameter("id"));
        if (staff == null) {
            response.sendRedirect(request.getContextPath() + VIEW_STAFF + ".jsp");
            return;
        }
        request.setAttribute("staff", staff);
        request.getRequestDispatcher(UPDATE_STAFF + ".jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("roleList", roleList);
        request.setAttribute("formType", formType);

        String id = request.getParameter("id");
        ClinicUser existingStaff = clinicUserFacade.find(id);
        if (existingStaff == null) {
            response.sendRedirect(request.getContextPath() + VIEW_STAFF);
            return;
        }

        String fullName = request.getParameter("fullName").trim();
        String email = request.getParameter("email").trim().toLowerCase();

        ClinicUserValidator clinicUserValidator = new ClinicUserValidator(clinicUserFacade);
        Map<String, String> errorMessages = new HashMap<>();
        if (!email.equals(existingStaff.getEmail())) {
            errorMessages.putAll(CustomerValidator.validateEmail(email));
            errorMessages.putAll(clinicUserValidator.validateDuplicateEmail(email));
        }
        if (!fullName.equals(existingStaff.getFullName())) {
            errorMessages.putAll(CustomerValidator.validateFullName(fullName));
            errorMessages.putAll(clinicUserValidator.validateDuplicateFullName(fullName));
        }

        if (!errorMessages.isEmpty()) {
            errorMessages.forEach(request::setAttribute);
            String redirectUrl = UPDATE_STAFF + ".jsp?id=" + existingStaff.getClinicUserId();
            request.getRequestDispatcher(redirectUrl).forward(request, response);
        } else {
            existingStaff.setEmail(email);
            existingStaff.setFullName(fullName);
            clinicUserFacade.edit(existingStaff);
            response.sendRedirect(request.getContextPath() + VIEW_STAFF + "?id=" + existingStaff.getClinicUserId());
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
