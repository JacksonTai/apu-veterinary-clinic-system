package validator;

import entity.Customer;
import entity.Expertise;
import repository.CustomerFacade;
import repository.ExpertiseFacade;

import javax.ejb.EJB;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static constant.i18n.En.*;

public class ExpertiseValidator implements Validator<Expertise> {

    @EJB
    private ExpertiseFacade expertiseFacade;

    public ExpertiseValidator(ExpertiseFacade expertiseFacade) {
        this.expertiseFacade = expertiseFacade;
    }

    @Override
    public Map<String, String> validate(Expertise expertise) {
        return new HashMap<>(validateName(expertise.getName()));
    }

    public static Map<String, String> validateName(String name) {
        Map<String, String> errorMessages = new HashMap<>();
        name = name.trim();
        if (name.isEmpty()) {
            errorMessages.put("nameError", EMPTY_NAME_MESSAGE);
        }
        return errorMessages;
    }

    public Map<String, String> validateDuplicateName(String name) {
        Map<String, String> errorMessages = new HashMap<>();
        Optional<Expertise> existingExpertise = expertiseFacade.findByName(name.trim().toLowerCase());
        if (existingExpertise.isPresent()) {
            errorMessages.put("nameError", DUPLICATE_EXPERTISE_NAME_MESSAGE);
        }
        return errorMessages;
    }

}
