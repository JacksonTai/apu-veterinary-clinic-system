/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entity.ClinicUser;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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

    public ClinicUser findByEmail(String email) {
        return findResultByAttribute("ClinicUser.findByEmail", "email", email).orElse(null);
    }

    public ClinicUser findByFullName(String fullName) {
        return findResultByAttribute("ClinicUser.findByFullName", "fullName", fullName)
                .orElse(null);
    }

    public Optional<ClinicUser> findByIdOrFullNameOrEmail(String input) {
        return findResultByAttribute("ClinicUser.findByIdOrFullNameOrEmail", "input", input);
    }

    public long getCountByUserRoleAndStatus(String userRole, String status) {
        TypedQuery<Long> query = em.createNamedQuery("ClinicUser.countByUserRoleAndStatus", Long.class);
        query.setParameter("userRole", userRole);
        query.setParameter("status", status);
        return query.getSingleResult();
    }
}
