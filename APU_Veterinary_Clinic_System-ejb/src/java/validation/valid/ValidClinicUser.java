package validation.valid;

import validation.validator.ClinicUserValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ClinicUserValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ValidClinicUser {

    String message() default "Invalid clinic user";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
