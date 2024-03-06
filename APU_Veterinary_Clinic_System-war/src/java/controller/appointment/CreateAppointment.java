/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.appointment;

import constant.AppointmentStatus;
import entity.*;
import model.appointment.createForm.AppointmentDetailModel;
import model.appointment.createForm.AssignVetModel;
import repository.AppointmentFacade;
import repository.CustomerFacade;
import repository.ExpertiseFacade;
import repository.VetFacade;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static constant.EndpointConstant.CREATE_APPOINTMENT;
import static constant.EndpointConstant.VIEW_APPOINTMENT;
import static constant.i18n.En.*;

import repository.PetFacade;

import static util.DateUtil.*;

/**
 * @author Jackson Tai
 */
@WebServlet(name = "CreateAppointment", urlPatterns = {CREATE_APPOINTMENT})
public class CreateAppointment extends HttpServlet {

    @EJB
    private PetFacade petFacade;

    @EJB
    private CustomerFacade customerFacade;

    @EJB
    private ExpertiseFacade expertiseFacade;

    @EJB
    private VetFacade vetFacade;

    @EJB
    private AppointmentFacade appointmentFacade;

    private static String STEP;
    private static String petId;
    private static Customer customer;
    private static List<Vet> vets = new ArrayList<>();
    private static List<Pet> availablePets = new ArrayList<>();
    private static List<Expertise> expertises = new ArrayList<>();
    private static final AppointmentDetailModel appointmentDetailModel = new AppointmentDetailModel();
    private static final AssignVetModel assignVetModel = new AssignVetModel();
    private static final List<LocalDate> weeks = new ArrayList<>();
    private static final String ASSIGN_VET = "assignVet";
    private static final String APPOINTMENT_DETAILS = "appointmentDetails";

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

        syncWeeksAndWeekDate();
        syncExpertises();
        if (customer != null) {
            syncCustomerDetails(customer.getCustomerId());
        }

        request.setAttribute("weeks", weeks);
        request.setAttribute("expertises", expertises);
        request.setAttribute("customers", customerFacade.findAll());

        String step = request.getParameter("step");
        request.setAttribute("step", STEP = step != null && (step.equals(APPOINTMENT_DETAILS) ||
                step.equals(ASSIGN_VET)) ? step : APPOINTMENT_DETAILS);
        request.getRequestDispatcher(CREATE_APPOINTMENT + ".jsp").forward(request, response);
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
        HttpSession session = request.getSession();

        if (STEP.equals(APPOINTMENT_DETAILS)) {
            String customerId = request.getParameter("customerId");
            String customerDetails = request.getParameter("customerDetails");
            petId = request.getParameter("petId");

            syncWeeksAndWeekDate();
            syncExpertises();
            syncCustomerDetails(customerId);

            request.setAttribute("weeks", weeks);
            request.setAttribute("expertises", expertises);

            // Get the selected expertises
            List<Expertise> selectedExpertises = new ArrayList<>();
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

            // Get the available vets for the selected expertises and week
            LocalDate week;
            try {
                week = LocalDate.parse(request.getParameter("week"));
            } catch (Exception e) {
                week = weeks.get(0);
            }
            List<LocalDate> weekDates = generateWeekDates(week);
            Map<LocalDate, List<Vet>> dateToVetsMap = new LinkedHashMap<>();
            for (LocalDate date : weekDates) {
                List<Vet> availableVets = new ArrayList<>();
                for (Vet vet : vets) {
                    if (vet.getWorkingDays().contains(date.toString()) && !selectedExpertises.isEmpty() &&
                            new HashSet<>(vet.getExpertises()).containsAll(selectedExpertises)) {
                        availableVets.add(vet);
                    }
                }
                dateToVetsMap.put(date, availableVets);
            }

            // Keep track of appointment details and assign vet steps' input using session
            appointmentDetailModel.setCustomerDetails(customerDetails);
            appointmentDetailModel.setCustomer(customer);
            appointmentDetailModel.setAvailablePets(availablePets);
            appointmentDetailModel.setSelectedPetId(petId);
            appointmentDetailModel.setSelectedWeek(week);
            appointmentDetailModel.setSelectedExpertises(selectedExpertises);
            session.setAttribute(APPOINTMENT_DETAILS, appointmentDetailModel);

            Map<String, String> errorMessages = new HashMap<>();
            if (selectedExpertises.isEmpty()) {
                errorMessages.put("expertisesError", EMPTY_EXPERTISE_SELECTION);
            }
            if (customerDetails == null || customerDetails.trim().isEmpty()) {
                errorMessages.put("customerDetailsError", EMPTY_CUSTOMER_DETAILS_MESSAGE);
            } else if ((customerFacade.findByIdOrFullNameOrEmailOrPhoneNumber(customerDetails.trim()).isPresent() &&
                    customer == null) || customer == null) {
                errorMessages.put("customerDetailsError", CONFIRM_CUSTOMER_DETAILS_MESSAGE);
            }
            if (petId == null) {
                errorMessages.put("petSelectError", EMPTY_PET_SELECTION);
            }
            if (!errorMessages.isEmpty()) {
                errorMessages.forEach(request::setAttribute);
                request.getRequestDispatcher(CREATE_APPOINTMENT + ".jsp").forward(request, response);
                return;
            }
            assignVetModel.setDateToVetsMap(dateToVetsMap);
            assignVetModel.setNoAvailableVets(dateToVetsMap.values().stream().allMatch(List::isEmpty));
            assignVetModel.setSelectedExpertises(selectedExpertises);
            session.setAttribute(ASSIGN_VET, assignVetModel);
            response.sendRedirect(request.getContextPath() + CREATE_APPOINTMENT + "?step=" + ASSIGN_VET);
        }

