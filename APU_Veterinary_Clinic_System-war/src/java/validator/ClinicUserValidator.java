package validator;

import entity.ClinicUser;
import org.mindrot.jbcrypt.BCrypt;
import repository.ClinicUserFacade;

import javax.ejb.EJB;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static constant.GlobalConstant.*;
import static constant.i18n.En.*;
import static util.StringUtil.containsExcessiveWhitespace;

public class ClinicUserValidator implements Validator<ClinicUser> {

    @EJB
    private ClinicUserFacade clinicUserFacade;

    public ClinicUserValidator(ClinicUserFacade clinicUserFacade) {
        this.clinicUserFacade = clinicUserFacade;
    }

    @Override
    public Map<String, String> validate(ClinicUser clinicUser) {
        Map<String, String> errorMessages = new HashMap<>();
        errorMessages.putAll(validateEmail(clinicUser.getEmail()));
        errorMessages.putAll(validatePassword(clinicUser.getPassword()));
        errorMessages.putAll(validateFullName(clinicUser.getFullName()));
        return errorMessages;
    }

    public static Map<String, String> validateEmail(String email) {
        Map<String, String> errorMessages = new HashMap<>();
        email = email.trim().toLowerCase();
        if (email.isEmpty()) {
            errorMessages.put("emailError", EMPTY_EMAIL_MESSAGE);
        } else if (!email.matches(STAFF_EMAIL_REGEX)) {
            errorMessages.put("emailError", INVALID_EMAIL_MESSAGE);
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

    public static Map<String, String> validateFullName(String fullName) {
        Map<String, String> errorMessages = new HashMap<>();
        fullName = fullName.trim();
        if (fullName.isEmpty()) {
            errorMessages.put("fullNameError", EMPTY_FULL_NAME_MESSAGE);
        } else if (fullName.length() < 8 || fullName.length() > 50) {
            errorMessages.put("fullNameError", INVALID_FULL_NAME_LENGTH_MESSAGE);
        } else if (containsExcessiveWhitespace(fullName)) {
            errorMessages.put("fullNameError", EXCESSIVE_WHITESPACE_FULL_NAME_MESSAGE);
        } else if (!Pattern.matches(ALPHABET_WHITESPACE_ONLY_REGEX, fullName)) {
            errorMessages.put("fullNameError", INVALID_FULL_NAME_CHARACTER_MESSAGE);
        }
        return errorMessages;
    }

    public Map<String, String> validateCredentials(String email, String password) {
        Map<String, String> errorMessages = new HashMap<>();
        email = email.trim().toLowerCase();
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
        ClinicUser clinicUser = clinicUserFacade.findByEmail(email.trim().toLowerCase());
        return clinicUser != null && BCrypt.checkpw(password, clinicUser.getPassword()) ? clinicUser : null;
    }

    public Map<String, String> validateClinicUserDetails(ClinicUser clinicUser) {
        Map<String, String> errorMessages = validate(clinicUser);
        errorMessages.putAll(validateDuplicateEmail(clinicUser.getEmail()));
        errorMessages.putAll(validateDuplicateFullName(clinicUser.getFullName()));
        return errorMessages;
    }

    public Map<String, String> validateDuplicateEmail(String email) {
        Map<String, String> errorMessages = new HashMap<>();
        ClinicUser existingClinicUser = clinicUserFacade.findByEmail(email.trim().toLowerCase());
        if (existingClinicUser != null) {
            errorMessages.put("emailError", DUPLICATE_EMAIL_MESSAGE);
        }
        return errorMessages;
    }

    public Map<String, String> validateDuplicateFullName(String fullName) {
        Map<String, String> errorMessages = new HashMap<>();
        ClinicUser existingClinicUser = clinicUserFacade.findByFullName(fullName.trim().toLowerCase());
        if (existingClinicUser != null) {
            errorMessages.put("fullNameError", DUPLICATE_FULL_NAME_MESSAGE);
        }
        return errorMessages;
    }

}
