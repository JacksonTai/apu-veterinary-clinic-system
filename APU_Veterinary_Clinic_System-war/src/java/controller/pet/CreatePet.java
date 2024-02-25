/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.pet;

import entity.Customer;
import entity.Pet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ejb.EJB;

import repository.PetFacade;
import validator.CustomerValidator;
import validator.PetValidator;

import static constant.EndpointConstant.*;
import static constant.i18n.En.EMPTY_CUSTOMER_DETAILS_MESSAGE;
import static constant.i18n.En.CUSTOMER_NOT_FOUND_MESSAGE;

import repository.CustomerFacade;
import validator.ValidationResponse;

/**
 * @author Jackson Tai
 */
@WebServlet(name = "CreatePet", urlPatterns = {CREATE_PET})
public class CreatePet extends HttpServlet {

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

        String customerDetails = request.getParameter("customerDetails").trim();
        String species = request.getParameter("species").trim();
        String breed = request.getParameter("breed").trim();
        String name = request.getParameter("name").trim();
        String healthStatus = request.getParameter("healthStatus").trim();
        Pet pet = new Pet(species, breed, name, healthStatus);

        PetValidator petValidator = new PetValidator();
        Map<String, String> errorMessages = new HashMap<>(petValidator.validate(pet));

        Customer customer = null;
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
            request.getRequestDispatcher(CREATE_PET + ".jsp").forward(request, response);
        } else {
            petFacade.create(pet);
            customer.getPets().add(pet);
            customerFacade.edit(customer);
            response.sendRedirect(request.getContextPath() + VIEW_PET + "?id=" + pet.getPetId());
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