        if (STEP.equals(ASSIGN_VET)) {

            Vet vet = null;
            String appointmentDate = null;
            String selectedVet = request.getParameter("selectedVet");

            if (selectedVet == null) {
                session.setAttribute("assignVetError", EMPTY_VET_SELECTION);
            }
            if (selectedVet != null) {
                session.removeAttribute("assignVetError");
                String[] parts = selectedVet.split("_");
                vet = vetFacade.find(parts[0]);
                appointmentDate = parts[1];

                Optional<Appointment> appointment = appointmentFacade.findByDateAndCustomerAndPetAndVetAndStatus
                        (appointmentDate, customer.getCustomerId(), petId, vet.getClinicUserId(),
                                AppointmentStatus.SCHEDULED);
                appointment.ifPresent(a -> session.setAttribute("assignVetError", DUPLICATE_APPOINTMENT));
                if (!appointment.isPresent()) {
                    session.removeAttribute("assignVetError");
                }
            }
            if (session.getAttribute("assignVetError") != null) {
                response.sendRedirect(request.getContextPath() + CREATE_APPOINTMENT + "?step=" + ASSIGN_VET);
                return;
            }

            Appointment appointment = new Appointment(appointmentDate, vet, customer, petFacade.find(petId));
            appointmentFacade.create(appointment);
            request.getSession().removeAttribute(APPOINTMENT_DETAILS);
            response.sendRedirect(request.getContextPath() + VIEW_APPOINTMENT + "?id=" +
                    appointment.getAppointmentId());
        }

    }

    private void syncWeeksAndWeekDate() {
        vets = vetFacade.findAll();
        List<LocalDate> nextFourMondays = getNextWeekMondayDates(4);
        for (LocalDate monday : nextFourMondays) {
            for (Vet vet : vets) {
                if (vet.getWorkingDays().contains(monday.toString()) && !weeks.contains(monday)) {
                    weeks.add(monday);
                    break;
                }
            }
        }
    }

    private void syncExpertises() {
        expertises = expertiseFacade.findAll();
    }

    private void syncCustomerDetails(String customerId) {
        if (customerId != null) {
            customer = customerFacade.find(customerId);
            if (customer != null) {
                availablePets = customer.getPets();
                appointmentDetailModel.setAvailablePets(availablePets);
                if (availablePets == null || availablePets.isEmpty()) {
                    appointmentDetailModel.setSelectedPetId(null);
                }
            }
            if (customer == null) {
                resetCustomerDetails();
            }
        } else {
            resetCustomerDetails();
        }
    }

    private static void resetCustomerDetails() {
        customer = null;
        availablePets = new ArrayList<>();
        appointmentDetailModel.setCustomerDetails("");
        appointmentDetailModel.setCustomer(customer);
        appointmentDetailModel.setAvailablePets(availablePets);
        appointmentDetailModel.setSelectedPetId(null);
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
