package validation.validator;

import entity.ClinicUser;
import validation.valid.ValidClinicUser;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static constant.RegexConstant.*;

public class ClinicUserValidator implements ConstraintValidator<ValidClinicUser, ClinicUser> {
    @Override
    public void initialize(ValidClinicUser constraintAnnotation) {
    }

    @Override
    public boolean isValid(ClinicUser clinicUser, ConstraintValidatorContext constraintValidatorContext) {
        if (clinicUser == null) {
            return false;
        }

        if (clinicUser.getFullName() == null || clinicUser.getFullName().isEmpty()) {
            return false;
        }

        if (clinicUser.getEmail() == null || clinicUser.getEmail().isEmpty()) {
            return false;
        } else if (!clinicUser.getEmail().matches(EMAIL_REGEX)) {
            return false;
        }

        if (clinicUser.getPassword() == null || clinicUser.getPassword().isEmpty()) {
            return false;
        } else if (!clinicUser.getPassword().matches(PASSWORD_REGEX)) {
            return false;
        }

        if (clinicUser.getPhoneNumber() == null || clinicUser.getPhoneNumber().isEmpty()) {
            return false;
        } else if (!clinicUser.getPhoneNumber().matches(MY_PHONE_REGEX)) {
            return false;
        }

        return true;
    }

}
