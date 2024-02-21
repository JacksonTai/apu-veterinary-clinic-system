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
                <td>User Type: </td>
                <td>${clinicUser.userType}</td>
            </tr>
            <tr>
                <td>Email: </td>
                <td>${clinicUser.email}</td>
            </tr>
        </table>
        <a href="<c:url value='<%= EndpointConstant.UPDATE_PROFILE %>'/>">Update Profile</a>
        <a href="<c:url value='<%= EndpointConstant.CHANGE_PASSWORD %>'/>">Change Password</a>
    </body>
</html>
