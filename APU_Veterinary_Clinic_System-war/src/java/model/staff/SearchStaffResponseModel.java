package model.staff;

import entity.ClinicUser;
import lombok.Data;

import java.util.Map;

@Data
public class SearchStaffResponseModel {

    private int statusCode;
    private ClinicUser clinicUser;
    private String message;

}
