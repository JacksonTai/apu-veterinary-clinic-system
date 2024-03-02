/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.staff.approval;

import com.fasterxml.jackson.databind.ObjectMapper;
import constant.MakeChecker;
import entity.ClinicUser;
import entity.MakerChecker;
import entity.Vet;
import lombok.SneakyThrows;
import repository.ClinicUserFacade;
import repository.MakerCheckerFacade;
import repository.VetFacade;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static constant.EndpointConstant.UPDATE_STAFF_APPROVAL;
import static constant.UserRole.VET;
import static constant.i18n.En.ERROR_UPDATING_APPROVAL;
import static util.HttpUtil.sendJsonResponse;

/**
 * @author Jackson Tai
 */
@WebServlet(name = "UpdateStaffApproval", urlPatterns = {UPDATE_STAFF_APPROVAL})
@MultipartConfig
public class UpdateStaffApproval extends HttpServlet {

    @EJB
    private VetFacade vetFacade;

    @EJB
    private ClinicUserFacade clinicUserFacade;

    @EJB
    private MakerCheckerFacade makerCheckerFacade;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    @SneakyThrows
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        ClinicUser checker = (ClinicUser) session.getAttribute("clinicUser");
        String approvalAction = request.getParameter("approvalAction");
        String mcId = request.getParameter("mcId");

        if (approvalAction != null && mcId != null) {
            MakerChecker mc = makerCheckerFacade.find(mcId);

            if (mc != null) {
                String message;
                ClinicUser maker = clinicUserFacade.find(mc.getMakerId());

                if (approvalAction.equals("approve")) {
                    try {
                        if (maker.getUserRole().equals(VET)) {
                            Vet vet = objectMapper.readValue(mc.getNewValue(), Vet.class);
                            vetFacade.edit(vet);
                        } else {
                            maker = objectMapper.readValue(mc.getNewValue(), ClinicUser.class);
                            clinicUserFacade.edit(maker);
                        }
                        message = String.valueOf(MakeChecker.Status.APPROVED);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else if (approvalAction.equals("reject")) {
                    message = String.valueOf(MakeChecker.Status.REJECTED);
                } else {
                    sendJsonResponse(response, HttpServletResponse.SC_BAD_REQUEST,
                            "{\"error\":\"" + ERROR_UPDATING_APPROVAL + "\"}");
                    return;
                }
                mc.setStatus(message);
                mc.setCheckerId(checker.getClinicUserId());
                makerCheckerFacade.edit(mc);
                sendJsonResponse(response, HttpServletResponse.SC_OK,
                        String.format("{\"mcId\":\"%s\", \"status\":\"%s\"}",
                                mc.getMakerCheckerId(), message));
            }
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
