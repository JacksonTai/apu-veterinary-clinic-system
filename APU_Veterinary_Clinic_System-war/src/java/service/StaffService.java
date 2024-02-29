package service;

import model.CreateStaffResponseModel;

public interface StaffService {
    CreateStaffResponseModel createStaff(String fullName, String email, String password, String userRole);
}
