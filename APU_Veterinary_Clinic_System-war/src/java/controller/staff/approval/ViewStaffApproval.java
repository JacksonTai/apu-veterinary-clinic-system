/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.staff.approval;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.ClinicUser;
import entity.Expertise;
import entity.MakerChecker;
import entity.Vet;
import lombok.SneakyThrows;
import repository.ClinicUserFacade;
import repository.MakerCheckerFacade;
import util.StringUtil;
import util.pagination.PaginationConfig;
import util.pagination.PaginationUtil;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static constant.EndpointConstant.VIEW_STAFF_APPROVAL;
import static constant.EndpointConstant.VIEW_STAFF_APPROVALS;
import static constant.GlobalConstant.DASH;
import static constant.UserRole.VET;
import static constant.i18n.En.RECORD_NOT_FOUND_MESSAGE;

/**
 * @author Jackson Tai
 */
@WebServlet(name = "ViewStaffApproval", urlPatterns = {VIEW_STAFF_APPROVAL})
public class ViewStaffApproval extends HttpServlet {

    @EJB
    private ClinicUserFacade clinicUserFacade;

    @EJB
    private MakerCheckerFacade makerCheckerFacade;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    @SneakyThrows
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String mcId = request.getParameter("id");

        if (mcId != null && !mcId.isEmpty()) {

            HttpSession session = request.getSession(false);
            ClinicUser staff = (ClinicUser) session.getAttribute("clinicUser");

            MakerChecker mc = makerCheckerFacade.find(mcId);
            if (mc == null) {
                request.setAttribute("notFoundMessage", RECORD_NOT_FOUND_MESSAGE);
            } else {
                try {
                    ClinicUser maker = clinicUserFacade.find(mc.getMakerId());

                    String role = maker.getUserRole();
                    ClinicUser currentMakerValue;
                    ClinicUser newMakerValue;
                    if (role.equals(VET)) {
                        currentMakerValue = objectMapper.readValue(mc.getCurrentValue(), Vet.class);
                        newMakerValue = objectMapper.readValue(mc.getNewValue(), Vet.class);

                        Vet currentVetValue = (Vet) currentMakerValue;
                        Vet newVetValue = (Vet) newMakerValue;
                        List<String> currentExpertises = currentVetValue.getExpertises().stream()
                                .map(Expertise::getName)
                                .collect(Collectors.toList());
                        request.setAttribute("currentExpertises", StringUtil.requireNonNullElse(
                                StringUtil.getConcatenatedString(currentExpertises, ","), DASH));
                        List<String> newExpertises = newVetValue.getExpertises().stream()
                                .map(Expertise::getName)
                                .collect(Collectors.toList());
                        request.setAttribute("newExpertises", StringUtil.requireNonNullElse(
                                StringUtil.getConcatenatedString(newExpertises, ","), DASH));
                    } else {
                        currentMakerValue = objectMapper.readValue(mc.getCurrentValue(), ClinicUser.class);
                        newMakerValue = objectMapper.readValue(mc.getNewValue(), ClinicUser.class);
                    }
                    request.setAttribute("currentMakerValue", currentMakerValue);
                    request.setAttribute("newMakerValue", newMakerValue);
                    request.setAttribute("staffApproval", mc);
                    request.setAttribute("isOwnApproval", staff.getClinicUserId().equals(mc.getMakerId()));
                    if (mc.getCheckerId() != null) {
                        ClinicUser checker = clinicUserFacade.find(mc.getCheckerId());
                        if (checker != null) {
                            request.setAttribute("checker", checker);
                        }
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            request.getRequestDispatcher(VIEW_STAFF_APPROVAL + ".jsp").forward(request, response);
            return;
        }

        String status = request.getParameter("type");
        status = (status == null || status.isEmpty() ? "pending" : status);
        LinkedHashMap<String, String> queryParams = new LinkedHashMap<>();
        queryParams.put("status", status.toUpperCase());
        PaginationUtil.applyPagination(PaginationConfig.<MakerChecker>builder()
                .request(request)
                .response(response)
                .entityAttribute("staffApprovals")
                .viewPageEndpoint(VIEW_STAFF_APPROVAL + "?type=" + status)
                .viewJspPath(VIEW_STAFF_APPROVALS)
                .facade(makerCheckerFacade)
                .namedQuery("MakerChecker.findByStatus")
                .queryParams(queryParams)
                .build());
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
