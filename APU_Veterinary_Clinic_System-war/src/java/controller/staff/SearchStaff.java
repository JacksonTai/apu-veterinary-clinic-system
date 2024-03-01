/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.staff;

import entity.ClinicUser;
import repository.ClinicUserFacade;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import static constant.EndpointConstant.SEARCH_STAFF;
import static constant.i18n.En.EMPTY_SEARCH_INPUT_MESSAGE;
import static constant.i18n.En.STAFF_NOT_FOUND_MESSAGE;

/**
 * @author Jackson Tai
 */
@WebServlet(name = "SearchStaff", urlPatterns = {SEARCH_STAFF})
@MultipartConfig
public class SearchStaff extends HttpServlet {

    @EJB
    private ClinicUserFacade clinicUserFacade;

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

        String input = request.getParameter("searchInput").trim();
        if (input.trim().isEmpty()) {
            sendJsonResponse(response, HttpServletResponse.SC_OK,
                    "{\"error\":\"" + EMPTY_SEARCH_INPUT_MESSAGE + "\"}");
            return;
        }

        Optional<ClinicUser> staff = clinicUserFacade.findByIdOrFullNameOrEmail(input);
        if (staff.isPresent()) {
            ClinicUser user = staff.get();
            sendJsonResponse(response, HttpServletResponse.SC_OK,
                    String.format("{\"id\":\"%s\", \"fullName\":\"%s\"}",
                            user.getClinicUserId(), user.getFullName()));
        } else {
            sendJsonResponse(response, HttpServletResponse.SC_OK,
                    "{\"error\":\"" + STAFF_NOT_FOUND_MESSAGE + "\"}");
        }
    }

    private void sendJsonResponse(HttpServletResponse response, int statusCode, String json) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(statusCode);
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
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
