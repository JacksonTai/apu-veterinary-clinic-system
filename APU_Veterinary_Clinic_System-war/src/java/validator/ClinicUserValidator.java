package validator;

import constant.ClinicUserStatus;
import constant.GlobalConstant;
import constant.i18n.En;
import entity.ClinicUser;
import org.mindrot.jbcrypt.BCrypt;
import repository.ClinicUserFacade;
import util.StringUtil;

import javax.ejb.EJB;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

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
            errorMessages.put("emailError", En.EMPTY_EMAIL_MESSAGE);
        } else if (!email.matches(GlobalConstant.STAFF_EMAIL_REGEX)) {
            errorMessages.put("emailError", En.INVALID_EMAIL_MESSAGE);
        }
        return errorMessages;
    }

    public static Map<String, String> validatePassword(String password) {
        Map<String, String> errorMessages = new HashMap<>();
        password = password.trim();
        if (password.isEmpty()) {
            errorMessages.put("passwordError", En.EMPTY_PASSWORD_MESSAGE);
        } else if (!password.matches(GlobalConstant.PASSWORD_REGEX)) {
            errorMessages.put("passwordError", En.INVALID_PASSWORD_MESSAGE);
        }
        return errorMessages;
    }

    public static Map<String, String> validateFullName(String fullName) {
        Map<String, String> errorMessages = new HashMap<>();
        fullName = fullName.trim();
        if (fullName.isEmpty()) {
            errorMessages.put("fullNameError", En.EMPTY_FULL_NAME_MESSAGE);
        } else if (fullName.length() < 8 || fullName.length() > 50) {
            errorMessages.put("fullNameError", En.INVALID_FULL_NAME_LENGTH_MESSAGE);
        } else if (StringUtil.containsExcessiveWhitespace(fullName)) {
            errorMessages.put("fullNameError", En.EXCESSIVE_WHITESPACE_FULL_NAME_MESSAGE);
        } else if (!Pattern.matches(GlobalConstant.ALPHABET_WHITESPACE_ONLY_REGEX, fullName)) {
            errorMessages.put("fullNameError", En.INVALID_FULL_NAME_CHARACTER_MESSAGE);
        }
        return errorMessages;
    }

    public Map<String, String> validateCredentials(String email, String password) {
        Map<String, String> errorMessages = new HashMap<>();
        email = email.trim().toLowerCase();
        password = password.trim();
        if (email.isEmpty()) {
            errorMessages.put("emailError", En.EMPTY_EMAIL_MESSAGE);
        }
        if (password.isEmpty()) {
            errorMessages.put("passwordError", En.EMPTY_PASSWORD_MESSAGE);
        }
        return errorMessages;
    }

    public Map<String, String> validateCredentialDetails(String email, String password) {
        Map<String, String> errorMessages = new HashMap<>();
        password = password.trim();
        if (password.isEmpty()) {
            errorMessages.put("invalidCredentialError", En.EMPTY_PASSWORD_MESSAGE);
        } else if (authenticateClinicUser(email, password) == null) {
            errorMessages.put("invalidCredentialError", En.INVALID_CREDENTIAL_MESSAGE);
        }
        return errorMessages;
    }

    public ClinicUser authenticateClinicUser(String email, String password) {
        ClinicUser clinicUser = clinicUserFacade.findByEmailAndStatus(email.trim().toLowerCase(),
                ClinicUserStatus.APPROVED);
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
        ClinicUser existingClinicUser = clinicUserFacade.findByEmailAndStatus(email.trim().toLowerCase(),
                ClinicUserStatus.APPROVED);
        if (existingClinicUser != null) {
            errorMessages.put("emailError", En.DUPLICATE_EMAIL_MESSAGE);
        }
        return errorMessages;
    }

    public Map<String, String> validateDuplicateFullName(String fullName) {
        Map<String, String> errorMessages = new HashMap<>();
        ClinicUser existingClinicUser = clinicUserFacade.findByFullNameAndStatus(fullName.trim().toLowerCase(),
                ClinicUserStatus.APPROVED);
        if (existingClinicUser != null) {
            errorMessages.put("fullNameError", En.DUPLICATE_FULL_NAME_MESSAGE);
        }
        return errorMessages;
    }

}
