/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.expertise;

import entity.Expertise;
import repository.ExpertiseFacade;
import validator.ExpertiseValidator;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static constant.EndpointConstant.CREATE_EXPERTISE;
import static constant.EndpointConstant.VIEW_EXPERTISE;

/**
 *
 * @author Jackson Tai
 */
@WebServlet(name = "CreateExpertise", urlPatterns = {CREATE_EXPERTISE})
public class CreateExpertise extends HttpServlet {

    @EJB
    private ExpertiseFacade expertiseFacade;

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
        request.getRequestDispatcher(CREATE_EXPERTISE + ".jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name").trim();
        Map<String, String> errorMessages = new HashMap<>(ExpertiseValidator.validateName(name));

        ExpertiseValidator expertiseValidator = new ExpertiseValidator(expertiseFacade);
        if (errorMessages.isEmpty()) {
            errorMessages.putAll(expertiseValidator.validateDuplicateName(name));
        }

        if (!errorMessages.isEmpty()) {
            errorMessages.forEach(request::setAttribute);
            request.getRequestDispatcher(CREATE_EXPERTISE + ".jsp").forward(request, response);
        } else {
            expertiseFacade.create(new Expertise(name));
            response.sendRedirect(request.getContextPath() + VIEW_EXPERTISE);
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
