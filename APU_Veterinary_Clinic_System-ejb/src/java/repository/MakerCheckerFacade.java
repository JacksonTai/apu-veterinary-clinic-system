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

    public MakerChecker findByMakerIdAndStatusAndModuleAndActionType(String makerId, String status,
                                                                     String module, String actionType) {
        String[] paramNames = {"makerId", "status", "module", "actionType"};
        String[] paramValues = {makerId, status, module, actionType};
        return findResultByAttributes("MakerChecker.findByMakerIdAndStatusAndModuleAndActionType",
                paramNames, paramValues).orElse(null);
    }

    public MakerChecker findByStatusAndModuleAndActionType(String status, String module, String actionType) {
        String[] paramNames = {"status", "module", "actionType"};
        String[] paramValues = {status, module, actionType};
        return findResultByAttributes("MakerChecker.findByStatusAndModuleAndActionType", paramNames, paramValues)
                .orElse(null);
    }

}
