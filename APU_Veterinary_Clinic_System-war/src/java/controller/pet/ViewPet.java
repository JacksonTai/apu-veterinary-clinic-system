/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.pet;

import constant.AppointmentStatus;
import entity.ClinicUser;
import entity.Pet;
import repository.CustomerFacade;
import repository.PetFacade;
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
import java.util.Optional;

import static constant.AppointmentStatus.SCHEDULED;
import static constant.EndpointConstant.VIEW_PET;
import static constant.EndpointConstant.VIEW_PETS;
import static constant.UserRole.VET;
import static constant.i18n.En.PET_NOT_FOUND_MESSAGE;
import repository.ClinicUserFacade;
import static util.StringUtil.toTitleCase;

/**
 * @author Jackson Tai
 */
@WebServlet(name = "ViewPet", urlPatterns = {VIEW_PET})
public class ViewPet extends HttpServlet {

    @EJB
    private ClinicUserFacade clinicUserFacade;

    @EJB
    private CustomerFacade customerFacade;

    @EJB
    private PetFacade petFacade;

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        ClinicUser clinicUser = (ClinicUser) session.getAttribute("clinicUser");
        boolean isVet = clinicUser.getUserRole().equals(VET);

        String petId = request.getParameter("id");
        if (petId != null && !petId.isEmpty()) {
            Pet pet = petFacade.find(petId);

            // Avoid other vet from viewing other vet's appointment pet
            Optional<List<Pet>> pets = petFacade.findByAppointmentVetId(clinicUser.getClinicUserId());
            if (pet == null || (isVet && pets.isPresent() && !pets.get().contains(pet))){
                request.setAttribute("notFoundMessage", PET_NOT_FOUND_MESSAGE);
            } else {
                request.setAttribute("pet", pet);
                request.setAttribute("medicalRecords", pet.getMedicalRecords());
                request.setAttribute("customer", customerFacade.findByPetId(petId).get());
            }
            request.getRequestDispatcher(VIEW_PET + ".jsp").forward(request, response);
            return;
        }

        String namedQuery = null;
        LinkedHashMap<String, String> queryParams = new LinkedHashMap<>();
        if (isVet) {
            namedQuery = "Pet.findByAppointmentVetIdAndStatus";
            queryParams.put("vetId", clinicUser.getClinicUserId());
            queryParams.put("status", AppointmentStatus.COMPLETED);
        }

        PaginationUtil.applyPagination(PaginationConfig.<Pet>builder()
                .request(request)
                .response(response)
                .entityAttribute("pets")
                .viewPageEndpoint(VIEW_PET)
                .viewJspPath(VIEW_PETS)
                .facade(petFacade)
                .namedQuery(namedQuery)
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
