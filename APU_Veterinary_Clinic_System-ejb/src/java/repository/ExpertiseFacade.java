/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entity.Customer;
import entity.Expertise;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

/**
 *
 * @author Jackson Tai
 */
@Stateless
public class ExpertiseFacade extends AbstractFacade<Expertise> {

    @PersistenceContext(unitName = "APU_Veterinary_Clinic_System-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ExpertiseFacade() {
        super(Expertise.class);
    }

    public Optional<Expertise> findByName(String name) {
        return findByAttribute("Expertise.findByName", "name", name);
    }

}
