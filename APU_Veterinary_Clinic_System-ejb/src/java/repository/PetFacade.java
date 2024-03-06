/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entity.Customer;
import entity.Pet;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Jackson Tai
 */
@Stateless
public class PetFacade extends AbstractFacade<Pet> {

    @PersistenceContext(unitName = "APU_Veterinary_Clinic_System-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PetFacade() {
        super(Pet.class);
    }

    public Optional<List<Pet>> findByAppointmentVetId(String vetId) {
        return findResultsByAttribute("Pet.findByAppointmentVetId", "vetId", vetId);
    }
    
}
