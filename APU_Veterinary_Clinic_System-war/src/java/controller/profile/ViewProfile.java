/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.profile;

import constant.UserRole;
import entity.ClinicUser;
import entity.Vet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static constant.EndpointConstant.*;
import javax.ejb.EJB;
import repository.VetFacade;

/**
 *
 * @author Jackson Tai
 */
@WebServlet(name = "ViewProfile", urlPatterns = {VIEW_PROFILE})
public class ViewProfile extends HttpServlet {

    @EJB
    private VetFacade vetFacade;
    
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
        HttpSession session = request.getSession(false);
        ClinicUser clinicUser = (ClinicUser) session.getAttribute("clinicUser");
        request.setAttribute("clinicUser", clinicUser);
        if (clinicUser.getUserRole().equals(UserRole.VET)) {
            Vet vet = vetFacade.find(clinicUser.getClinicUserId());
            System.out.println(vet.getExpertise());
            request.setAttribute("expertises", vet.getExpertise());
        }
        request.getRequestDispatcher(VIEW_PROFILE + ".jsp").forward(request, response);
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
