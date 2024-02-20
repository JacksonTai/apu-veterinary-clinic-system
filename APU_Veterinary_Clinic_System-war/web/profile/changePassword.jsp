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
    <title>Change Password</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<h1>Change Password</h1>
<form action="<c:url value='<%= EndpointConstant.CHANGE_PASSWORD %>'/>" method="POST">
    <table>
        <tr>
            <td>Current Password:</td>
            <td>
                <input type="password" name="currentPassword"
                       value="${not empty param.currentPassword ? param.currentPassword : ''}">
                <c:if test="${not empty currentPasswordError}">
                    <span style="color: red;">${currentPasswordError}</span>
                </c:if>
            </td>
        </tr>
        <tr>
            <td>New Password:</td>
            <td>
                <input type="password" name="newPassword"
                       value="${not empty param.newPassword ? param.newPassword : ''}">
                <c:if test="${not empty passwordError}">
                    <span style="color: red;">${passwordError}</span>
                </c:if>
            </td>
        </tr>
        <tr>
            <td>Confirm Password:</td>
            <td>
                <input type="password" name="confirmPassword"
                       value="${not empty param.confirmPassword ? param.confirmPassword : ''}">
                <c:if test="${not empty confirmPasswordError}">
                    <span style="color: red;">${confirmPasswordError}</span>
                </c:if>
            </td>
        </tr>
        <tr>
            <td colspan="2">
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
