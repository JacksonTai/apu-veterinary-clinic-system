<%-- 
    Document   : login
    Created on : Feb 6, 2024, 9:28:44 AM
    Author     : Jackson Tai
--%>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="/shared/component/head_source.jsp" %>
    <title>Create Customer Profile</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<main class="w-75 my-2 mx-auto overflow-x-auto">
    <h1 class="text-center">Create Customer Profile</h1>
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="<c:url value='<%= EndpointConstant.VIEW_CUSTOMER %>'/>">Customers</a>
            </li>
            <li class="breadcrumb-item active" aria-current="page">Create Customer Profile</li>
        </ol>
    </nav>
    <form action="<c:url value='<%= EndpointConstant.CREATE_CUSTOMER %>'/>" method="POST" class="my-2 mx-auto"
          style="max-width: 30rem;">
        <div class="mb-3">
            <label for="fullName" class="form-label">Full Name:</label>
            <input type="text" class="form-control" id="fullName" name="fullName"
                   value="${not empty param.fullName ? param.fullName : ''}">
            <c:if test="${not empty fullNameError}">
                <span style="color: red;">${fullNameError}</span>
            </c:if>
        </div>
        <div class="mb-3">
            <label for="phoneNumber" class="form-label">Phone Number:</label>
            <input type="text" class="form-control" id="phoneNumber" name="phoneNumber"
                   placeholder="01xxxxxxxxx (Malaysian number)"
                   value="${not empty param.phoneNumber ? param.phoneNumber : ''}">
            <c:if test="${not empty phoneNumberError}">
                <span style="color: red;">${phoneNumberError}</span>
            </c:if>
        </div>
        <div class="mb-3">
            <label for="email" class="form-label">Email:</label>
            <input type="text" class="form-control" id="email" name="email"
                   value="${not empty param.email ? param.email : ''}">
            <c:if test="${not empty emailError}">
                <span style="color: red;">${emailError}</span>
            </c:if>
        </div>
        <div class="mb-3">
            <label for="gender" class="form-label">Gender:</label>
            <select id="gender" name="gender" class="form-select">
                <option value="Male" ${param.gender == 'Male' ? 'selected' : ''}>Male</option>
                <option value="Female" ${param.gender == 'Female' ? 'selected' : ''}>Female</option>
                <option value="Other" ${param.gender == 'Other' ? 'selected' : ''}>Other</option>
            </select>
            <c:if test="${not empty genderError}">
                <span style="color: red;">${genderError}</span>
            </c:if>
        </div>
        <div class="mb-3">
            <label for="dateOfBirth" class="form-label">Date of Birth:</label>
            <input type="date" class="form-control" id="dateOfBirth" name="dateOfBirth"
                   value="${not empty param.dateOfBirth ? param.dateOfBirth : ''}">
            <c:if test="${not empty dateOfBirthError}">
                <span style="color: red;">${dateOfBirthError}</span>
            </c:if>
        </div>
        <div class="mb-3">
            <label for="address" class="form-label">Address:</label>
            <input type="text" class="form-control" id="address" name="address"
                   value="${not empty param.address ? param.address : ''}">
            <c:if test="${not empty addressError}">
                <span style="color: red;">${addressError}</span>
            </c:if>
        </div>
        <div class="mx-auto d-flex justify-content-center">
            <a class="btn btn-light d-block me-2"
               href="<c:url value='<%= EndpointConstant.VIEW_CUSTOMER %>'/>" role="button">Cancel</a>
            <button type="submit" class="btn btn-primary">Confirm</button>
        </div>
    </form>
</main>
<%@ include file="/shared/component/footer.jsp" %>
</body>
</html>
