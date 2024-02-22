package constant;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jackson Tai
 */
public class EndpointConstant {

    // Sub-path
    public static final String STAFF = "/staff";
    public static final String CUSTOMER = "/customer";
    public static final String APPOINTMENT = "/appointment";
    public static final String PET = "/pet";
    public static final String WORKING_ROTA = "/workingRota";
    public static final String REPORT = "/report";
    public static final String IAM = "/iam";
    public static final String PROFILE = "/profile";

    public static final String LOGOUT = "/logout";
    public static final String REGISTRATION_SUCCESS = "/registrationSuccess";

    public static final String VIEW_PROFILE = PROFILE + "/viewProfile";
    public static final String UPDATE_PROFILE = PROFILE + "/updateProfile";
    public static final String CHANGE_PASSWORD = PROFILE + "/changePassword";

    public static final String STAFF_HOME = STAFF + "/home";
    public static final String STAFF_LOGIN = STAFF + "/login";
    public static final String STAFF_REGISTER = STAFF + "/register";

    public static final String VIEW_APPOINTMENT = APPOINTMENT + "/viewAppointment";
    public static final String DELETE_APPOINTMENT = APPOINTMENT + "/deleteAppointment";
    public static final String CREATE_APPOINTMENT = APPOINTMENT + "/createAppointment";
    public static final String UPDATE_APPOINTMENT = APPOINTMENT + "/updateAppointment";

    public static final String VIEW_CUSTOMER = CUSTOMER + "/viewCustomer";
    public static final String VIEW_CUSTOMERS = CUSTOMER + "/viewCustomers";
    public static final String UPDATE_CUSTOMER = CUSTOMER + "/updateCustomer";
    public static final String DELETE_CUSTOMER = CUSTOMER + "/deleteCustomer";
    public static final String CREATE_CUSTOMER = CUSTOMER + "/createCustomer";

    public static final String VIEW_IAM = IAM + "/viewIam";

    public static final String VIEW_PET = PET + "/viewPet";
    public static final String DELETE_PET = PET + "/deletePet";
    public static final String CREATE_PET = PET + "/createPet";
    public static final String UPDATE_PET = PET + "/updatePet";

    public static final String VIEW_WORKING_ROTA = WORKING_ROTA + "/viewWorkingRota";

    public static final String REVENUE = "/revenue";
    public static final String CUSTOMER_REPORT = "/report";

}
