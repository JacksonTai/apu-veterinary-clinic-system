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
    <table class="table mx-auto" style="max-width: 30rem">
        <colgroup>
            <col span="1" style="width: 7rem;">
            <col span="1" style="width: auto;">
        </colgroup>
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
            <th scope="row">Full Name:</th>
            <td>${clinicUser.fullName}</td>
        </tr>
        <tr>
            <th scope="row">Email:</th>
            <td>${clinicUser.email}</td>
        </tr>
        <c:if test="${not empty sessionScope.clinicUser}">
            <c:if test="${sessionScope.clinicUser.userRole eq VET}">
                <tr>
                    <th scope="row">Expertises:</th>
                    <td>${expertises}</td>
                </tr>
            </c:if>
        </c:if>
        </tbody>
    </table>
    <div class="mx-auto d-flex justify-content-center">
        <a class="btn btn-secondary me-2" href="<c:url value='<%= EndpointConstant.UPDATE_PROFILE %>'/>" role="button">
            Update Profile</a>
        <a class="btn btn-secondary" href="<c:url value='<%= EndpointConstant.CHANGE_PASSWORD %>'/>" role="button">
            Change Password</a>
    </div>
</main>
<%@ include file="/shared/component/footer.jsp" %>
</body>
</html>
