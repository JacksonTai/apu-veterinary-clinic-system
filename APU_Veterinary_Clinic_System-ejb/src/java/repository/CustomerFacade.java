/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entity.Customer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jackson Tai
 */
@Stateless
public class CustomerFacade extends AbstractFacade<Customer> {

    @PersistenceContext(unitName = "APU_Veterinary_Clinic_System-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomerFacade() {
        super(Customer.class);
    }

    public Customer findByCustomerId(String customerId) {
        return em.find(Customer.class, customerId);
    }

    public Customer findByFullName(String fullName) {
        try {
            return (Customer) em.createNamedQuery("Customer.findByFullName")
                    .setParameter("fullName", fullName)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Customer findByPhoneNumber(String phoneNumber) {
        try {
            return (Customer) em.createNamedQuery("Customer.findByPhoneNumber")
                    .setParameter("phoneNumber", phoneNumber)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Customer findByEmail(String email) {
        try {
            return (Customer) em.createNamedQuery("Customer.findByEmail")
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
