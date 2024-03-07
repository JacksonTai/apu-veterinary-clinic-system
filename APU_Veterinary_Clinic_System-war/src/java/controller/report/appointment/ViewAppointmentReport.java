/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.report.appointment;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static constant.AppointmentStatus.*;
import static constant.EndpointConstant.VIEW_APPOINTMENT_REPORT;
import static constant.EndpointConstant.VIEW_CUSTOMER_REPORT;

import javax.ejb.EJB;
import repository.AppointmentFacade;

/**
 *
 * @author Jackson Tai
 */
@WebServlet(name = "ViewAppointmentReport", urlPatterns = {VIEW_APPOINTMENT_REPORT})
public class ViewAppointmentReport extends HttpServlet {

    @EJB
    private AppointmentFacade appointmentFacade;
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("totalAppointment", appointmentFacade.count());
        request.setAttribute("totalScheduled", appointmentFacade.getCountByStatus(SCHEDULED));
        request.setAttribute("totalCancelled", appointmentFacade.getCountByStatus(CANCELLED));
        request.setAttribute("totalCompleted", appointmentFacade.getCountByStatus(COMPLETED));
        request.setAttribute("totalOngoing", appointmentFacade.getCountByStatus(ONGOING));
        request.getRequestDispatcher(VIEW_APPOINTMENT_REPORT + ".jsp").forward(request, response);
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
