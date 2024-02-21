/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entity.ClinicUser;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * @author Jackson Tai
 */
@Stateless
public class ClinicUserFacade extends AbstractFacade<ClinicUser> {

    @PersistenceContext(unitName = "APU_Veterinary_Clinic_System-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClinicUserFacade() {
        super(ClinicUser.class);
    }

    public ClinicUser findByClinicUserId(String clinicUserID) {
        return em.find(ClinicUser.class, clinicUserID);
    }

    public ClinicUser findByEmail(String email) {
        try {
            return (ClinicUser) em.createNamedQuery("ClinicUser.findByEmail")
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
