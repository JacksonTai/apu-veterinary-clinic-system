/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.pet;

import entity.Customer;
import entity.Pet;
import repository.AppointmentFacade;
import repository.CustomerFacade;
import repository.PetFacade;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static constant.EndpointConstant.DELETE_PET;

/**
 *
 * @author Jackson Tai
 */
@WebServlet(name = "DeletePet", urlPatterns = {DELETE_PET})
public class DeletePet extends HttpServlet {

    @EJB
    private AppointmentFacade appointmentFacade;

    @EJB
    private CustomerFacade customerFacade;

    @EJB
    private PetFacade petFacade;
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {

        String petId = request.getParameter("id");
        Pet pet = petFacade.find(petId);
        Optional<Customer> customer = customerFacade.findByPetId(petId);
        if (customer.isPresent()) {
            customer.get().getPets().remove(pet);
            customerFacade.edit(customer.get());
        }

        if (pet != null) {
            appointmentFacade.findAllByPetId(petId).forEach(appointment -> {
                appointmentFacade.remove(appointment);
            });
            petFacade.remove(pet);
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
