<%-- 
    Document   : login
    Created on : Feb 6, 2024, 9:28:44 AM
    Author     : Jackson Tai
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="/shared/component/head_source.jsp" %>
    <title>Staff Registration</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<h1>Staff Registration</h1>
<form action="<c:url value='<%= EndpointConstant.STAFF_REGISTER %>'/>" method="POST">
    <table>
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
            <td>Password:</td>
            <td>
                <input type="password" name="password" value="${not empty param.password ? param.password : ''}">
                <c:if test="${not empty passwordError}">
                    <span style="color: red;">${passwordError}</span>
                </c:if>
            </td>
        </tr>
        <tr>
            <td>User Role</td>
            <td>
                <select name="userRole">
                    <option value=${RECEPTIONIST} ${param.userRole eq RECEPTIONIST ? 'selected' : ''}>Receptionist
                    </option>
                    <option value="${VET}" ${param.userRole eq VET ? 'selected' : ''}>Vet</option>
                </select>
            </td>
        </tr>
    </table>
    <p><input type="submit" value="Register"></p>
</form>
</body>
</html>
