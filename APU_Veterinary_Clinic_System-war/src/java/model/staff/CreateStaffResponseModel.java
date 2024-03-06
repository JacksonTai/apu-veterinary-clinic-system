package model.staff;

import entity.ClinicUser;
import lombok.Data;

import java.util.Map;

@Data
public class CreateStaffResponseModel {

    private int statusCode;
    private ClinicUser clinicUser;
    private Map<String, String> errorMessages;

}
