/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.pet;

import entity.Pet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ejb.EJB;

import repository.PetFacade;
import validator.PetValidator;

import static constant.EndpointConstant.*;

/**
 * @author Jackson Tai
 */
@WebServlet(name = "CreatePet", urlPatterns = {CREATE_PET})
public class CreatePet extends HttpServlet {

    @EJB
    private PetFacade petFacade;

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
        request.getRequestDispatcher(CREATE_PET + ".jsp").forward(request, response);
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

        String species = request.getParameter("species").trim();
        String breed = request.getParameter("breed").trim();
        String name = request.getParameter("name").trim();
        String healthStatus = request.getParameter("healthStatus").trim();
        Pet pet = new Pet(species, breed, name, healthStatus);

        PetValidator petValidator = new PetValidator();
        Map<String, String> errorMessages = petValidator.validate(pet);
        if (!errorMessages.isEmpty()) {
            errorMessages.forEach(request::setAttribute);
            request.getRequestDispatcher(CREATE_PET + ".jsp").forward(request, response);
        } else {
            petFacade.create(pet);
            response.sendRedirect(request.getContextPath() + VIEW_PET  + "?id=" + pet.getPetId());
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
