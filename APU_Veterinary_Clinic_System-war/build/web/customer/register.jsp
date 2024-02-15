<%-- 
    Document   : login
    Created on : Feb 6, 2024, 9:28:44 AM
    Author     : Jackson Tai
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/component/head_source.jsp" %>
        <title>Customer Registration - AVCS</title>
    </head>
    <body>
        <%@ include file="/component/header.jsp" %>
        <h1>Customer Registration</h1>
        <form action="Register" method="POST">
            <table>
                <tr>
                    <td>Full Name</td>
                    <td>
                        <input type="text" name="fullName">
                        <c:if test="${not empty fullNameError}">
                            <span style="color: red;">${fullNameError}</span>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td>Phone Number</td>
                    <td>
                        <input type="text" name="phoneNumber">
                        <c:if test="${not empty phoneNumberError}">
                            <span style="color: red;">${phoneNumberError}</span>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td>Email</td>
                    <td>
                        <input type="text" name="email">
                        <c:if test="${not empty emailError}">
                            <span style="color: red;">${emailError}</span>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td>Password: </td>
                    <td>
                        <input type="password" name="password">
                        <c:if test="${not empty passwordError}">
                            <span style="color: red;">${passwordError}</span>
                        </c:if>
                    </td>
                </tr>
            </table>
            <p><input type="submit" value="Register"></p>
        </form>
    </body>
</html>
