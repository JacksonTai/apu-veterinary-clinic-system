/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.staff;

import model.staff.CreateStaffResponseModel;
import repository.ClinicUserFacade;
import service.StaffService;
import service.StaffServiceImpl;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static constant.EndpointConstant.REGISTRATION_SUCCESS;
import static constant.EndpointConstant.STAFF_REGISTER;
import static constant.UserRole.RECEPTIONIST;
import static constant.UserRole.VET;
import repository.MakerCheckerFacade;

/**
 * @author Jackson Tai
 */
@WebServlet(name = "Staff Register", urlPatterns = {STAFF_REGISTER})
public class Register extends HttpServlet {

    @EJB
    private MakerCheckerFacade makerCheckerFacade;

    @EJB
    private ClinicUserFacade clinicUserFacade;

    List<String> roleList = new ArrayList<>(Arrays.asList(VET, RECEPTIONIST));
    String formType = "CREATE";

    private StaffService staffService;

    @Override
    public void init() throws ServletException {
        super.init();
        staffService = new StaffServiceImpl(clinicUserFacade, makerCheckerFacade);
    }

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
        request.setAttribute("roleList", roleList);
        request.setAttribute("formType", formType);
        request.getRequestDispatcher(STAFF_REGISTER + ".jsp").forward(request, response);
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

        request.setAttribute("roleList", roleList);
        request.setAttribute("formType", formType);
        String fullName = request.getParameter("fullName").trim();
        String email = request.getParameter("email").trim().toLowerCase();
        String password = request.getParameter("password").trim();
        String userRole = request.getParameter("userRole").trim();

        CreateStaffResponseModel createStaffResponse = staffService.createStaff(fullName, email, password, userRole);

        if (createStaffResponse.getStatusCode() == HttpServletResponse.SC_CREATED) {
            response.sendRedirect(request.getContextPath() + REGISTRATION_SUCCESS);
        } else {
            createStaffResponse.getErrorMessages().forEach(request::setAttribute);
            request.getRequestDispatcher(STAFF_REGISTER + ".jsp").forward(request, response);
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
