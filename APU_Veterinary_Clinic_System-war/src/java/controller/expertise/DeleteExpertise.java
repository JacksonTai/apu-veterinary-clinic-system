/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.expertise;

import com.fasterxml.jackson.databind.ObjectMapper;
import constant.MakeChecker;
import entity.Expertise;
import entity.MakerChecker;
import entity.Vet;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.ExpertiseFacade;
import repository.MakerCheckerFacade;
import repository.VetFacade;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static constant.EndpointConstant.DELETE_EXPERTISE;
import static constant.i18n.En.ERROR_DELETING_EXPERTISE;

/**
 * @author Jackson Tai
 */
@WebServlet(name = "DeleteExpertise", urlPatterns = {DELETE_EXPERTISE})
public class DeleteExpertise extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(DeleteExpertise.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @EJB
    private MakerCheckerFacade makerCheckerFacade;

    @EJB
    private VetFacade vetFacade;

    @EJB
    private ExpertiseFacade expertiseFacade;

    @Override
    @SneakyThrows
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String expertiseId = request.getParameter("id");
        Expertise expertise = expertiseFacade.find(expertiseId);
        if (expertise != null) {
           Optional<List<Vet>> vets = vetFacade.findAllByExpertiseId(expertiseId);
            if (vets.isPresent() && !vets.get().isEmpty()) {
                vets.get().forEach(vet -> {
                    vet.getExpertises().remove(expertise);
                    vetFacade.edit(vet);
                });
            }

            // check if there is pending mc request with the current and new value having this expertise and reject them
            MakerChecker mc = makerCheckerFacade.findByStatusAndModuleAndActionType(
                    MakeChecker.Status.PENDING.toString(), MakeChecker.Module.PROFILE.toString(),
                    MakeChecker.ActionType.UPDATE.toString());
            if (mc != null) {
                try {
                    Vet currVet = objectMapper.readValue(mc.getCurrentValue(), Vet.class);
                    Vet newVet = objectMapper.readValue(mc.getNewValue(), Vet.class);
                    if (currVet.getExpertises().contains(expertise) || newVet.getExpertises().contains(expertise)) {
                        mc.setStatus(MakeChecker.Status.REJECTED.toString());
                        makerCheckerFacade.edit(mc);
                    }
                } catch (Exception e) {
                    logger.error(ERROR_DELETING_EXPERTISE, e);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ERROR_DELETING_EXPERTISE);
                }
            }

            expertiseFacade.remove(expertise);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
