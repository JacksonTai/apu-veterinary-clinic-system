/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.customer;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static constant.EndpointConstant.DELETE_CUSTOMER;

import javax.ejb.EJB;

import entity.Customer;
import repository.CustomerFacade;

/**
 * @author Jackson Tai
 */
@WebServlet(name = "DeleteCustomer", urlPatterns = {DELETE_CUSTOMER})
public class DeleteCustomer extends HttpServlet {

    @EJB
    private CustomerFacade customerFacade;

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String customerId = request.getParameter("id");
        Customer customer = customerFacade.find(customerId);

        if (customer != null) {
            customerFacade.remove(customer);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
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
