/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.pet;

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
import java.io.IOException;

import static constant.EndpointConstant.VIEW_PET;
import static constant.EndpointConstant.VIEW_PETS;
import static constant.i18n.En.PET_NOT_FOUND_MESSAGE;

/**
 *
 * @author Jackson Tai
 */
@WebServlet(name = "ViewPet", urlPatterns = {VIEW_PET})
public class ViewPet extends HttpServlet {

    @EJB
    private CustomerFacade customerFacade;

    @EJB
    private PetFacade petFacade;

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

        String petId = request.getParameter("id");
        if (petId != null && !petId.isEmpty()) {
            Pet pet = petFacade.find(petId);
            if (pet == null) {
                request.setAttribute("notFoundMessage", PET_NOT_FOUND_MESSAGE);
            } else {
                request.setAttribute("pet", pet);
                request.setAttribute("customer", customerFacade.findByPetId(petId).get());
            }
            request.getRequestDispatcher(VIEW_PET + ".jsp").forward(request, response);
            return;
        }

        PaginationUtil.applyPagination(PaginationConfig.<Pet>builder()
                .request(request)
                .response(response)
                .entityAttribute("pets")
                .viewPageEndpoint(VIEW_PET)
                .viewJspPath(VIEW_PETS)
                .facade(petFacade)
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
