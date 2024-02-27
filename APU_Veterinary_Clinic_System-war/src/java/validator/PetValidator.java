package validator;

import constant.PetHealthStatus;
import constant.i18n.En;
import entity.Pet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PetValidator implements Validator<Pet> {

    @Override
    public Map<String, String> validate(Pet pet) {
        Map<String, String> errorMessages = new HashMap<>();
        errorMessages.putAll(validateSpecies(pet.getSpecies()));
        errorMessages.putAll(validateBreed(pet.getBreed()));
        errorMessages.putAll(validateName(pet.getName()));
        errorMessages.putAll(validateHealthStatus(pet.getHealthStatus()));
        return errorMessages;
    }

    public static Map<String, String> validateSpecies(String species) {
        Map<String, String> errorMessages = new HashMap<>();
        species = species.trim();
        if (species.isEmpty()) {
            errorMessages.put("speciesError", En.EMPTY_SPECIES_MESSAGE);
        }
        return errorMessages;
    }

    public static Map<String, String> validateBreed(String breed) {
        Map<String, String> errorMessages = new HashMap<>();
        breed = breed.trim();
        if (breed.isEmpty()) {
            errorMessages.put("breedError", En.EMPTY_BREED_MESSAGE);
        }
        return errorMessages;
    }

    public static Map<String, String> validateName(String name) {
        Map<String, String> errorMessages = new HashMap<>();
        name = name.trim();
        if (name.isEmpty()) {
            errorMessages.put("nameError", En.EMPTY_NAME_MESSAGE);
        }
        return errorMessages;
    }

    public static Map<String, String> validateHealthStatus(String healthStatus) {
        Map<String, String> errorMessages = new HashMap<>();
        healthStatus = healthStatus.trim();
        if (healthStatus.isEmpty()) {
            errorMessages.put("healthStatusError", En.EMPTY_HEALTH_STATUS_MESSAGE);
        } else if (!Arrays.asList(PetHealthStatus.HEALTHY, PetHealthStatus.SICK, PetHealthStatus.DEAD,
                PetHealthStatus.RECOVERING, PetHealthStatus.UNDER_OBSERVATION,
                PetHealthStatus.CRITICAL_CONDITION).contains(healthStatus)) {
            errorMessages.put("healthStatusError", En.INVALID_HEALTH_STATUS_MESSAGE);
        }
        return errorMessages;
    }

}
