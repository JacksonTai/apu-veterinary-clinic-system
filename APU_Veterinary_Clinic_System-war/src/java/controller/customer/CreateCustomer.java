/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.customer;

import entity.Customer;
import repository.CustomerFacade;
import util.StringUtil;
import validator.CustomerValidator;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static constant.EndpointConstant.CREATE_CUSTOMER;
import static constant.EndpointConstant.VIEW_CUSTOMER;
import static constant.GlobalConstant.ISO_DATE_FORMAT;

/**
 * @author Jackson Tai
 */
@WebServlet(name = "CreateCustomer", urlPatterns = {CREATE_CUSTOMER})
public class CreateCustomer extends HttpServlet {

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
        request.getRequestDispatcher(CREATE_CUSTOMER + ".jsp").forward(request, response);
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
        String email = request.getParameter("email").trim().toLowerCase();
        String address = request.getParameter("address").trim();
        String gender = request.getParameter("gender").trim();
        String dateOfBirth = request.getParameter("dateOfBirth").trim();

        Customer customer = new Customer(fullName, phoneNumber, email, gender, dateOfBirth, address);

        CustomerValidator customerValidator = new CustomerValidator(customerFacade);
        Map<String, String> errorMessages = new HashMap<>(customerValidator.validateCustomerDetails(customer));

        if (!errorMessages.isEmpty()) {
            errorMessages.forEach(request::setAttribute);
            request.getRequestDispatcher(CREATE_CUSTOMER + ".jsp").forward(request, response);
        } else {
            customer.setDateOfBirth(StringUtil.convertDateFormat(customer.getDateOfBirth(), ISO_DATE_FORMAT));
            customerFacade.create(customer);
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
