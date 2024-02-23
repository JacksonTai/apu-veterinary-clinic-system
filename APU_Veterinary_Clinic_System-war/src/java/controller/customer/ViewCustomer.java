/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.customer;

import entity.Customer;
import repository.CustomerFacade;
import util.pagination.PaginationConfig;
import util.pagination.PaginationUtil;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constant.EndpointConstant.VIEW_CUSTOMER;
import static constant.EndpointConstant.VIEW_CUSTOMERS;

/**
 * @author Jackson Tai
 */
@WebServlet(name = "ViewCustomer", urlPatterns = {VIEW_CUSTOMER})
public class ViewCustomer extends HttpServlet {

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

        String customerId = request.getParameter("id");
        if (customerId != null && !customerId.isEmpty()) {
            request.setAttribute("customer", customerFacade.find(customerId));
            request.getRequestDispatcher(VIEW_CUSTOMER + ".jsp").forward(request, response);
            return;
        }

        PaginationUtil.applyPagination(request, response, PaginationConfig.<Customer>builder()
                .request(request)
                .response(response)
                .entityAttribute("customers")
                .viewPageEndpoint(VIEW_CUSTOMER)
                .viewJspPath(VIEW_CUSTOMERS)
                .facade(customerFacade)
                .build());
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
