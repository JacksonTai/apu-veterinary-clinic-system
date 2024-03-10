package service.staff;

import model.staff.CreateStaffResponseModel;
import model.staff.SearchStaffResponseModel;

public interface StaffService {
    CreateStaffResponseModel createStaff(String fullName, String email, String password, String userRole);

    CreateStaffResponseModel createStaff(String fullName, String email, String password, String userRole,
                                         boolean isMakerCheckerEnabled);

}
