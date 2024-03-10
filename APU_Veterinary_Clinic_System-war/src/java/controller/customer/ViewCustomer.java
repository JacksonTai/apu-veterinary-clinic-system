/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.customer;

import entity.ClinicUser;
import entity.Customer;
import repository.CustomerFacade;
import repository.PetFacade;
import util.pagination.PaginationConfig;
import util.pagination.PaginationUtil;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static constant.EndpointConstant.VIEW_CUSTOMER;
import static constant.EndpointConstant.VIEW_CUSTOMERS;
import static constant.UserRole.RECEPTIONIST;
import static constant.i18n.En.CUSTOMER_NOT_FOUND_MESSAGE;

/**
 * @author Jackson Tai
 */
@WebServlet(name = "ViewCustomer", urlPatterns = {VIEW_CUSTOMER})
public class ViewCustomer extends HttpServlet {

    @EJB
    private PetFacade petFacade;

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

        HttpSession session = request.getSession(false);
        ClinicUser clinicUser = (ClinicUser) session.getAttribute("clinicUser");
        String customerId = request.getParameter("id");

        if (customerId != null && !customerId.isEmpty()) {
            Customer customer = customerFacade.find(customerId);
            if (customer == null) {
                request.setAttribute("notFoundMessage", CUSTOMER_NOT_FOUND_MESSAGE);
            } else {
                request.setAttribute("customer", customer);
                request.setAttribute("pets", customer.getPets());
            }
            request.getRequestDispatcher(VIEW_CUSTOMER + ".jsp").forward(request, response);
            return;
        }

        if (clinicUser.getUserRole().equals(RECEPTIONIST)) {
            PaginationUtil.applyPagination(PaginationConfig.<Customer>builder()
                    .request(request)
                    .response(response)
                    .entityAttribute("customers")
                    .viewPageEndpoint(VIEW_CUSTOMER)
                    .viewJspPath(VIEW_CUSTOMERS)
                    .facade(customerFacade)
                    .build());
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
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
