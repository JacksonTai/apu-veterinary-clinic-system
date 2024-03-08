/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.report.customer;

import entity.Customer;
import repository.CustomerFacade;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static constant.EndpointConstant.VIEW_CUSTOMER_REPORT;
import static constant.GlobalConstant.ISO_DATE_FORMAT;
import static util.BigDecimalUtil.round;
import static util.DateUtil.parseDate;

/**
 * @author Jackson Tai
 */
@WebServlet(name = "ViewCustomerReport", urlPatterns = {VIEW_CUSTOMER_REPORT})
public class ViewCustomerReport extends HttpServlet {

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

        List<Customer> customers = customerFacade.findAll();
        long totalCustomers = customerFacade.count();
        long totalMaleCustomer = customerFacade.getCountByGender("Male");
        long totalFemaleCustomer = customerFacade.getCountByGender("Female");
        double maleRatio = (double) totalMaleCustomer / totalCustomers * 100;
        double femaleRatio = (double) totalFemaleCustomer / totalCustomers * 100;
        double averageAge = customers.stream()
                .mapToInt(customer -> {
                    LocalDate birthDate = parseDate(customer.getDateOfBirth(), ISO_DATE_FORMAT);
                    return (int) ChronoUnit.YEARS.between(birthDate, LocalDate.now());
                })
                .average()
                .orElse(0);
        double averagePetOwned = (double) customers.stream()
                .mapToInt(customer -> customer.getPets().size())
                .sum() / totalCustomers;

        request.setAttribute("totalCustomers", totalCustomers);
        request.setAttribute("totalMaleCustomer", totalMaleCustomer);
        request.setAttribute("totalFemaleCustomer", totalFemaleCustomer);
        request.setAttribute("maleRatio", round(BigDecimal.valueOf(maleRatio), 2));
        request.setAttribute("femaleRatio", round(BigDecimal.valueOf(femaleRatio), 2));
        request.setAttribute("averageAge", round(BigDecimal.valueOf(averageAge), 2));
        request.setAttribute("averagePetOwned", round(BigDecimal.valueOf(averagePetOwned), 2));

        request.getRequestDispatcher(VIEW_CUSTOMER_REPORT + ".jsp").forward(request, response);
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
