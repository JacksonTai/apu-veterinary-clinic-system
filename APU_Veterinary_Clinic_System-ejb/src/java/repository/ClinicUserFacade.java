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

    public ClinicUser findByEmailAndStatus(String email, String status) {
        String[] attributes = {"email", "status"};
        String[] values = {email, status};
        return findResultByAttributes("ClinicUser.findByEmailAndStatus", attributes, values)
                .orElse(null);
    }

    public ClinicUser findByFullNameAndStatus(String fullName, String status) {
        String[] attributes = {"fullName", "status"};
        String[] values = {fullName, status};
        return findResultByAttributes("ClinicUser.findByFullNameAndStatus", attributes, values)
                .orElse(null);
    }

    public Optional<ClinicUser> findByIdOrFullNameOrEmailAndStatus(String input, String status) {
        String[] attributes = {"input", "status"};
        String[] values = {input, status};
        System.out.println("ClinicUser.findByIdOrFullNameOrEmailAndStatus");
        System.out.println("input: " + input);
        System.out.println("status: " + status);
        return findResultByAttributes("ClinicUser.findByIdOrFullNameOrEmailAndStatus", attributes, values);
    }

    public long getCountByStatus(String status) {
        TypedQuery<Long> query = em.createNamedQuery("ClinicUser.countByStatus", Long.class);
        query.setParameter("status", status);
        return query.getSingleResult();
    }

    public long getCountByUserRoleAndStatus(String userRole, String status) {
        TypedQuery<Long> query = em.createNamedQuery("ClinicUser.countByUserRoleAndStatus", Long.class);
        query.setParameter("userRole", userRole);
        query.setParameter("status", status);
        return query.getSingleResult();
    }
}
