<%-- 
    Document   : login
    Created on : Feb 6, 2024, 9:28:44 AM
    Author     : Jackson Tai
--%>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="/shared/component/head_source.jsp" %>
    <title>Update Customer</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<main class="w-75 my-2 mx-auto overflow-x-auto">
    <h1 class="text-center">Update Customer</h1>
    <form action="<c:url value='<%= EndpointConstant.UPDATE_CUSTOMER %>'/>" method="POST" class="my-2 mx-auto"
          style="max-width: 30rem;">
        <input type="hidden" name="formSubmitted" value="true">
        <input type="hidden" name="id" value="${customer.customerId ? customer.customerId : param.id}">
        <div class="mb-3">
            <label for="fullName" class="form-label">Full Name:</label>
            <input type="text" class="form-control" id="fullName" name="fullName"
                   value="${param.formSubmitted != null ? param.fullName : customer.fullName}">
            <c:if test="${not empty fullNameError}">
                <span style="color: red;">${fullNameError}</span>
            </c:if>
        </div>
        <div class="mb-3">
            <label for="phoneNumber" class="form-label">Phone Number:</label>
            <input type="text" class="form-control" id="phoneNumber" name="phoneNumber"
                   value="${param.formSubmitted != null ? param.phoneNumber : customer.phoneNumber}">
            <c:if test="${not empty phoneNumberError}">
                <span style="color: red;">${phoneNumberError}</span>
            </c:if>
        </div>
        <div class="mb-3">
            <label for="email" class="form-label">Email:</label>
            <input type="text" class="form-control" id="email" name="email"
                   value="${param.formSubmitted != null ? param.email : customer.email}">
            <c:if test="${not empty emailError}">
                <span style="color: red;">${emailError}</span>
            </c:if>
        </div>
        <div class="mb-3">
            <label for="gender" class="form-label">Gender:</label>
            <select id="gender" name="gender" class="form-select">
                <option value="Male"
                ${param.formSubmitted != null ? (param.gender == 'Male' ? 'selected' : '') :
                        (customer.gender == 'Male' ? 'selected' : '')}>
                    Male
                </option>
                <option value="Female"
                ${param.formSubmitted != null ? (param.gender == 'Female' ? 'selected' : '') :
                        (customer.gender == 'Female' ? 'selected' : '')}>
                    Female
                </option>
                <option value="Other"
                ${param.formSubmitted != null ? (param.gender == 'Other' ? 'selected' : '') :
                        (customer.gender == 'Other' ? 'selected' : '')}>
                    Other
                </option>
            </select>
            <c:if test="${not empty genderError}">
                <span style="color: red;">${genderError}</span>
            </c:if>
        </div>
        <div class="mb-3">
            <label for="dateOfBirth" class="form-label">Date of Birth:</label>
            <input type="date" class="form-control" id="dateOfBirth" name="dateOfBirth"
                   value="${param.formSubmitted != null ? param.dateOfBirth : customer.dateOfBirth}">
            <c:if test="${not empty dateOfBirthError}">
                <span style="color: red;">${dateOfBirthError}</span>
            </c:if>
        </div>
        <div class="mb-3">
            <label for="address" class="form-label">Address:</label>
            <input type="text" class="form-control" id="address" name="address"
                   value="${param.formSubmitted != null ? param.address : customer.address}">
            <c:if test="${not empty addressError}">
                <span style="color: red;">${addressError}</span>
            </c:if>
        </div>
        <div class="mx-auto d-flex justify-content-center">
            <a class="btn btn-light d-block me-2"
               href="<c:url value='<%= EndpointConstant.VIEW_CUSTOMER %>'/>?id=${customer.customerId ?
                customer.customerId : param.id}" role="button">Cancel</a>
            <button type="submit" class="btn btn-primary">Confirm</button>
        </div>
    </form>
</main>
</body>
</html>
