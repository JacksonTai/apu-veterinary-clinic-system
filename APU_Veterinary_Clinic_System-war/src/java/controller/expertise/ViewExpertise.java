/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.expertise;

import entity.Expertise;
import repository.ExpertiseFacade;
import util.pagination.PaginationConfig;
import util.pagination.PaginationUtil;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constant.EndpointConstant.VIEW_EXPERTISE;
import static constant.EndpointConstant.VIEW_EXPERTISES;

/**
 * @author Jackson Tai
 */
@WebServlet(name = "ViewExpertise", urlPatterns = {VIEW_EXPERTISE})
public class ViewExpertise extends HttpServlet {

    @EJB
    private ExpertiseFacade expertiseFacade;

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
        PaginationUtil.applyPagination(request, response, PaginationConfig.<Expertise>builder()
                .request(request)
                .response(response)
                .entityAttribute("expertises")
                .viewPageEndpoint(VIEW_EXPERTISE)
                .viewJspPath(VIEW_EXPERTISES)
                .facade(expertiseFacade)
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
