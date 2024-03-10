/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.report.staff;

import repository.ClinicUserFacade;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constant.ClinicUserStatus.APPROVED;
import static constant.EndpointConstant.VIEW_STAFF_REPORT;
import static constant.UserRole.*;

/**
 * @author Jackson Tai
 */
@WebServlet(name = "ViewStaffReport", urlPatterns = {VIEW_STAFF_REPORT})
public class ViewStaffReport extends HttpServlet {

    @EJB
    private ClinicUserFacade clinicUserFacade;

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
        request.setAttribute("totalStaff", clinicUserFacade.getCountByStatus(APPROVED));
        request.setAttribute("totalVet", clinicUserFacade.getCountByUserRoleAndStatus(VET, APPROVED));
        request.setAttribute("totalReceptionist", clinicUserFacade.getCountByUserRoleAndStatus(RECEPTIONIST, APPROVED));
        request.setAttribute("totalManagingStaff", clinicUserFacade.getCountByUserRoleAndStatus(MANAGING_STAFF, APPROVED));
        request.getRequestDispatcher(VIEW_STAFF_REPORT + ".jsp").forward(request, response);
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
