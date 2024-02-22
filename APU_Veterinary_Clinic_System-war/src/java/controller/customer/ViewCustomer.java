/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static constant.EndpointConstant.VIEW_CUSTOMER;
import static constant.EndpointConstant.VIEW_CUSTOMERS;
import javax.ejb.EJB;

import entity.Customer;
import repository.CustomerFacade;

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

        String pageParam = request.getParameter("page");
        int pageNumber = pageParam != null ? Integer.parseInt(pageParam) : 1;
        int pageSize = 10;

        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = startIndex + pageSize - 1;

        List<Customer> customers = customerFacade.findRange(new int[]{startIndex, endIndex});
        int totalCustomers = customerFacade.count();
        int totalPages = (int) Math.ceil((double) totalCustomers / pageSize);

        boolean previousDisabled = pageNumber == 1;
        boolean nextDisabled = pageNumber == totalPages;

        request.setAttribute("customers", customers);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", pageNumber);
        request.setAttribute("previousDisabled", previousDisabled);
        request.setAttribute("nextDisabled", nextDisabled);
        request.getRequestDispatcher(VIEW_CUSTOMERS + ".jsp").include(request, response);
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
