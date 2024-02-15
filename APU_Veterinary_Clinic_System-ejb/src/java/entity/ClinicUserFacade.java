/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Optional;

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

    public ClinicUser findByFullName(String fullName) {
        try {
            return (ClinicUser) em.createNamedQuery("ClinicUser.findByFullName")
                    .setParameter("fullName", fullName)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public ClinicUser findByPhoneNumber(String phoneNumber) {
        try {
            return (ClinicUser) em.createNamedQuery("ClinicUser.findByPhoneNumber")
                    .setParameter("phoneNumber", phoneNumber)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
