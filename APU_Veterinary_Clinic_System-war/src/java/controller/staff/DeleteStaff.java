/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.staff;

import entity.ClinicUser;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static constant.EndpointConstant.DELETE_STAFF;
import javax.ejb.EJB;
import repository.ClinicUserFacade;

/**
 *
 * @author Jackson Tai
 */
@WebServlet(name = "DeleteStaff", urlPatterns = {DELETE_STAFF})
public class DeleteStaff extends HttpServlet {

    @EJB
    private ClinicUserFacade clinicUserFacade;

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String staffId = request.getParameter("id");
        ClinicUser staff = clinicUserFacade.find(staffId);
        if (staff != null) {
            clinicUserFacade.remove(staff);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
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