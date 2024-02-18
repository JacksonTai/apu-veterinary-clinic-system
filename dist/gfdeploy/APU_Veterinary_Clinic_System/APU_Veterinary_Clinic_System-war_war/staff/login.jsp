<%--
    Document   : login
    Created on : Feb 6, 2024, 9:28:44 AM
    Author     : Jackson Tai
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/shared/component/head_source.jsp" %>
        <title>Staff Login</title>
    </head>
    <body>
        <%@ include file="/shared/component/header.jsp" %>
        <h1>Staff Login</h1>
        <form action="<c:url value='<%= EndpointConstant.STAFF_LOGIN %>'/>" method="POST">
            <table>
                <tr>
                    <td>Staff Email: </td>
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
                    <td colspan="2">
                        <c:if test="${not empty invalidLoginError}">
                            <span style="color: red;">${invalidLoginError}</span>
                        </c:if>
                    </td>
                </tr>
            </table>
            <p><input type="submit" value="Login"></p>
        </form>
    </body>
</html>
