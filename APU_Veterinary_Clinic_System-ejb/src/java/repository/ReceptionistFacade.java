/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entity.Receptionist;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jackson Tai
 */
@Stateless
public class ReceptionistFacade extends AbstractFacade<Receptionist> {

    @PersistenceContext(unitName = "APU_Veterinary_Clinic_System-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReceptionistFacade() {
        super(Receptionist.class);
    }
    
}
