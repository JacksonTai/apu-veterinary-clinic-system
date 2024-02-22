<%-- 
    Document   : login
    Created on : Feb 6, 2024, 9:28:44 AM
    Author     : Jackson Tai
--%>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="/shared/component/head_source.jsp" %>
    <title>Add Customer</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<h1>Add Customer</h1>
<form action="<c:url value='<%= EndpointConstant.CREATE_CUSTOMER %>'/>" method="POST">
    <table>
        <tr>
            <td>Full Name</td>
            <td>
                <input type="text" name="fullName" value="${not empty param.fullName ? param.fullName : ''}">
                <c:if test="${not empty fullNameError}">
                    <span style="color: red;">${fullNameError}</span>
                </c:if>
            </td>
        </tr>
        <tr>
            <td>Phone Number (+60)</td>
            <td>
                <input type="text" name="phoneNumber" value="${not empty param.phoneNumber ? param.phoneNumber : ''}">
                <c:if test="${not empty phoneNumberError}">
                    <span style="color: red;">${phoneNumberError}</span>
                </c:if>
            </td>
        </tr>
        <tr>
            <td>Email</td>
            <td>
                <input type="text" name="email" value="${not empty param.email ? param.email : ''}">
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
                <input type="date" name="dateOfBirth" value="${not empty param.dateOfBirth ? param.dateOfBirth : ''}">
                <c:if test="${not empty dateOfBirthError}">
                    <span style="color: red;">${dateOfBirthError}</span>
                </c:if>
            </td>
        </tr>
        <tr>
            <td>Address</td>
            <td>
                <input type="text" name="address" value="${not empty param.address ? param.address : ''}">
                <c:if test="${not empty addressError}">
                    <span style="color: red;">${addressError}</span>
                </c:if>
            </td>
        </tr>
    </table>
    <p><input type="submit" value="Create"></p>
</form>
<a href="<c:url value='<%= EndpointConstant.VIEW_CUSTOMER %>'/>">Back to Customer Records</a>
</body>
</html>
