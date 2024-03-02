/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.workingRota;

import entity.Vet;
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
import static constant.GlobalConstant.WEEKDAYS;
import static util.DateUtil.generateWeekDates;
import static util.DateUtil.getNextFourMondaysDates;

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
    private static final List<LocalDate> weeks = getNextFourMondaysDates();
    private static LocalDate week;
    private static boolean maxWorkingRota;

    @Override
    public void init() throws ServletException {
        super.init();
        vets = vetFacade.findAll();
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
            request.setAttribute("weekDates", generateWeekDates(week));
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

            // TODO: check if there is at least 3 vets working on each day and at least 5 expertises are covered by those 3 vets
            request.setAttribute("vets", vets);
            request.setAttribute("weekDates", weeks);

            // Fetch the vet and update the working days
            for (Map.Entry<String, List<String>> entry : vetWorkingDaysMap.entrySet()) {
                String clinicUserId = entry.getKey();
                Vet vet = vetFacade.find(clinicUserId);
                if (vet != null) {
                    vet.setWorkingDays(entry.getValue());
                    vetFacade.edit(vet);
                }
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
