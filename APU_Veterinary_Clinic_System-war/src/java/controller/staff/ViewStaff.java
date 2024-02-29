/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.staff;

import entity.ClinicUser;
import entity.ManagingStaff;
import entity.Receptionist;
import entity.Vet;
import util.pagination.PaginationConfig;
import util.pagination.PaginationUtil;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static constant.EndpointConstant.VIEW_STAFF;
import static constant.EndpointConstant.VIEW_STAFFS;
import static constant.i18n.En.RECORD_NOT_FOUND_MESSAGE;

import javax.ejb.EJB;

import repository.ClinicUserFacade;
import repository.ManagingStaffFacade;
import repository.ReceptionistFacade;
import repository.VetFacade;

/**
 * @author Jackson Tai
 */
@WebServlet(name = "ViewStaff", urlPatterns = {VIEW_STAFF})
public class ViewStaff extends HttpServlet {

    @EJB
    private VetFacade vetFacade;

    @EJB
    private ReceptionistFacade receptionistFacade;

    @EJB
    private ManagingStaffFacade managingStaffFacade;

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

        HttpSession session = request.getSession(false);
        ClinicUser clinicUser = (ClinicUser) session.getAttribute("clinicUser");

        String staffId = request.getParameter("id");

        if (staffId != null && !staffId.isEmpty()) {
            ClinicUser staff = clinicUserFacade.find(staffId);
            request.setAttribute("isOwnProfile", clinicUser.getClinicUserId().equals(staffId));
            if (staff == null) {
                request.setAttribute("notFoundMessage", RECORD_NOT_FOUND_MESSAGE);
            } else {
                request.setAttribute("managingStaff", staff);
            }
            request.getRequestDispatcher(VIEW_STAFF + ".jsp").forward(request, response);
            return;
        }

        String role = request.getParameter("role");
        if (role == null || role.isEmpty()) {
            role = "vet";
        }

        if (role.equals("vet")) {
            PaginationUtil.applyPagination(PaginationConfig.<Vet>builder()
                    .request(request)
                    .response(response)
                    .entityAttribute("staffs")
                    .viewPageEndpoint(VIEW_STAFF + "role=" + role)
                    .viewJspPath(VIEW_STAFFS)
                    .facade(vetFacade)
                    .build());
        } else if (role.equals("receptionist")) {
            PaginationUtil.applyPagination(PaginationConfig.<Receptionist>builder()
                    .request(request)
                    .response(response)
                    .entityAttribute("staffs")
                    .viewPageEndpoint(VIEW_STAFF + "role=" + role)
                    .viewJspPath(VIEW_STAFFS)
                    .facade(receptionistFacade)
                    .build());
        } else if (role.equals("managingStaff")) {
            PaginationUtil.applyPagination(PaginationConfig.<ManagingStaff>builder()
                    .request(request)
                    .response(response)
                    .entityAttribute("staffs")
                    .viewPageEndpoint(VIEW_STAFF + "role=" + role)
                    .viewJspPath(VIEW_STAFFS)
                    .facade(managingStaffFacade)
                    .build());
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
