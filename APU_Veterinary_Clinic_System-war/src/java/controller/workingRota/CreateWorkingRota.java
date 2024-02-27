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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import static constant.EndpointConstant.CREATE_WORKING_ROTA;
import static constant.GlobalConstant.DMY_SLASH_DATE_FORMAT;
import static util.StringUtil.convertDateFormat;

/**
 * @author Jackson Tai
 */
@WebServlet(name = "CreateWorkingRota", urlPatterns = {CREATE_WORKING_ROTA})
public class CreateWorkingRota extends HttpServlet {

    @EJB
    private VetFacade vetFacade;

    @EJB
    private ClinicUserFacade clinicUserFacade;

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
        List<Vet> vets = vetFacade.findAll();
        request.setAttribute("vets", vets);

        LocalDate currentDate = LocalDate.now();
        // If today is not Monday, find the next Monday
        if (!currentDate.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            currentDate = currentDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        }

        // Initialize a list to store the dates
        List<String> weekDates = new ArrayList<>();

        // Start from the next Monday after the current date and add 7 days successively until having 4 dates
        for (int i = 1; i <= 4; i++) {
            String formattedDate = convertDateFormat(currentDate.toString(), DMY_SLASH_DATE_FORMAT);
            weekDates.add(formattedDate);
            currentDate = currentDate.plusDays(7); // Move to the next Monday
        }
        request.setAttribute("weekDates", weekDates);
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

        String weekDate = request.getParameter("weekDate");

        // 1) Get the working days of each vet.
        Enumeration<String> parameterNames = request.getParameterNames();
        Map<String, List<String>> vetWorkingDaysMap = new HashMap<>();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            if (paramName.contains("_")) {
                String[] parts = paramName.split("_");
                String clinicUserId = parts[0];
                String day = parts[1];
                if (request.getParameter(paramName) != null) {
                    List<String> workingDays = vetWorkingDaysMap.computeIfAbsent(clinicUserId, k -> new ArrayList<>());
                    workingDays.add(day);
                }
            }
        }

        // 2) Map the day number (e.g. 0, 1 ... 7) into dates.
        SimpleDateFormat sdf = new SimpleDateFormat(DMY_SLASH_DATE_FORMAT, Locale.ENGLISH);
        Date weekStartDate = null;
        try {
            weekStartDate = sdf.parse(weekDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(weekStartDate);
        Map<Integer, String> dayToDateMap = new HashMap<>();
        for (int i = 1; i <= 7; i++) {
            dayToDateMap.put(i, sdf.format(calendar.getTime()));
            calendar.add(Calendar.DATE, 1); // Move to the next day
        }

        // 3)
        for (Map.Entry<String, List<String>> entry : vetWorkingDaysMap.entrySet()) {
            String clinicUserId = entry.getKey();
            List<String> workingDaysNumeric = entry.getValue();
            List<String> workingDaysActualDates = new ArrayList<>();

            // Convert the numeric day to the actual date
            for (String dayNum : workingDaysNumeric) {
                workingDaysActualDates.add(dayToDateMap.get(Integer.parseInt(dayNum)));
            }

            System.out.println(clinicUserId);
            System.out.println(workingDaysActualDates);

            // Fetch the vet and update the working days
            Vet vet = vetFacade.find(clinicUserId);
            if (vet != null) {
//                vet.setWorkingDays(workingDaysActualDates);
//                vetFacade.edit(vet);
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
