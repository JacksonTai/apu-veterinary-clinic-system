const PET = "/pet";
const STAFF = "/staff";
const CUSTOMER = "/customer";
const EXPERTISE = "/expertise";
const APPROVAL = "/approval";
const STAFF_APPROVAL = STAFF + APPROVAL;

const endpoints = {
  "VIEW_CUSTOMER": CUSTOMER + "/viewCustomer",
  "VIEW_CUSTOMERS": CUSTOMER + "/viewCustomers",
  "DELETE_CUSTOMER": CUSTOMER + "/deleteCustomer",
  "SEARCH_CUSTOMER": CUSTOMER + "/searchCustomer",
  "VIEW_PET": PET + "/viewPet",
  "DELETE_PET": PET + "/deletePet",
  "VIEW_EXPERTISE": EXPERTISE + "/viewExpertise",
  "DELETE_EXPERTISE": EXPERTISE + "/deleteExpertise",
  "DELETE_STAFF": STAFF + "/deleteStaff",
  "VIEW_STAFF": STAFF + "/viewStaff",
  "SEARCH_STAFF": STAFF + "/searchStaff",
  "VIEW_STAFF_APPROVAL": STAFF_APPROVAL + "/viewStaffApproval",
  "UPDATE_STAFF_APPROVAL": STAFF_APPROVAL + "/updateStaffApproval",
};
