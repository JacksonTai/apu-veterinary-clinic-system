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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
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

    public Optional<List<Vet>> findAllByExpertiseId(String expertiseId) {
        return findResultsByAttribute("Vet.findAllByExpertiseId", "expertiseId", expertiseId);
    }

    public List<Vet> findByStatus(String status) {
        return findResultsByAttribute("Vet.findByStatus", "status", status)
                .orElse(new ArrayList<>());
    }

}
