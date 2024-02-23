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
    <title>Profile</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<main class="w-75 my-2 mx-auto overflow-x-auto">
    <h1 class="text-center">Profile</h1>
    <table class="table mx-auto" style="width: 30rem">
        <thead>
        <tr>
            <th scope="col"></th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <th scope="row">ID:</th>
            <td>${clinicUser.clinicUserId}</td>
        </tr>
        <tr>
            <th scope="row">Role:</th>
            <td>${clinicUser.userRole}</td>
        </tr>
        <tr>
            <th scope="row">Email:</th>
            <td>${clinicUser.email}</td>
        </tr>
        </tbody>
    </table>
    <div class="mx-auto d-flex justify-content-center">
        <a class="btn btn-secondary me-2" href="<c:url value='<%= EndpointConstant.UPDATE_PROFILE %>'/>" role="button">
            Update Profile</a>
        <a class="btn btn-secondary" href="<c:url value='<%= EndpointConstant.CHANGE_PASSWORD %>'/>" role="button">
            Change Password</a>
    </div>
</main>

</body>
</html>
