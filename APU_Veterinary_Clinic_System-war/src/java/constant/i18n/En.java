package constant.i18n;

import static constant.GlobalConstant.DMY_SLASH_DATE_FORMAT;

public class En {

    public static final String EMPTY_PHONE_NUMBER_MESSAGE = "Phone Number is required.";
    public static final String INVALID_PHONE_NUMBER_MESSAGE = "Please enter a valid Malaysia phone number.";
    public static final String DUPLICATE_PHONE_NUMBER_MESSAGE = "Phone Number already exists.";

    public static final String EMPTY_EMAIL_MESSAGE = "Email is required.";
    public static final String INVALID_EMAIL_MESSAGE = "Email is invalid.";
    public static final String DUPLICATE_EMAIL_MESSAGE = "Email already exists.";

    public static final String EMPTY_PASSWORD_MESSAGE = "Password is required.";
    public static final String INVALID_PASSWORD_MESSAGE = "Password must be at least 8 characters long and contain at least one digit, one lowercase letter, one uppercase letter, and one special character.";
    public static final String INVALID_CREDENTIAL_MESSAGE = "Invalid credentials. Please try again.";
    public static final String UNMATCHED_NEW_PASSWORD_MESSAGE = "New password do not match";
    public static final String EMPTY_CONFIRM_PASSWORD_MESSAGE = "Confirm password is required";
    public static final String NEW_PASSWORD_SAME_AS_CURRENT_MESSAGE = "New password cannot be the same as the current password.";

    public static final String EMPTY_FULL_NAME_MESSAGE = "Full Name is required.";
    public static final String DUPLICATE_FULL_NAME_MESSAGE = "Full Name already exists.";
    public static final String INVALID_FULL_NAME_CHARACTER_MESSAGE = "Full Name must be only having alphabet letters.";
    public static final String INVALID_FULL_NAME_LENGTH_MESSAGE = "Full Name must be between 8 to 50 characters long.";
    public static final String EXCESSIVE_WHITESPACE_FULL_NAME_MESSAGE = "Full Name cannot contain excessive white spaces.";

    public static final String EMPTY_ADDRESS_MESSAGE = "Address is required.";
    public static final String INVALID_ADDRESS_LENGTH_MESSAGE = "Address must be between 10 to 100 characters long.";

    public static final String EMPTY_DATE_OF_BIRTH_MESSAGE = "Date of Birth is required.";
    public static final String INVALID_DATE_OF_BIRTH_FORMAT_MESSAGE = "Invalid date of birth format. Please use the format " + DMY_SLASH_DATE_FORMAT + ".";
    public static final String FUTURE_DATE_OF_BIRTH_MESSAGE = "Date of Birth cannot be in the future.";

    public static final String EMPTY_GENDER_MESSAGE = "Gender is required.";
    public static final String INVALID_GENDER_MESSAGE = "Gender is invalid.";

    public static final String EMPTY_SPECIES_MESSAGE = "Species cannot be empty.";
    public static final String EMPTY_BREED_MESSAGE = "Breed cannot be empty.";
    public static final String EMPTY_NAME_MESSAGE = "Name cannot be empty.";
    public static final String EMPTY_HEALTH_STATUS_MESSAGE = "Health status cannot be empty.";
    public static final String EMPTY_CUSTOMER_DETAILS_MESSAGE = "Customer details is required.";
    public static final String EMPTY_SEARCH_INPUT_MESSAGE = "Search input is required.";
    public static final String INVALID_HEALTH_STATUS_MESSAGE = "Invalid health status.";

    public static final String RECORD_NOT_FOUND_MESSAGE = "Record not found";
    public static final String STAFF_NOT_FOUND_MESSAGE = "Staff not found";
    public static final String PET_NOT_FOUND_MESSAGE = "Pet not found";
    public static final String CUSTOMER_NOT_FOUND_MESSAGE = "Customer not found";

    public static final String DUPLICATE_EXPERTISE_NAME_MESSAGE = "This expertise already exists.";
    public static final String ERROR_UPDATING_PROFILE = "Error occurred while trying to update profile";
    public static final String ERROR_DELETING_EXPERTISE = "Error occurred while trying to delete the expertise";
    public static final String ERROR_UPDATING_APPROVAL = "Error occurred while trying to updating the approval";
    public static final String NO_CHANGES_MESSAGE = "No changes are made.";

}
