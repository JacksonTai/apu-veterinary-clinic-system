package service;

import constant.UserRole;
import entity.ClinicUser;
import entity.ManagingStaff;
import entity.Receptionist;
import entity.Vet;
import model.CreateStaffResponseModel;
import org.mindrot.jbcrypt.BCrypt;
import repository.ClinicUserFacade;
import validator.ClinicUserValidator;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class StaffServiceImpl implements StaffService {

    ClinicUserFacade clinicUserFacade;

    public StaffServiceImpl(ClinicUserFacade clinicUserFacade) {
        this.clinicUserFacade = clinicUserFacade;
    }

    @Override
    public CreateStaffResponseModel createStaff(String fullName, String email, String password, String userRole) {

        CreateStaffResponseModel response = new CreateStaffResponseModel();
        ClinicUser clinicUser;
        switch (userRole) {
            case UserRole.VET:
                clinicUser = new Vet(email, password, fullName);
                break;
            case UserRole.RECEPTIONIST:
                clinicUser = new Receptionist(email, password, fullName);
                break;
            case UserRole.MANAGING_STAFF:
                clinicUser = new ManagingStaff(email, password, fullName);
                break;
            default:
                clinicUser = null;
                break;
        }

        if (clinicUser != null) {
            clinicUser.setUserRole(userRole);
            ClinicUserValidator clinicUserValidator = new ClinicUserValidator(clinicUserFacade);
            Map<String, String> errorMessages = clinicUserValidator.validateClinicUserDetails(clinicUser);

            if (!errorMessages.isEmpty()) {
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                response.setErrorMessages(errorMessages);
            } else {
                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
                clinicUser.setPassword(hashedPassword);
                clinicUserFacade.create(clinicUser);
                response.setClinicUser(clinicUser);
                response.setStatusCode(HttpServletResponse.SC_CREATED);
            }
        }
        return response;
    }

}
