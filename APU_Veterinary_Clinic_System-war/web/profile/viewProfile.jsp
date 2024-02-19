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
        <title>Profile</title>
    </head>
    <body>
        <%@ include file="/shared/component/header.jsp" %>
        <h1>Profile</h1>
        <table>
            <tr>
                <td>User ID: </td>
                <td>${clinicUser.clinicUserId}</td>
            </tr>
            <tr>
                <td>Full Name: </td>
                <td>${clinicUser.fullName}</td>
            </tr>
            <tr>
                <td>Email: </td>
                <td>${clinicUser.email}</td>
            </tr>
            <tr>
                <td>Phone Number: </td>
                <td>${clinicUser.phoneNumber}</td>
            </tr>
        </table>
        <a href="<c:url value='<%= EndpointConstant.UPDATE_PROFILE %>'/>">Edit Profile</a>
    </body>
</html>
