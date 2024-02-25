package validator;

import java.util.Map;
import java.util.Optional;

public class ValidationResponse<T> {

    private final Optional<T> entity;
    private final Map<String, String> errorMessages;

    public ValidationResponse(Optional<T> entity, Map<String, String> errorMessages) {
        this.entity = entity;
        this.errorMessages = errorMessages;
    }

    public Optional<T> getEntity() {
        return entity;
    }

    public Map<String, String> getErrorMessages() {
        return errorMessages;
    }
}
