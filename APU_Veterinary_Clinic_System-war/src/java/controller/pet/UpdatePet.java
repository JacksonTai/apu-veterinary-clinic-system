/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.pet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
import static constant.i18n.En.*;

import repository.CustomerFacade;
import validator.ValidationResponse;

/**
 * @author Jackson Tai
 */
@WebServlet(name = "UpdatePet", urlPatterns = {UPDATE_PET})
public class UpdatePet extends HttpServlet {

    @EJB
    private CustomerFacade customerFacade;

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

        Pet pet = petFacade.find(request.getParameter("id"));
        if (pet == null) {
            request.setAttribute("notFoundMessage", PET_NOT_FOUND_MESSAGE);
        } else {
            request.setAttribute("pet", pet);
            Optional<Customer> existingOwner = customerFacade.findByPetId(pet.getPetId());
            existingOwner.ifPresent(value -> request.setAttribute("existingOwner", value));
        }
        request.getRequestDispatcher(UPDATE_PET + ".jsp").forward(request, response);
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

        String petId = request.getParameter("id");
        Pet existingPet = petFacade.find(petId);
        if (existingPet == null) {
            request.setAttribute("notFoundMessage", PET_NOT_FOUND_MESSAGE);
            request.getRequestDispatcher(VIEW_PET + ".jsp").forward(request, response);
            return;
        }

        String customerDetails = request.getParameter("customerDetails").trim();
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

        Customer customer = null;
        Optional<Customer> existingOwner = customerFacade.findByPetId(petId);
        if (!existingOwner.isPresent()) {
            response.sendRedirect(request.getContextPath() + VIEW_PET);
            return;
        }

        CustomerValidator customerValidator = new CustomerValidator(customerFacade);
        ValidationResponse<Customer> validationResponse = customerValidator.validateCustomerDetails(customerDetails);
        Map<String, String> customerDetailsErrorMessages = validationResponse.getErrorMessages();
        if (!customerDetailsErrorMessages.isEmpty()) {
            errorMessages.putAll(customerDetailsErrorMessages);
        } else if (validationResponse.getEntity().isPresent()) {
            customer = validationResponse.getEntity().get();
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
            if (!existingOwner.get().getCustomerId().equals(customer.getCustomerId())) {
                existingOwner.get().getPets().remove(existingPet);
                customer.getPets().add(existingPet);
                customerFacade.edit(existingOwner.get());
                customerFacade.edit(customer);
            }
            response.sendRedirect(request.getContextPath() + VIEW_PET + "?id=" + existingPet.getPetId());
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
