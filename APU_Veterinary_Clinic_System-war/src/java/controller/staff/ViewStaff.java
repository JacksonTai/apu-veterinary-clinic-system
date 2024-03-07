/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.staff;

import constant.ClinicUserStatus;
import entity.ClinicUser;
import entity.Expertise;
import entity.Vet;
import repository.ClinicUserFacade;
import repository.ManagingStaffFacade;
import repository.ReceptionistFacade;
import repository.VetFacade;
import util.StringUtil;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static constant.EndpointConstant.VIEW_STAFF;
import static constant.EndpointConstant.VIEW_STAFFS;
import static constant.GlobalConstant.DASH;
import static constant.UserRole.VET;
import static constant.i18n.En.RECORD_NOT_FOUND_MESSAGE;
import static util.StringUtil.toTitleCase;

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

        String staffId = request.getParameter("id");

        if (staffId != null && !staffId.isEmpty()) {

            HttpSession session = request.getSession(false);
            ClinicUser loggedInStaff = (ClinicUser) session.getAttribute("clinicUser");
            request.setAttribute("isOwnProfile", loggedInStaff.getClinicUserId().equals(staffId));

            ClinicUser staff = clinicUserFacade.find(staffId);
            if (staff == null || !staff.getStatus().equals(ClinicUserStatus.APPROVED)) {
                request.setAttribute("notFoundMessage", RECORD_NOT_FOUND_MESSAGE);
            } else {
                request.setAttribute("staff", staff);
                if (staff.getUserRole().equals(VET)) {
                    Vet vet = (Vet) staff;
                    List<String> expertises = vet.getExpertises().stream()
                            .map(Expertise::getName)
                            .collect(Collectors.toList());
                    request.setAttribute("expertises", StringUtil.requireNonNullElse(
                            StringUtil.getConcatenatedString(expertises, ","), DASH));
                }
            }

            request.getRequestDispatcher(VIEW_STAFF + ".jsp").forward(request, response);
            return;
        }

        String role = request.getParameter("role");
        LinkedHashMap<String, String> queryParams = new LinkedHashMap<>();
        queryParams.put("userRole", toTitleCase(role == null || role.isEmpty() ? "vet" : role));
        queryParams.put("status", ClinicUserStatus.APPROVED);
        PaginationUtil.applyPagination(PaginationConfig.<ClinicUser>builder()
                .request(request)
                .response(response)
                .entityAttribute("staffs")
                .viewPageEndpoint(VIEW_STAFF + "?role=" + role)
                .viewJspPath(VIEW_STAFFS)
                .facade(clinicUserFacade)
                .namedQuery("ClinicUser.findByUserRoleAndStatus")
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
