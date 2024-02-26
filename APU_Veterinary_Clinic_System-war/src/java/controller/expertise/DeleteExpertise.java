/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.expertise;

import entity.Expertise;
import repository.ExpertiseFacade;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constant.EndpointConstant.DELETE_EXPERTISE;

/**
 *
 * @author Jackson Tai
 */
@WebServlet(name = "DeleteExpertise", urlPatterns = {DELETE_EXPERTISE})
public class DeleteExpertise extends HttpServlet {

    @EJB
    private ExpertiseFacade expertiseFacade;

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String expertiseId = request.getParameter("id");
        Expertise expertise = expertiseFacade.find(expertiseId);
        if (expertise != null) {
            expertiseFacade.remove(expertise);
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
