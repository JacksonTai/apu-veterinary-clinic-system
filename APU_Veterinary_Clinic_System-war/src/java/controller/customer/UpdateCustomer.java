/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.customer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ejb.EJB;

import entity.Customer;
import repository.CustomerFacade;
import util.StringUtil;
import validator.CustomerValidator;

import static constant.EndpointConstant.*;
import static constant.GlobalConstant.*;

/**
 * @author Jackson Tai
 */
@WebServlet(name = "UpdateCustomer", urlPatterns = {UPDATE_CUSTOMER})
public class UpdateCustomer extends HttpServlet {

    @EJB
    private CustomerFacade customerFacade;

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

        Customer customer = customerFacade.find(request.getParameter("id"));
        if (customer == null) {
            response.sendRedirect(request.getContextPath() + VIEW_CUSTOMER + ".jsp");
            return;
        }
        request.setAttribute("customer", customer);
        request.getRequestDispatcher(UPDATE_CUSTOMER + ".jsp").forward(request, response);
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

        String id = request.getParameter("id");
        Customer existingCustomer = customerFacade.find(id);
        if (existingCustomer == null) {
            response.sendRedirect(request.getContextPath() + VIEW_CUSTOMER);
            return;
        }

        String email = request.getParameter("email").trim().toLowerCase();
        String fullName = request.getParameter("fullName").trim();
        String phoneNumber = request.getParameter("phoneNumber").trim();
        String address = request.getParameter("address").trim();
        String dateOfBirth = request.getParameter("dateOfBirth").trim();
        String gender = request.getParameter("gender").trim();

        CustomerValidator customerValidator = new CustomerValidator(customerFacade);
        Map<String, String> errorMessages = new HashMap<>();
        if (!email.equals(existingCustomer.getEmail())) {
            errorMessages.putAll(CustomerValidator.validateEmail(email));
            errorMessages.putAll(customerValidator.validateDuplicateEmail(email));
        }
        if (!fullName.equals(existingCustomer.getFullName())) {
            errorMessages.putAll(CustomerValidator.validateFullName(fullName));
            errorMessages.putAll(customerValidator.validateDuplicateFullName(fullName));
        }
        if (!phoneNumber.equals(existingCustomer.getPhoneNumber())) {
            errorMessages.putAll(CustomerValidator.validatePhoneNumber(phoneNumber));
            errorMessages.putAll(customerValidator.validateDuplicatePhoneNumber(phoneNumber));
        }
        if (!address.equals(existingCustomer.getAddress())) {
            errorMessages.putAll(CustomerValidator.validateAddress(address));
        }
        if (!gender.equals(existingCustomer.getGender())) {
            errorMessages.putAll(CustomerValidator.validateGender(gender));
        }
        if (!dateOfBirth.equals(existingCustomer.getDateOfBirth())) {
            errorMessages.putAll(CustomerValidator.validateDateOfBirth(dateOfBirth));
        }

        if (!errorMessages.isEmpty()) {
            errorMessages.forEach(request::setAttribute);
            String redirectUrl = UPDATE_CUSTOMER + ".jsp?id=" + existingCustomer.getCustomerId();
            request.getRequestDispatcher(redirectUrl).forward(request, response);
        } else {
            existingCustomer.setEmail(email);
            existingCustomer.setFullName(fullName);
            existingCustomer.setPhoneNumber(phoneNumber);
            existingCustomer.setAddress(address);
            existingCustomer.setGender(gender);
            existingCustomer.setDateOfBirth(StringUtil.convertDateFormat(dateOfBirth, ISO_DATE_FORMAT));
            customerFacade.edit(existingCustomer);
            response.sendRedirect(request.getContextPath() + VIEW_CUSTOMER + "?id=" + existingCustomer.getCustomerId());
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
