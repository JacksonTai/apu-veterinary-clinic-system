package service.staff;

import com.fasterxml.jackson.databind.ObjectMapper;
import constant.ClinicUserStatus;
import constant.MakerCheckerConstant;
import constant.UserRole;
import entity.*;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import model.staff.CreateStaffResponseModel;
import model.staff.SearchStaffResponseModel;
import org.mindrot.jbcrypt.BCrypt;
import repository.ClinicUserFacade;
import repository.MakerCheckerFacade;
import validator.ClinicUserValidator;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

import static constant.i18n.En.EMPTY_SEARCH_INPUT_MESSAGE;
import static constant.i18n.En.STAFF_NOT_FOUND_MESSAGE;

@AllArgsConstructor
public class StaffServiceImpl implements StaffService {

    private ClinicUserFacade clinicUserFacade;
    private MakerCheckerFacade makerCheckerFacade;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public StaffServiceImpl(ClinicUserFacade clinicUserFacade) {
        this.clinicUserFacade = clinicUserFacade;
    }

    @Override
    @SneakyThrows
    public CreateStaffResponseModel createStaff(String fullName, String email, String password, String userRole,
                                                boolean isMakerCheckerEnabled) {
        CreateStaffResponseModel response = createStaff(fullName, email, password, userRole);

        if (isMakerCheckerEnabled && response.getStatusCode() == HttpServletResponse.SC_CREATED) {
            ClinicUser createdClinicUser = response.getClinicUser();
            createdClinicUser.setStatus(ClinicUserStatus.PENDING_APPROVAL);
            clinicUserFacade.edit(createdClinicUser);
            String currentValue = objectMapper.writeValueAsString(createdClinicUser);
            createdClinicUser.setStatus(ClinicUserStatus.APPROVED);
            String newValue = objectMapper.writeValueAsString(createdClinicUser);
            makerCheckerFacade.create(new MakerChecker(createdClinicUser.getClinicUserId(), null,
                    MakerCheckerConstant.Module.ACCOUNT.toString(),
                    MakerCheckerConstant.ActionType.CREATE.toString(), currentValue, newValue,
                    MakerCheckerConstant.Status.PENDING.toString()));
            response.setClinicUser(createdClinicUser);
            response.setStatusCode(HttpServletResponse.SC_CREATED);
        }
        return response;
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
                clinicUser.setStatus(ClinicUserStatus.APPROVED);
                clinicUserFacade.create(clinicUser);
                response.setClinicUser(clinicUserFacade.findByFullName(fullName));
                response.setStatusCode(HttpServletResponse.SC_CREATED);
            }
        }
        return response;
    }

    @Override
    public SearchStaffResponseModel searchStaff(String input) {
        SearchStaffResponseModel response = new SearchStaffResponseModel();
        if (input.trim().isEmpty()) {
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            response.setMessage(EMPTY_SEARCH_INPUT_MESSAGE);
            return response;
        }

        Optional<ClinicUser> staff = clinicUserFacade.findByIdOrFullNameOrEmail(input);
        if (staff.isPresent()) {
            response.setClinicUser(staff.get());
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setStatusCode(HttpServletResponse.SC_NOT_FOUND);
            response.setMessage(STAFF_NOT_FOUND_MESSAGE);
        }
        return response;
    }

}
