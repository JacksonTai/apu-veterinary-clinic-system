<%-- 
    Document   : profile
    Created on : Feb 18, 2024, 10:41:18 AM
    Author     : Jackson Tai
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/shared/component/head_source.jsp" %>
    <title>Update Profile</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<h1>Update Profile</h1>
<form action="<c:url value='<%= EndpointConstant.UPDATE_PROFILE %>'/>" method="POST">
    <input type="hidden" name="formSubmitted" value="true">
    <table>
        <tr>
            <td>Full Name</td>
            <td>
                <input type="text" name="fullName"
                       value="${param.formSubmitted != null ? param.fullName : clinicUser.fullName}">
                <c:if test="${not empty fullNameError}">
                    <span style="color: red;">${fullNameError}</span>
                </c:if>
            </td>
        </tr>
        <tr>
            <td>Phone Number (+60)</td>
            <td>
                <input type="text" name="phoneNumber"
                       value="${param.formSubmitted != null ? param.phoneNumber : clinicUser.phoneNumber}">
                <c:if test="${not empty phoneNumberError}">
                    <span style="color: red;">${phoneNumberError}</span>
                </c:if>
            </td>
        </tr>
        <tr>
            <td>Email</td>
            <td>
                <input type="text" name="email" value="${param.formSubmitted != null ? param.email : clinicUser.email}">
                <c:if test="${not empty emailError}">
                    <span style="color: red;">${emailError}</span>
                </c:if>
            </td>
        </tr>
        <tr>
            <td>Password:</td>
            <td>
                <input type="password" name="password" value="${not empty param.password ? param.password : ''}">
                <c:if test="${not empty invalidCredentialError}">
                    <span style="color: red;">${invalidCredentialError}</span>
                </c:if>
            </td>
        </tr>
    </table>
    <a href="<c:url value='<%= EndpointConstant.VIEW_PROFILE %>'/>">Cancel</a>
    <p><input type="submit" value="Confirm"></p>
</form>
</body>
</html>
