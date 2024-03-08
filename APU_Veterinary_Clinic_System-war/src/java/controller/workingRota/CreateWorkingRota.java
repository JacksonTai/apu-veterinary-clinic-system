/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.workingRota;

import constant.ClinicUserStatus;
import entity.Vet;
import org.apache.commons.lang3.SerializationUtils;
import repository.ClinicUserFacade;
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

import static constant.EndpointConstant.CREATE_WORKING_ROTA;
import static constant.EndpointConstant.VIEW_WORKING_ROTA;
import static constant.GlobalConstant.WEEKDAYS;
import static util.DateUtil.generateWeekDates;
import static util.DateUtil.getNextWeekMondayDates;

/**
 * @author Jackson Tai
 */
@WebServlet(name = "CreateWorkingRota", urlPatterns = {CREATE_WORKING_ROTA})
public class CreateWorkingRota extends HttpServlet {

    @EJB
    private VetFacade vetFacade;

    @EJB
    private ClinicUserFacade clinicUserFacade;

    private static List<Vet> vets = new ArrayList<>();
    private static LocalDate week;
    private static final List<LocalDate> weeks = getNextWeekMondayDates(4);
    private static List<LocalDate> weekDates = new ArrayList<>();
    private static boolean maxWorkingRota;

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
        updateWeeksAndWeekDate();
        if (!maxWorkingRota) {
            String weekParam = request.getParameter("week");
            if (weekParam != null) {
                try {
                    week = LocalDate.parse(weekParam);
                } catch (Exception e) {
                    week = weeks.get(0);
                }
            }
            request.setAttribute("vets", vets);
            request.setAttribute("weeks", weeks);
            request.setAttribute("weekDays", WEEKDAYS);
            request.setAttribute("weekDates", weekDates = generateWeekDates(week));
        }
        request.setAttribute("maxWorkingRota", maxWorkingRota);
        request.getRequestDispatcher(CREATE_WORKING_ROTA + ".jsp").forward(request, response);
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
        updateWeeksAndWeekDate();
        if (!maxWorkingRota) {
            String weekParam = request.getParameter("week");
            if (weekParam != null) {
                try {
                    week = LocalDate.parse(weekParam);
                } catch (Exception e) {
                    week = weeks.get(0);
                }
            }

            // Get the working days of each vet.
            Enumeration<String> parameterNames = request.getParameterNames();
            Map<String, List<String>> vetWorkingDaysMap = new HashMap<>();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                if (paramName.contains("_")) {
                    String[] parts = paramName.split("_");
                    String clinicUserId = parts[0];
                    String day = parts[1];
                    if (request.getParameter(paramName) != null) {
                        List<String> workingDays = vetWorkingDaysMap.
                                computeIfAbsent(clinicUserId, k -> new ArrayList<>());
                        workingDays.add(day);
                    }
                }
            }

            request.setAttribute("weeks", weeks);
            request.setAttribute("weekDays", WEEKDAYS);
            request.setAttribute("weekDates", weekDates = generateWeekDates(week));

            List<Vet> tempVets = new ArrayList<>();
            vets.forEach(vet -> tempVets.add(SerializationUtils.clone(vet)));

            Map<LocalDate, Set<String>> dateToExpertisesMap = new HashMap<>();
            for (LocalDate weekDate : weekDates) {
                Set<String> allExpertises = new HashSet<>();

                for (Map.Entry<String, List<String>> entry : vetWorkingDaysMap.entrySet()) {
                    String vetId = entry.getKey();
                    List<String> vetWorkingDays = entry.getValue();

                    // Check if the vet is working on the current day and add the expertises to the set
                    if (vetWorkingDays.contains(weekDate.toString())) {
                        tempVets.forEach(vet -> {
                            if (vet.getClinicUserId().equals(vetId)) {
                                vet.setWorkingDays(vetWorkingDays);
                                vet.getExpertises().forEach(expertise -> allExpertises.add(expertise.getName()));
                            }
                        });
                    }
                }
                dateToExpertisesMap.put(weekDate, allExpertises);
            }

            request.setAttribute("vets", tempVets);
            request.setAttribute("dateToExpertisesMap", dateToExpertisesMap);
            for (Map.Entry<LocalDate, Set<String>> entry : dateToExpertisesMap.entrySet()) {
                Set<String> expertisesCovered = entry.getValue();
                if (expertisesCovered.size() < 5) {
                    request.getRequestDispatcher(CREATE_WORKING_ROTA + ".jsp").forward(request, response);
                    return;
                }
            }
            for (Map.Entry<String, List<String>> vetWorkingDaysEntry : vetWorkingDaysMap.entrySet()) {
                String vetId = vetWorkingDaysEntry.getKey();
                List<String> vetWorkingDays = vetWorkingDaysEntry.getValue();
                vets.forEach(vet -> {
                    if (vet.getClinicUserId().equals(vetId)) {
                        vet.getWorkingDays().addAll(vetWorkingDays);
                        vetFacade.edit(vet);
                    }
                });
            }
            response.sendRedirect(request.getContextPath() + VIEW_WORKING_ROTA + "?week=" + week);
        }
    }

    private void updateWeeksAndWeekDate() {
        vets = vetFacade.findByStatus(ClinicUserStatus.APPROVED);
        for (Vet vet : vets) {
            for (String workingDay : vet.getWorkingDays()) {
                LocalDate workingDate = LocalDate.parse(workingDay);
                if (weeks.contains(workingDate)) {
                    weeks.remove(workingDate);
                    break;
                }
            }
        }
        if (!(maxWorkingRota = weeks.isEmpty())) {
            week = weeks.get(0);
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
