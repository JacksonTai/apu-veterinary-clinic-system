package validator;

import constant.GlobalConstant;
import constant.i18n.En;
import entity.Customer;
import repository.CustomerFacade;

import javax.ejb.EJB;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

;

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
        errorMessages.putAll(validateFullName(customer.getFullName()));
        errorMessages.putAll(validateEmail(customer.getEmail()));
        errorMessages.putAll(validateGender(customer.getGender()));
        errorMessages.putAll(validateDateOfBirth(customer.getDateOfBirth()));
        errorMessages.putAll(validateAddress(customer.getAddress()));
        return errorMessages;
    }

    public static Map<String, String> validateFullName(String fullName) {
        return ClinicUserValidator.validateFullName(fullName);
    }

    public static Map<String, String> validatePhoneNumber(String phoneNumber) {
        Map<String, String> errorMessages = new HashMap<>();
        phoneNumber = phoneNumber.trim();
        if (phoneNumber.isEmpty()) {
            errorMessages.put("phoneNumberError", En.EMPTY_PHONE_NUMBER_MESSAGE);
        } else if (!Pattern.matches(GlobalConstant.MY_PHONE_REGEX, phoneNumber)) {
            errorMessages.put("phoneNumberError", En.INVALID_PHONE_NUMBER_MESSAGE);
        }
        return errorMessages;
    }

    public static Map<String, String> validateEmail(String email) {
        Map<String, String> errorMessages = new HashMap<>();
        email = email.trim();
        if (email.isEmpty()) {
            errorMessages.put("emailError", En.EMPTY_EMAIL_MESSAGE);
        } else if (!Pattern.matches(GlobalConstant.CUSTOMER_EMAIL_REGEX, email) || Pattern.matches(GlobalConstant.STAFF_EMAIL_REGEX, email)) {
            errorMessages.put("emailError", En.INVALID_EMAIL_MESSAGE);
        }
        return errorMessages;
    }

    public static Map<String, String> validateGender(String gender) {
        Map<String, String> errorMessages = new HashMap<>();
        gender = gender.trim();
        if (gender.isEmpty()) {
            errorMessages.put("genderError", En.EMPTY_GENDER_MESSAGE);
        } else if (!gender.equalsIgnoreCase("Male") && !gender.equalsIgnoreCase("Female") &&
                !gender.equalsIgnoreCase("Other")) {
            errorMessages.put("genderError", En.INVALID_GENDER_MESSAGE);
        }
        return errorMessages;
    }

    public static Map<String, String> validateDateOfBirth(String dateOfBirth) {
        Map<String, String> errorMessages = new HashMap<>();
        dateOfBirth = dateOfBirth.trim();
        if (dateOfBirth.isEmpty()) {
            errorMessages.put("dateOfBirthError", En.EMPTY_DATE_OF_BIRTH_MESSAGE);
        } else {
            LocalDate dateOfBirthLocalDate;
            try {
                dateOfBirthLocalDate = LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern(GlobalConstant.ISO_DATE_FORMAT));
                LocalDate today = LocalDate.now();
                if (dateOfBirthLocalDate.isAfter(today)) {
                    errorMessages.put("dateOfBirthError", En.FUTURE_DATE_OF_BIRTH_MESSAGE);
                }
            } catch (DateTimeParseException e) {
                errorMessages.put("dateOfBirthError", En.INVALID_DATE_OF_BIRTH_FORMAT_MESSAGE);
            }
        }
        return errorMessages;
    }

    public static Map<String, String> validateAddress(String address) {
        Map<String, String> errorMessages = new HashMap<>();
        address = address.trim();
        if (address.isEmpty()) {
            errorMessages.put("addressError", En.EMPTY_ADDRESS_MESSAGE);
        } else if (address.length() < 10 || address.length() > 100) {
            errorMessages.put("addressError", En.INVALID_ADDRESS_LENGTH_MESSAGE);
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
        Optional<Customer> existingCustomer = customerFacade.findByFullName(fullName.trim().toLowerCase());
        if (existingCustomer.isPresent()) {
            errorMessages.put("fullNameError", En.DUPLICATE_FULL_NAME_MESSAGE);
        }
        return errorMessages;
    }

    public Map<String, String> validateDuplicatePhoneNumber(String phoneNumber) {
        Map<String, String> errorMessages = new HashMap<>();
        Optional<Customer> existingCustomer = customerFacade.findByPhoneNumber(phoneNumber.trim());
        if (existingCustomer.isPresent()) {
            errorMessages.put("phoneNumberError", En.DUPLICATE_PHONE_NUMBER_MESSAGE);
        }
        return errorMessages;
    }

    public Map<String, String> validateDuplicateEmail(String email) {
        Map<String, String> errorMessages = new HashMap<>();
        Optional<Customer> existingCustomer = customerFacade.findByEmail(email.trim().toLowerCase());
        if (existingCustomer.isPresent()) {
            errorMessages.put("emailError", En.DUPLICATE_EMAIL_MESSAGE);
        }
        return errorMessages;
    }

    public ValidationResponse<Customer> validateCustomerDetails(String customerDetails) {
        Map<String, String> errorMessages = new HashMap<>();
        Optional<Customer> customerOptional = Optional.empty();
        if (customerDetails.isEmpty()) {
            errorMessages.put("customerDetailsError", En.EMPTY_CUSTOMER_DETAILS_MESSAGE);
        } else {
            customerOptional = customerFacade.findByIdOrFullNameOrEmailOrPhoneNumber(customerDetails);
            if (!customerOptional.isPresent()) {
                errorMessages.put("customerDetailsError", En.CUSTOMER_NOT_FOUND_MESSAGE);
            }
        }
        return new ValidationResponse<>(customerOptional, errorMessages);
    }

}
