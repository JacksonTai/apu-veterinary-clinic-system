/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.staff;

import entity.ClinicUser;
import entity.Expertise;
import entity.Vet;
import repository.ClinicUserFacade;
import repository.ExpertiseFacade;
import repository.VetFacade;
import validator.ClinicUserValidator;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static constant.EndpointConstant.UPDATE_STAFF;
import static constant.EndpointConstant.VIEW_STAFF;
import static constant.UserRole.*;

/**
 *
 * @author Jackson Tai
 */
@WebServlet(name = "UpdateStaff", urlPatterns = {UPDATE_STAFF})
public class UpdateStaff extends HttpServlet {

    @EJB
    private VetFacade vetFacade;

    @EJB
    private ExpertiseFacade expertiseFacade;

    @EJB
    private ClinicUserFacade clinicUserFacade;

    private List<String> roleList = new ArrayList<>(Arrays.asList(VET, RECEPTIONIST, MANAGING_STAFF));
    private List<Expertise> expertises = new ArrayList<>();
    private String formType = "UPDATE";
    private Vet vet;
    private boolean isVet;

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("roleList", roleList);
        request.setAttribute("formType", formType);
        ClinicUser staff = clinicUserFacade.find(request.getParameter("id"));
        if (staff == null) {
            response.sendRedirect(request.getContextPath() + VIEW_STAFF + ".jsp");
            return;
        }
        request.setAttribute("expertises", expertises = expertiseFacade.findAll());
        isVet = staff.getUserRole().equals(VET);
        if (isVet) {
            vet = (Vet) staff;
            request.setAttribute("selectedExpertises", vet.getExpertises());
        }
        request.setAttribute("staff", staff);
        request.getRequestDispatcher(UPDATE_STAFF + ".jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("roleList", roleList);
        request.setAttribute("formType", formType);

        String id = request.getParameter("id");
        ClinicUser existingStaff = clinicUserFacade.find(id);
        if (existingStaff == null) {
            response.sendRedirect(request.getContextPath() + VIEW_STAFF);
            return;
        }

        String fullName = request.getParameter("fullName").trim();
        String email = request.getParameter("email").trim().toLowerCase();

        List<Expertise> selectedExpertises = new ArrayList<>();
        if (isVet) {
            request.setAttribute("expertises", expertises);

            // Get the selected expertises
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                if (paramName.startsWith("expertise_")) {
                    String expertiseId = paramName.substring("expertise_".length());
                    expertises.stream()
                            .filter(expertise -> expertise.getExpertiseId().equals(expertiseId))
                            .findFirst()
                            .ifPresent(selectedExpertises::add);
                }
            }
            request.setAttribute("selectedExpertises", selectedExpertises);
        }

        ClinicUserValidator clinicUserValidator = new ClinicUserValidator(clinicUserFacade);
        Map<String, String> errorMessages = new HashMap<>();
        if (!email.equals(existingStaff.getEmail())) {
            errorMessages.putAll(ClinicUserValidator.validateEmail(email));
            errorMessages.putAll(clinicUserValidator.validateDuplicateEmail(email));
        }
        if (!fullName.equals(existingStaff.getFullName())) {
            errorMessages.putAll(ClinicUserValidator.validateFullName(fullName));
            errorMessages.putAll(clinicUserValidator.validateDuplicateFullName(fullName));
        }

        if (!errorMessages.isEmpty()) {
            errorMessages.forEach(request::setAttribute);
            request.setAttribute("staff", existingStaff);
            request.getRequestDispatcher(UPDATE_STAFF + ".jsp").forward(request, response);
        } else {
            if (isVet) {
                vet.setEmail(email);
                vet.setFullName(fullName);
                vet.setExpertises(selectedExpertises);
                vetFacade.edit(vet);
            } else {
                existingStaff.setEmail(email);
                existingStaff.setFullName(fullName);
                clinicUserFacade.edit(existingStaff);
            }
            response.sendRedirect(request.getContextPath() + VIEW_STAFF + "?id=" + existingStaff.getClinicUserId());
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
