package validator;

import constant.i18n.En;
import entity.MedicalRecord;

import java.util.HashMap;
import java.util.Map;

import static constant.i18n.En.EMPTY_DIAGNOSIS_MESSAGE;

public class MedicalRecordValidator implements Validator<MedicalRecord> {

    @Override
    public Map<String, String> validate(MedicalRecord medicalRecord) {
        Map<String, String> errorMessages = new HashMap<>();
        errorMessages.putAll(validateDiagnosis(medicalRecord.getDiagnosis()));
        errorMessages.putAll(validatePrognosis(medicalRecord.getPrognosis()));
        return errorMessages;
    }

    public static Map<String, String> validateDiagnosis(String diagnosis) {
        Map<String, String> errorMessages = new HashMap<>();
        diagnosis = diagnosis.trim();
        if (diagnosis.isEmpty()) {
            errorMessages.put("diagnosisError", EMPTY_DIAGNOSIS_MESSAGE);
        }
        return errorMessages;
    }

    public static Map<String, String> validatePrognosis(String prognosis) {
        Map<String, String> errorMessages = new HashMap<>();
        prognosis = prognosis.trim();
        if (prognosis.isEmpty()) {
            errorMessages.put("prognosisError", En.EMPTY_PROGNOSIS_MESSAGE);
        }
        return errorMessages;
    }
}
