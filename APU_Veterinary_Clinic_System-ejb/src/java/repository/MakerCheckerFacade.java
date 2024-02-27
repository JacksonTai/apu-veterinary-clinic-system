/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entity.MakerChecker;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jackson Tai
 */
@Stateless
public class MakerCheckerFacade extends AbstractFacade<MakerChecker> {

    @PersistenceContext(unitName = "APU_Veterinary_Clinic_System-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MakerCheckerFacade() {
        super(MakerChecker.class);
    }

    public MakerChecker findByMakerIdAndModuleAndActionType(String makerId, String module, String actionType) {
        String[] paramNames = {"makerId", "module", "actionType"};
        String[] paramValues = {makerId, module, actionType};
        return findByAttributes("Customer.findByMakerIdAndModuleAndActionType", paramNames, paramValues)
                .orElse(null);
    }

}
