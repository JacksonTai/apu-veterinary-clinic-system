/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entity.Appointment;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Jackson Tai
 */
@Stateless
public class AppointmentFacade extends AbstractFacade<Appointment> {

    @PersistenceContext(unitName = "APU_Veterinary_Clinic_System-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AppointmentFacade() {
        super(Appointment.class);
    }

    public Optional<Appointment> findByDateAndCustomerAndPetAndVetAndStatus(String date, String customerId,
                                                                            String petId, String vetId, String status) {
        String[] paramNames = {"date", "customerId", "petId", "vetId", "status"};
        String[] paramValues = {date, customerId, petId, vetId, status};
        return findResultByAttributes("Appointment.findByDateAndCustomerAndPetAndVetAndStatus", paramNames,
                paramValues);
    }

    public List<Appointment> findAllByCustomer (String customerId) {
        return findResultsByAttribute("Appointment.findAllByCustomer", "customerId", customerId)
                .orElse(new ArrayList<>());
    }

    public long getCountByStatus(String status) {
        TypedQuery<Long> query = em.createNamedQuery("Appointment.countByStatus", Long.class);
        query.setParameter("appointmentStatus", status);
        return query.getSingleResult();
    }
}
