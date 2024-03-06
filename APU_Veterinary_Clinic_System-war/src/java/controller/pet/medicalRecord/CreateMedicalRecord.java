/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.pet.medicalRecord;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.MedicalRecord;
import entity.Pet;
import repository.PetFacade;
import validator.MedicalRecordValidator;

import static constant.EndpointConstant.*;
import static constant.i18n.En.PET_NOT_FOUND_MESSAGE;

/**
 * @author Jackson Tai
 */
@WebServlet(name = "CreateMedicalRecord", urlPatterns = {CREATE_PET_MEDICAL_RECORD})
public class CreateMedicalRecord extends HttpServlet {

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
        request.getRequestDispatcher(CREATE_PET_MEDICAL_RECORD + ".jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String petId = request.getParameter("id");
        Pet existingPet = petFacade.find(petId);
        if (existingPet == null) {
            request.setAttribute("notFoundMessage", PET_NOT_FOUND_MESSAGE);
            request.getRequestDispatcher(VIEW_PET + ".jsp").forward(request, response);
            return;
        }

        String diagnosis = request.getParameter("diagnosis").trim();
        String prognosis = request.getParameter("prognosis").trim();

        MedicalRecord medicalRecord = new MedicalRecord(diagnosis, prognosis);
        MedicalRecordValidator medicalRecordValidator = new MedicalRecordValidator();
        Map<String, String> errorMessages = new HashMap<>(medicalRecordValidator.validate(medicalRecord));
        if (!errorMessages.isEmpty()) {
            errorMessages.forEach(request::setAttribute);
            request.setAttribute("pet", existingPet);
            request.getRequestDispatcher(CREATE_PET_MEDICAL_RECORD + ".jsp").forward(request, response);
            return;
        }
        existingPet.getMedicalRecords().add(medicalRecord);
        petFacade.edit(existingPet);
        response.sendRedirect(request.getContextPath() + VIEW_PET + "?id=" + existingPet.getPetId());
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
