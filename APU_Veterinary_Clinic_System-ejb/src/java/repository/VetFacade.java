/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entity.Vet;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jackson Tai
 */
@Stateless
public class VetFacade extends AbstractFacade<Vet> {

    @PersistenceContext(unitName = "APU_Veterinary_Clinic_System-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VetFacade() {
        super(Vet.class);
    }
    
}
