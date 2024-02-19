package validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotEmpty;

@NotEmpty(message = "Full Name is required.")
public class RequiredFullNameConstraint implements ConstraintValidator<NotEmpty, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {

            return false;
        }
        return true;
    }
}