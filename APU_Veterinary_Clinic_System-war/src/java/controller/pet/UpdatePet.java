/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.pet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Customer;
import entity.Pet;
import repository.PetFacade;
import validator.CustomerValidator;
import validator.PetValidator;

import static constant.EndpointConstant.*;

/**
 *
 * @author Jackson Tai
 */
@WebServlet(name = "UpdatePet", urlPatterns = {UPDATE_PET})
public class UpdatePet extends HttpServlet {

    @EJB
    private PetFacade petFacade;

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

        Pet pet = petFacade.find(request.getParameter("id"));
        if (pet == null) {
            response.sendRedirect(request.getContextPath() + VIEW_PET + ".jsp");
            return;
        }
        request.setAttribute("pet", pet);
        request.getRequestDispatcher(UPDATE_PET + ".jsp").forward(request, response);
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

        String id = request.getParameter("id");
        Pet existingPet = petFacade.find(id);
        if (existingPet == null) {
            response.sendRedirect(request.getContextPath() + VIEW_PET);
            return;
        }

        String species = request.getParameter("species").trim();
        String breed = request.getParameter("breed").trim();
        String name = request.getParameter("name").trim();
        String healthStatus = request.getParameter("healthStatus").trim();

        Map<String, String> errorMessages = new HashMap<>();
        if (!species.equals(existingPet.getSpecies())) {
            errorMessages.putAll(PetValidator.validateSpecies(species));
        }
        if (!breed.equals(existingPet.getBreed())) {
            errorMessages.putAll(PetValidator.validateBreed(breed));
        }
        if (!name.equals(existingPet.getName())) {
            errorMessages.putAll(PetValidator.validateName(name));
        }
        if (!healthStatus.equals(existingPet.getHealthStatus())) {
            errorMessages.putAll(PetValidator.validateHealthStatus(healthStatus));
        }

        if (!errorMessages.isEmpty()) {
            errorMessages.forEach(request::setAttribute);
            request.getRequestDispatcher(UPDATE_PET + ".jsp").forward(request, response);
        } else {
            existingPet.setSpecies(species);
            existingPet.setBreed(breed);
            existingPet.setName(name);
            existingPet.setHealthStatus(healthStatus);
            petFacade.edit(existingPet);
            response.sendRedirect(request.getContextPath() + VIEW_PET  + "?id=" + existingPet.getPetId());
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
