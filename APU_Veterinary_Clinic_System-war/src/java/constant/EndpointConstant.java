package constant;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author Jackson Tai
 */
public class EndpointConstant {

    // Sub-path
    public static final String PET = "/pet";
    public static final String ASSET = "/asset";
    public static final String STAFF = "/staff";
    public static final String CUSTOMER = "/customer";
    public static final String APPOINTMENT = "/appointment";
    public static final String WORKING_ROTA = "/workingRota";
    public static final String REPORT = "/report";
    public static final String PROFILE = "/profile";
    public static final String APPROVAL = "/approval";
    public static final String EXPERTISE = "/expertise";
    public static final String STAFF_APPROVAL = STAFF + APPROVAL;
    public static final String MEDICAL_RECORD = "/medicalRecord";
    public static final String PET_MEDICAL_RECORD = PET + MEDICAL_RECORD;

    public static final String WAR_ROOT = "/APU_Veterinary_Clinic_System-war";
    public static final String LOGOUT = "/logout";
    public static final String REGISTRATION_SUCCESS = "/registrationSuccess";

    public static final String VIEW_PROFILE = PROFILE + "/viewProfile";
    public static final String UPDATE_PROFILE = PROFILE + "/updateProfile";
    public static final String CHANGE_PASSWORD = PROFILE + "/changePassword";

    public static final String STAFF_HOME = STAFF + "/home";
    public static final String STAFF_LOGIN = STAFF + "/login";
    public static final String STAFF_REGISTER = STAFF + "/register";

    public static final String VIEW_APPOINTMENT = APPOINTMENT + "/viewAppointment";
    public static final String VIEW_APPOINTMENTS = APPOINTMENT + "/viewAppointments";
    public static final String CREATE_APPOINTMENT = APPOINTMENT + "/createAppointment";
    public static final String UPDATE_APPOINTMENT = APPOINTMENT + "/updateAppointment";

    public static final String VIEW_CUSTOMER = CUSTOMER + "/viewCustomer";
    public static final String VIEW_CUSTOMERS = CUSTOMER + "/viewCustomers";
    public static final String UPDATE_CUSTOMER = CUSTOMER + "/updateCustomer";
    public static final String DELETE_CUSTOMER = CUSTOMER + "/deleteCustomer";
    public static final String CREATE_CUSTOMER = CUSTOMER + "/createCustomer";
    public static final String SEARCH_CUSTOMER = CUSTOMER + "/searchCustomer";

    public static final String VIEW_PET = PET + "/viewPet";
    public static final String VIEW_PETS = PET + "/viewPets";
    public static final String DELETE_PET = PET + "/deletePet";
    public static final String CREATE_PET = PET + "/createPet";
    public static final String UPDATE_PET = PET + "/updatePet";

    public static final String CREATE_PET_MEDICAL_RECORD = PET_MEDICAL_RECORD + "/createMedicalRecord";

    public static final String VIEW_WORKING_ROTA = WORKING_ROTA + "/viewWorkingRota";
    public static final String CREATE_WORKING_ROTA = WORKING_ROTA + "/createWorkingRota";

    public static final String VIEW_EXPERTISE = EXPERTISE + "/viewExpertise";
    public static final String VIEW_EXPERTISES = EXPERTISE + "/viewExpertises";
    public static final String DELETE_EXPERTISE = EXPERTISE + "/deleteExpertise";
    public static final String CREATE_EXPERTISE = EXPERTISE + "/createExpertise";

    public static final String DELETE_STAFF = STAFF + "/deleteStaff";
    public static final String VIEW_STAFF = STAFF + "/viewStaff";
    public static final String VIEW_STAFFS = STAFF + "/viewStaffs";
    public static final String CREATE_STAFF = STAFF + "/createStaff";
    public static final String UPDATE_STAFF = STAFF + "/updateStaff";
    public static final String SEARCH_STAFF = STAFF + "/searchStaff";

    public static final String VIEW_STAFF_APPROVAL = STAFF_APPROVAL + "/viewStaffApproval";
    public static final String VIEW_STAFF_APPROVALS = STAFF_APPROVAL + "/viewStaffApprovals";
    public static final String UPDATE_STAFF_APPROVAL = STAFF_APPROVAL + "/updateStaffApproval";

    public static final String VIEW_CUSTOMER_REPORT = REPORT + CUSTOMER + "/viewCustomerReport";
    public static final String VIEW_APPOINTMENT_REPORT = REPORT + APPOINTMENT + "/viewAppointmentReport";
    public static final String VIEW_STAFF_REPORT = REPORT + STAFF + "/viewStaffReport";

}
