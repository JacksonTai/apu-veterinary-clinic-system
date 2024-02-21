package validator;

import entity.ClinicUser;
import entity.ClinicUserFacade;
import org.mindrot.jbcrypt.BCrypt;

import javax.ejb.EJB;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

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
        errorMessages.putAll(validateFullName(clinicUser.getFullName()));
        errorMessages.putAll(validatePhoneNumber(clinicUser.getPhoneNumber()));
        errorMessages.putAll(validateEmail(clinicUser.getEmail()));
        errorMessages.putAll(validatePassword(clinicUser.getPassword()));
        return errorMessages;
    }

    public static Map<String, String> validateFullName(String fullName) {
        Map<String, String> errorMessages = new HashMap<>();
        fullName = fullName.trim();
        if (fullName.isEmpty()) {
            errorMessages.put("fullNameError", EMPTY_FULL_NAME_MESSAGE);
        } else if (fullName.length() < 8 || fullName.length() > 50) {
            errorMessages.put("fullNameError", INVALID_LENGTH_FULL_NAME_MESSAGE);
        } else if (containsExcessiveWhitespace(fullName)) {
            errorMessages.put("fullNameError", EXCESSIVE_WHITESPACE_FULL_NAME_MESSAGE);
        } else if (containsNumeric(fullName)) {
            errorMessages.put("fullNameError", NUMERIC_FULL_NAME_MESSAGE);
        }
        return errorMessages;
    }

    public static Map<String, String> validatePhoneNumber(String phoneNumber) {
        Map<String, String> errorMessages = new HashMap<>();
        phoneNumber = phoneNumber.trim();
        if (phoneNumber.isEmpty()) {
            errorMessages.put("phoneNumberError", EMPTY_PHONE_NUMBER_MESSAGE);
        } else if (!Pattern.matches(MY_PHONE_REGEX, phoneNumber)) {
            errorMessages.put("phoneNumberError", INVALID_PHONE_NUMBER_MESSAGE);
        }
        return errorMessages;
    }

    public static Map<String, String> validateEmail(String email) {
        Map<String, String> errorMessages = new HashMap<>();
        email = email.trim();
        if (email.isEmpty()) {
            errorMessages.put("emailError", EMPTY_EMAIL_MESSAGE);
        } else if (!email.matches(STAFF_EMAIL_REGEX)) {
            errorMessages.put("emailError", INVALID_STAFF_EMAIL_MESSAGE);
        }
        return errorMessages;
    }

    public static Map<String, String> validatePassword(String password) {
        Map<String, String> errorMessages = new HashMap<>();
        password = password.trim();
        if (password.isEmpty()) {
            errorMessages.put("passwordError", EMPTY_PASSWORD_MESSAGE);
        } else if (!password.matches(PASSWORD_REGEX)) {
            errorMessages.put("passwordError", INVALID_PASSWORD_MESSAGE);
        }
        return errorMessages;
    }

    public Map<String, String> validateCredentials(String email, String password) {
        Map<String, String> errorMessages = new HashMap<>();
        email = email.trim();
        password = password.trim();
        if (email.isEmpty()) {
            errorMessages.put("emailError", EMPTY_EMAIL_MESSAGE);
        }
        if (password.isEmpty()) {
            errorMessages.put("passwordError", EMPTY_PASSWORD_MESSAGE);
        }
        return errorMessages;
    }

    public Map<String, String> validateCredentialDetails(String email, String password) {
        Map<String, String> errorMessages = new HashMap<>();
        password = password.trim();
        if (password.isEmpty()) {
            errorMessages.put("invalidCredentialError", EMPTY_PASSWORD_MESSAGE);
        } else if (authenticateClinicUser(email, password) == null) {
            errorMessages.put("invalidCredentialError", INVALID_CREDENTIAL_MESSAGE);
        }
        return errorMessages;
    }

    public ClinicUser authenticateClinicUser(String email, String password) {
        ClinicUser clinicUser = clinicUserFacade.findByEmail(email.toLowerCase());
        return clinicUser != null && BCrypt.checkpw(password, clinicUser.getPassword()) ? clinicUser : null;
    }

    public Map<String, String> validateClinicUserDetails(ClinicUser clinicUser) {
        Map<String, String> errorMessages = validate(clinicUser);
        errorMessages.putAll(validateDuplicateEmail(clinicUser.getEmail()));
        errorMessages.putAll(validateDuplicatePhoneNumber(clinicUser.getPhoneNumber()));
        errorMessages.putAll(validateDuplicateFullName(clinicUser.getFullName()));
        return errorMessages;
    }

    public Map<String, String> validateDuplicateFullName(String fullName) {
        Map<String, String> errorMessages = new HashMap<>();
        ClinicUser existingUser = clinicUserFacade.findByFullName(fullName.trim());
        if (existingUser != null) {
            errorMessages.put("fullNameError", DUPLICATE_FULL_NAME_MESSAGE);
        }
        return errorMessages;
    }

    public Map<String, String> validateDuplicatePhoneNumber(String phoneNumber) {
        Map<String, String> errorMessages = new HashMap<>();
        ClinicUser existingUser = clinicUserFacade.findByPhoneNumber(phoneNumber.trim());
        if (existingUser != null) {
            errorMessages.put("phoneNumberError", DUPLICATE_PHONE_NUMBER_MESSAGE);
        }
        return errorMessages;
    }

    public Map<String, String> validateDuplicateEmail(String email) {
        Map<String, String> errorMessages = new HashMap<>();
        ClinicUser existingUser = clinicUserFacade.findByEmail(email.trim());
        if (existingUser != null) {
            errorMessages.put("emailError", DUPLICATE_EMAIL_MESSAGE);
        }
        return errorMessages;
    }

}
