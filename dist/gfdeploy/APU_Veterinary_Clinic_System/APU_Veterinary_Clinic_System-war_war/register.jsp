<%-- 
    Document   : login
    Created on : Feb 6, 2024, 9:28:44 AM
    Author     : Jackson Tai
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ include file="/component/head_source.jsp" %>
        <title>Register - AVCS</title>
    </head>
    <body>
        <%@ include file="/component/header.jsp" %>
        <h1>Registration</h1>
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
            <p><input type="submit" value="Register"></p>
        </form>
    </body>
</html>
