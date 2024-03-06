/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.appointment;

import entity.Appointment;
import entity.Customer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static constant.EndpointConstant.*;
import static constant.i18n.En.PET_NOT_FOUND_MESSAGE;
import static constant.i18n.En.RECORD_NOT_FOUND_MESSAGE;

import javax.ejb.EJB;

import repository.AppointmentFacade;
import validator.AppointmentValidator;
import validator.PetValidator;

/**
 * @author Jackson Tai
 */
@WebServlet(name = "UpdateAppointment", urlPatterns = {UPDATE_APPOINTMENT})
public class UpdateAppointment extends HttpServlet {

    @EJB
    private AppointmentFacade appointmentFacade;

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
        Appointment appointment = appointmentFacade.find(request.getParameter("id"));
        if (appointment == null) {
            response.sendRedirect(request.getContextPath() + VIEW_APPOINTMENT + ".jsp");
            return;
        }
        request.setAttribute("appointment", appointment);
        request.getRequestDispatcher(UPDATE_APPOINTMENT + ".jsp").forward(request, response);
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
        Appointment appointment = appointmentFacade.find(request.getParameter("id"));
        if (appointment == null) {
            request.setAttribute("notFoundMessage", RECORD_NOT_FOUND_MESSAGE);
            response.sendRedirect(request.getContextPath() + VIEW_APPOINTMENT);
            return;
        }
        String status = request.getParameter("appointmentStatus").trim();
        Map<String, String> errorMessages = new HashMap<>(AppointmentValidator.validateAppointmentStatus(status));
        if (!errorMessages.isEmpty()) {
            errorMessages.forEach(request::setAttribute);
            request.getRequestDispatcher(UPDATE_APPOINTMENT + ".jsp").forward(request, response);
            return;
        }
        appointment.setAppointmentStatus(status);
        appointmentFacade.edit(appointment);
        response.sendRedirect(request.getContextPath() + VIEW_APPOINTMENT + "?id=" + appointment.getAppointmentId());
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
