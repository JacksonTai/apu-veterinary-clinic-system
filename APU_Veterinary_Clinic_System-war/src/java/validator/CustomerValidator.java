package validator;

import entity.Customer;
import repository.CustomerFacade;

import javax.ejb.EJB;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static constant.GlobalConstant.*;
import static constant.i18n.En.*;
import static util.StringUtil.*;

public class CustomerValidator implements Validator<Customer> {

    @EJB
    private CustomerFacade customerFacade;

    public CustomerValidator(CustomerFacade customerFacade) {
        this.customerFacade = customerFacade;
    }

    @Override
    public Map<String, String> validate(Customer customer) {
        Map<String, String> errorMessages = new HashMap<>();
        errorMessages.putAll(validateFullName(customer.getFullName()));
        errorMessages.putAll(validatePhoneNumber(customer.getPhoneNumber()));
        errorMessages.putAll(validateEmail(customer.getEmail()));
        errorMessages.putAll(validateGender(customer.getGender()));
        errorMessages.putAll(validateDateOfBirth(customer.getDateOfBirth()));
        errorMessages.putAll(validateAddress(customer.getAddress()));
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
        } else if (!containsAlphabetic(fullName)){
            errorMessages.put("fullNameError", INVALID_FULL_NAME_CHARACTER_MESSAGE);
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
        } else if (!Pattern.matches(CUSTOMER_EMAIL_REGEX, email) || Pattern.matches(STAFF_EMAIL_REGEX, email)) {
            errorMessages.put("emailError", INVALID_EMAIL_MESSAGE);
        }
        return errorMessages;
    }

    public static Map<String, String> validateGender(String gender) {
        Map<String, String> errorMessages = new HashMap<>();
        gender = gender.trim();
        if (gender.isEmpty()) {
            errorMessages.put("genderError", EMPTY_GENDER_MESSAGE);
        } else if (!gender.equalsIgnoreCase("Male") && !gender.equalsIgnoreCase("Female") &&
                !gender.equalsIgnoreCase("Other")) {
            errorMessages.put("genderError", INVALID_GENDER_MESSAGE);
        }
        return errorMessages;
    }

    public static Map<String, String> validateDateOfBirth(LocalDate dateOfBirth) {
        Map<String, String> errorMessages = new HashMap<>();
        if (dateOfBirth == null) {
            errorMessages.put("dateOfBirthError", EMPTY_DATE_OF_BIRTH_MESSAGE);
        } else {
            LocalDate today = LocalDate.now();
            if (dateOfBirth.isAfter(today)) {
                errorMessages.put("dateOfBirthError", FUTURE_DATE_OF_BIRTH_MESSAGE);
            }
        }
        return errorMessages;
    }

    public static Map<String, String> validateAddress(String address) {
        Map<String, String> errorMessages = new HashMap<>();
        address = address.trim();
        if (address.isEmpty()) {
            errorMessages.put("addressError", EMPTY_ADDRESS_MESSAGE);
        } else if (address.length() < 10 || address.length() > 100) {
            errorMessages.put("addressError", INVALID_ADDRESS_LENGTH_MESSAGE);
        }
        return errorMessages;
    }

    public Map<String, String> validateCustomerDetails(Customer customer) {
        Map<String, String> errorMessages = validate(customer);
        errorMessages.putAll(validateDuplicateEmail(customer.getEmail()));
        errorMessages.putAll(validateDuplicatePhoneNumber(customer.getPhoneNumber()));
        errorMessages.putAll(validateDuplicateFullName(customer.getFullName()));
        return errorMessages;
    }

    public Map<String, String> validateDuplicateFullName(String fullName) {
        Map<String, String> errorMessages = new HashMap<>();
        Customer existingCustomer = customerFacade.findByFullName(fullName.trim());
        if (existingCustomer != null) {
            errorMessages.put("fullNameError", DUPLICATE_FULL_NAME_MESSAGE);
        }
        return errorMessages;
    }

    public Map<String, String> validateDuplicatePhoneNumber(String phoneNumber) {
        Map<String, String> errorMessages = new HashMap<>();
        Customer existingCustomer = customerFacade.findByPhoneNumber(phoneNumber.trim());
        if (existingCustomer != null) {
            errorMessages.put("phoneNumberError", DUPLICATE_PHONE_NUMBER_MESSAGE);
        }
        return errorMessages;
    }

    public Map<String, String> validateDuplicateEmail(String email) {
        Map<String, String> errorMessages = new HashMap<>();
        Customer existingCustomer = customerFacade.findByEmail(email.trim());
        if (existingCustomer != null) {
            errorMessages.put("emailError", DUPLICATE_EMAIL_MESSAGE);
        }
        return errorMessages;
    }

}
