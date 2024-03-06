/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.ClinicUser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static constant.EndpointConstant.SEARCH_CUSTOMER;

import javax.ejb.EJB;

import entity.Customer;
import entity.Pet;
import repository.CustomerFacade;

import static constant.i18n.En.*;
import static util.HttpUtil.sendJsonResponse;

/**
 * @author Jackson Tai
 */
@WebServlet(name = "SearchCustomer", urlPatterns = {SEARCH_CUSTOMER})
@MultipartConfig
public class SearchCustomer extends HttpServlet {

    @EJB
    private CustomerFacade customerFacade;

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
        String input = request.getParameter("customerDetailsAjax").trim();
        if (input.trim().isEmpty()) {
            sendJsonResponse(response, HttpServletResponse.SC_OK,
                    "{\"error\":\"" + EMPTY_SEARCH_INPUT_MESSAGE + "\"}");
            return;
        }

        Optional<Customer> staff = customerFacade.findByIdOrFullNameOrEmailOrPhoneNumber(input);
        if (staff.isPresent()) {
            Customer customer = staff.get();
            ObjectMapper mapper = new ObjectMapper();
            String pets = mapper.writeValueAsString(customer.getPets());

            sendJsonResponse(response, HttpServletResponse.SC_OK,
                    "{\"id\":\"" + customer.getCustomerId() +
                            "\",\"fullName\":\"" + customer.getFullName() +
                            "\",\"pets\":" + pets + "}");
        } else {
            sendJsonResponse(response, HttpServletResponse.SC_OK,
                    "{\"error\":\"" + CUSTOMER_NOT_FOUND_MESSAGE + "\"}");
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
