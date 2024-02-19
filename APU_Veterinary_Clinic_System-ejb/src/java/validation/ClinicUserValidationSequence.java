package validation;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class, EmptyFields.class, InvalidFields.class})
public interface ClinicUserValidationSequence {
}
