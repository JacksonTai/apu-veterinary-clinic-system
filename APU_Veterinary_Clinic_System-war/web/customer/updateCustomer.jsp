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
<h1>Update Customer</h1>
<form action="<c:url value='<%= EndpointConstant.UPDATE_CUSTOMER %>'/>" method="POST">
    <input type="hidden" name="formSubmitted" value="true">
    <table>
        <tr>
            <td>Full Name</td>
            <td>
                <input type="text" name="fullName"
                       value="${param.formSubmitted != null ? param.fullName : customer.fullName}">
                <c:if test="${not empty fullNameError}">
                    <span style="color: red;">${fullNameError}</span>
                </c:if>
            </td>
        </tr>
        <tr>
            <td>Phone Number (+60)</td>
            <td>
                <input type="text" name="phoneNumber"
                       value="${param.formSubmitted != null ? param.phoneNumber : customer.phoneNumber}">
                <c:if test="${not empty phoneNumberError}">
                    <span style="color: red;">${phoneNumberError}</span>
                </c:if>
            </td>
        </tr>
        <tr>
            <td>Email</td>
            <td>
                <input type="text" name="email" value="${param.formSubmitted != null ? param.email : customer.email}">
                <c:if test="${not empty emailError}">
                    <span style="color: red;">${emailError}</span>
                </c:if>
            </td>
        </tr>
        <tr>
            <td>Gender</td>
            <td>
                <select name="gender">
                    <option value="">Select Gender</option>
                    <option value="Male" ${param.gender == 'Male' ? 'selected' : ''}>Male</option>
                    <option value="Female" ${param.gender == 'Female' ? 'selected' : ''}>Female</option>
                    <option value="Other" ${param.gender == 'Other' ? 'selected' : ''}>Other</option>
                </select>
                <c:if test="${not empty genderError}">
                    <span style="color: red;">${genderError}</span>
                </c:if>
            </td>
        </tr>
        <tr>
            <td>Date of Birth</td>
            <td>
                <input type="date" name="dateOfBirth"
                       value="${param.formSubmitted != null ? param.dateOfBirth : customer.dateOfBirth}">
                <c:if test="${not empty dateOfBirthError}">
                    <span style="color: red;">${dateOfBirthError}</span>
                </c:if>
            </td>
        </tr>
        <tr>
            <td>Address</td>
            <td>
                <input type="text" name="address"
                       value="${param.formSubmitted != null ? param.address : customer.address}">
                <c:if test="${not empty addressError}">
                    <span style="color: red;">${addressError}</span>
                </c:if>
            </td>
        </tr>
    </table>
    <p><input type="submit" value="Update"></p>
</form>
<a href="<c:url value='<%= EndpointConstant.VIEW_CUSTOMER %>'/>">Back to Customer Records</a>
</body>
</html>
