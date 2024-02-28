/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entity.Customer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

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

    public Optional<Customer> findByCustomerId(String customerId) {
        return findResultByAttribute("Customer.findByCustomerId", "customerId", customerId);
    }

    public Optional<Customer> findByFullName(String fullName) {
        return findResultByAttribute("Customer.findByFullName", "fullName", fullName);
    }

    public Optional<Customer> findByPhoneNumber(String phoneNumber) {
        return findResultByAttribute("Customer.findByPhoneNumber", "phoneNumber", phoneNumber);
    }

    public Optional<Customer> findByEmail(String email) {
        return findResultByAttribute("Customer.findByEmail", "email", email);
    }

    // Note: Can be improved by using QueryDSL's BooleanBuilder in spring boot
    public Optional<Customer> findByIdOrFullNameOrEmailOrPhoneNumber(String input) {
        return findResultByAttribute("Customer.findByIdOrFullNameOrEmailOrPhoneNumber", "input", input);
    }

    public Optional<Customer> findByPetId(String petId) {
        return findResultByAttribute("Customer.findByPetId", "petId", petId);
    }
}
