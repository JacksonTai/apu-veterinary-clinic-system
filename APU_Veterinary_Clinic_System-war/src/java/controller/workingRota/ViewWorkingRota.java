/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.workingRota;

import entity.Vet;
import repository.VetFacade;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static constant.EndpointConstant.VIEW_WORKING_ROTA;
import static constant.GlobalConstant.WEEKDAYS;
import static util.DateUtil.generateWeekDates;
import static util.DateUtil.getNextFourMondaysDates;

/**
 * @author Jackson Tai
 */
@WebServlet(name = "ViewWorkingRota", urlPatterns = {VIEW_WORKING_ROTA})
public class ViewWorkingRota extends HttpServlet {

    @EJB
    private VetFacade vetFacade;

    private static final List<LocalDate> weeks = getNextFourMondaysDates();
    private LocalDate week = weeks.get(0);

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
        String weekParam = request.getParameter("week");
        if (weekParam != null) {
            try {
                week = LocalDate.parse(weekParam);
            } catch (Exception e) {
                week = weeks.get(0);
            }
        }

        List<Vet> vets = vetFacade.findAll();
        List<LocalDate> weekDates = generateWeekDates(week);

        request.setAttribute("vets", vets);
        request.setAttribute("weeks", weeks);
        request.setAttribute("weekDays", WEEKDAYS);
        request.setAttribute("weekDates", weekDates);

        Map<String, List<String>> vetWorkingDaysMap = new HashMap<>();
        vets.forEach(vet -> vetWorkingDaysMap.put(vet.getClinicUserId(), vet.getWorkingDays()));

        Map<LocalDate, Set<String>> dateToExpertisesMap = new HashMap<>();
        for (LocalDate weekDate : weekDates) {
            Set<String> allExpertises = new HashSet<>();

            for (Map.Entry<String, List<String>> entry : vetWorkingDaysMap.entrySet()) {
                String vetId = entry.getKey();
                List<String> vetWorkingDays = entry.getValue();

                // Check if the vet is working on the current day and add the expertises to the set
                if (vetWorkingDays.contains(weekDate.toString())) {
                    vets.forEach(vet -> {
                        if (vet.getClinicUserId().equals(vetId)) {
                            vet.setWorkingDays(vetWorkingDays);
                            vet.getExpertises().forEach(expertise -> allExpertises.add(expertise.getName()));
                        }
                    });
                }
            }
            dateToExpertisesMap.put(weekDate, allExpertises);
        }
        request.setAttribute("dateToExpertisesMap", dateToExpertisesMap);
        request.getRequestDispatcher(VIEW_WORKING_ROTA + ".jsp").forward(request, response);
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
