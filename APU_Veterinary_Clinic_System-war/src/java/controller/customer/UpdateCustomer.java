/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.customer;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
import validator.CustomerValidator;

import static constant.EndpointConstant.*;
import static constant.GlobalConstant.ISO_DATE_FORMAT;
import static constant.i18n.En.INVALID_DATE_OF_BIRTH_FORMAT_MESSAGE;

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

        String fullName = request.getParameter("fullName").trim();
        String phoneNumber = request.getParameter("phoneNumber").trim();
        String email = request.getParameter("email").trim();
        String address = request.getParameter("address").trim();
        String gender = request.getParameter("gender").trim();
        String dateOfBirth = request.getParameter("dateOfBirth").trim();

        Map<String, String> errorMessages = new HashMap<>();
        LocalDate dateOfBirthLocalDate = null;
        try {
            dateOfBirthLocalDate = LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern(ISO_DATE_FORMAT));
        } catch (DateTimeParseException e) {
            errorMessages.put("dateOfBirthError", INVALID_DATE_OF_BIRTH_FORMAT_MESSAGE);
        }

        Customer customer = new Customer(fullName, phoneNumber, email, gender, dateOfBirthLocalDate, address);

        CustomerValidator customerValidator = new CustomerValidator(customerFacade);
        errorMessages.putAll(customerValidator.validateCustomerDetails(customer));

        if (!errorMessages.isEmpty()) {
            errorMessages.forEach(request::setAttribute);
            request.getRequestDispatcher(CREATE_CUSTOMER + ".jsp").forward(request, response);
        } else {
            customer.setEmail(email.toLowerCase());
            customerFacade.edit(customer);
            response.sendRedirect(request.getContextPath() + VIEW_CUSTOMER);
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
