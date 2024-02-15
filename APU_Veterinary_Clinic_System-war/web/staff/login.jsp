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
        <title>Staff Login</title>
    </head>
    <body>
        <%@ include file="/component/header.jsp" %>
        <h1>Login</h1>
        <form action="Login" method="POST">
            <table>
                <tr>
                    <td>Email</td>
                    <td><input type="text" name="email" size="20"></td>
                </tr>
                <tr>
                    <td>Password: </td>
                    <td><input type="text" name="password" size="20"></td>
                </tr>
            </table>
            <p><input type="submit" value="Login"></p>
        </form>
    </body>
</html>
