/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.appointment;

import entity.Appointment;
import repository.AppointmentFacade;
import util.pagination.PaginationConfig;
import util.pagination.PaginationUtil;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;

import static constant.AppointmentStatus.SCHEDULED;
import static constant.EndpointConstant.VIEW_APPOINTMENT;
import static constant.EndpointConstant.VIEW_APPOINTMENTS;
import static constant.i18n.En.RECORD_NOT_FOUND_MESSAGE;
import static util.StringUtil.toTitleCase;

/**
 * @author Jackson Tai
 */
@WebServlet(name = "ViewAppointment", urlPatterns = {VIEW_APPOINTMENT})
public class ViewAppointment extends HttpServlet {

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
        String appointmentId = request.getParameter("id");

        if (appointmentId != null && !appointmentId.isEmpty()) {
            Appointment appointment = appointmentFacade.find(appointmentId);
            if (appointment == null) {
                request.setAttribute("notFoundMessage", RECORD_NOT_FOUND_MESSAGE);
            } else {
                request.setAttribute("appointment", appointment);
            }
            request.getRequestDispatcher(VIEW_APPOINTMENT + ".jsp").forward(request, response);
            return;
        }

        String status = request.getParameter("status");
        LinkedHashMap<String, String> queryParams = new LinkedHashMap<>();
        queryParams.put("appointmentStatus", toTitleCase(status == null || status.isEmpty() ? SCHEDULED : status));
        PaginationUtil.applyPagination(PaginationConfig.<Appointment>builder()
                .request(request)
                .response(response)
                .entityAttribute("appointments")
                .viewPageEndpoint(VIEW_APPOINTMENT + "?status=" + status)
                .viewJspPath(VIEW_APPOINTMENTS)
                .facade(appointmentFacade)
                .namedQuery("Appointment.findByStatus")
                .queryParams(queryParams)
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
