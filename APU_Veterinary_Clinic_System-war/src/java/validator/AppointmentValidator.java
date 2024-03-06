package validator;

import constant.AppointmentStatus;
import entity.Appointment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static constant.i18n.En.EMPTY_APPOINTMENT_STATUS_MESSAGE;
import static constant.i18n.En.INVALID_APPOINTMENT_STATUS_MESSAGE;

public class AppointmentValidator implements Validator<Appointment> {

    private static final List<String> appointmentStatusList = Arrays.asList(AppointmentStatus.SCHEDULED,
            AppointmentStatus.ONGOING, AppointmentStatus.CANCELLED, AppointmentStatus.COMPLETED);

    @Override
    public Map<String, String> validate(Appointment appointment) {
        Map<String, String> errorMessages = new HashMap<>();
        errorMessages.putAll(validateAppointmentStatus(appointment.getAppointmentStatus()));
        return errorMessages;
    }

    public static Map<String, String> validateAppointmentStatus(String appointmentStatus) {
        appointmentStatus = appointmentStatus.trim();
        Map<String, String> errorMessages = new HashMap<>();
        if (appointmentStatus.isEmpty()) {
            errorMessages.put("appointmentStatusError", EMPTY_APPOINTMENT_STATUS_MESSAGE);
        }
        if (!appointmentStatusList.contains(appointmentStatus)) {
            errorMessages.put("appointmentStatusError", INVALID_APPOINTMENT_STATUS_MESSAGE);
        }
        return errorMessages;
    }

}
