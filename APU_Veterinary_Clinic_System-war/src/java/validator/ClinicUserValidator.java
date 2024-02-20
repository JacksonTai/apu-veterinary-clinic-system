package validator;

import entity.ClinicUser;
import entity.ClinicUserFacade;
import exception.InvalidLoginException;
import org.mindrot.jbcrypt.BCrypt;

import javax.ejb.EJB;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import static constant.EndpointConstant.STAFF_LOGIN;
import static constant.GlobalConstant.*;
import static constant.i18n.En.*;
import static util.StringUtil.containsExcessiveWhitespace;
import static util.StringUtil.containsNumeric;

public class ClinicUserValidator implements Validator<ClinicUser> {

    @EJB
    private ClinicUserFacade clinicUserFacade;

    public ClinicUserValidator(ClinicUserFacade clinicUserFacade) {
        this.clinicUserFacade = clinicUserFacade;
    }

    @Override
    public Map<String, String> validate(ClinicUser clinicUser) {
        Map<String, String> errorMessages = new HashMap<>();

        String fullName = clinicUser.getFullName().trim();
        if (fullName.isEmpty()) {
            errorMessages.put("fullNameError", EMPTY_FULL_NAME_MESSAGE);
        } else if (fullName.length() < 8 || fullName.length() > 50) {
            errorMessages.put("fullNameError", INVALID_LENGTH_FULL_NAME_MESSAGE);
        } else if (containsExcessiveWhitespace(fullName)) {
            errorMessages.put("fullNameError", EXCESSIVE_WHITESPACE_FULL_NAME_MESSAGE);
        } else if (containsNumeric(fullName)) {
            errorMessages.put("fullNameError", NUMERIC_FULL_NAME_MESSAGE);
        }

        String phoneNumber = clinicUser.getPhoneNumber().trim();
        if (phoneNumber.isEmpty()) {
            errorMessages.put("phoneNumberError", EMPTY_PHONE_NUMBER_MESSAGE);
        } else if (!Pattern.matches(MY_PHONE_REGEX, phoneNumber)) {
            errorMessages.put("phoneNumberError", INVALID_PHONE_NUMBER_MESSAGE);
        }

        String email = clinicUser.getEmail().trim();
        if (email.isEmpty()) {
            errorMessages.put("emailError", EMPTY_EMAIL_MESSAGE);
        } else if (!email.matches(STAFF_EMAIL_REGEX)) {
            errorMessages.put("emailError", INVALID_STAFF_EMAIL_MESSAGE);
        }

        String password = clinicUser.getPassword().trim();
        if (password.isEmpty()) {
            errorMessages.put("passwordError", EMPTY_PASSWORD_MESSAGE);
        } else if (!password.matches(PASSWORD_REGEX)) {
            errorMessages.put("passwordError", INVALID_PASSWORD_MESSAGE);
        }

        return errorMessages;
    }

    public Map<String, String> validateLogin(String email, String password) {
        Map<String, String> errorMessages = new HashMap<>();
        if (email.isEmpty()) {
            errorMessages.put("emailError", EMPTY_EMAIL_MESSAGE);
        }
        if (password.isEmpty()) {
            errorMessages.put("passwordError", EMPTY_PASSWORD_MESSAGE);
        }
        return errorMessages;
    }

    public ClinicUser validateLoginCredential(String email, String password) {
        ClinicUser clinicUser = clinicUserFacade.findByEmail(email);
        if (clinicUser != null && BCrypt.checkpw(password, clinicUser.getPassword())) {
            return clinicUser;
        }
        throw new InvalidLoginException(INVALID_LOGIN_MESSAGE);
    }

    public Map<String, String> validateRegistration(ClinicUser clinicUser) {
        Map<String, String> errorMessages = validate(clinicUser);

        if (errorMessages.isEmpty()) {
            String fullName = clinicUser.getFullName().trim();
            String phoneNumber = clinicUser.getPhoneNumber().trim();
            String email = clinicUser.getEmail().trim();
             
            ClinicUser existingUser = clinicUserFacade.findByFullName(fullName);
            if (existingUser != null) {
                errorMessages.put("fullNameError", DUPLICATE_FULL_NAME_MESSAGE);
            }

            existingUser = clinicUserFacade.findByPhoneNumber(phoneNumber);
            if (existingUser != null) {
                errorMessages.put("phoneNumberError", DUPLICATE_PHONE_NUMBER_MESSAGE);
            }

            existingUser = clinicUserFacade.findByEmail(email);
            if (existingUser != null) {
                errorMessages.put("emailError", DUPLICATE_EMAIL_MESSAGE);
            }
        }
        return errorMessages;
    }
}
