<%-- 
    Document   : profile
    Created on : Feb 18, 2024, 10:41:18 AM
    Author     : Jackson Tai
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/shared/component/head_source.jsp" %>
        <title>Update Profile</title>
    </head>
    <body>
        <%@ include file="/shared/component/header.jsp" %>
        <h1>Profile</h1>
        <form action="<c:url value='<%= EndpointConstant.UPDATE_PROFILE %>'/>" method="POST">
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
                    <td>Staff Email</td>
                    <td>
                        <input type="text" name="email" value="${not empty param.email ? param.email : ''}">
                        <c:if test="${not empty emailError}">
                            <span style="color: red;">${emailError}</span>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td>Password: </td>
                    <td>
                        <input type="password" name="password" value="${not empty param.password ? param.password : ''}">
                        <c:if test="${not empty passwordError}">
                            <span style="color: red;">${passwordError}</span>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td>Confirm Password: </td>
                    <td>
                        <input type="password" name="confirmPassword" value="${not empty param.confirmPassword ? param.confirmPassword : ''}">
                        <c:if test="${not empty confirmPasswordError}">
                            <span style="color: red;">${confirmPasswordError}</span>
                        </c:if>
                    </td>
                </tr>
            </table>
            <p><input type="submit" value="Register"></p>
        </form>
    </body>
</html>
