package validator;

import entity.Customer;
import repository.CustomerFacade;

import javax.ejb.EJB;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static constant.GlobalConstant.MY_PHONE_REGEX;
import static constant.i18n.En.*;
import static util.StringUtil.containsExcessiveWhitespace;
import static util.StringUtil.containsNumeric;

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
            errorMessages.put("phoneNumberError", DUPLICATE_EMAIL_MESSAGE);
        }
        return errorMessages;
    }

}
