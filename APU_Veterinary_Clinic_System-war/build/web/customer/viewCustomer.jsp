<%-- 
    Document   : home
    Created on : Feb 6, 2024, 9:45:27 AM
    Author     : Jackson Tai
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<jsp:useBean id="StringUtil" class="util.StringUtil"/>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="/shared/component/head_source.jsp" %>
    <script>
        const contextPath = "${pageContext.request.contextPath}";
    </script>
    <script src="${pageContext.request.contextPath}/asset/js/constant/endpointConstant.js"></script>
    <script src="${pageContext.request.contextPath}/asset/js/customer/deleteCustomer.js"></script>
    <title>Customer Profile</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<main class="w-75 my-2 mx-auto overflow-x-auto">
    <h1 class="text-center">Customer Profile</h1>
    <div class="mx-auto" style="max-width: 30rem">
        <a href="<c:url value='<%= EndpointConstant.VIEW_CUSTOMER %>'/>" role="button">Back</a>
        <table class="table">
            <thead>
            <tr>
                <th scope="col"></th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <th scope="row">ID:</th>
                <td>${StringUtil.requireNonNullElse(customer.customerId, DASH)}</td>
            </tr>
            <tr>
                <th scope="row">Full name:</th>
                <td>${StringUtil.requireNonNullElse(customer.fullName, DASH)}</td>
            </tr>
            <tr>
                <th scope="row">Email:</th>
                <td>${StringUtil.requireNonNullElse(customer.email, DASH)}</td>
            </tr>
            <tr>
                <th scope="row">Phone number:</th>
                <td>${StringUtil.requireNonNullElse(customer.phoneNumber, DASH)}</td>
            </tr>
            <tr>
                <th scope="row">Gender:</th>
                <td>${StringUtil.requireNonNullElse(customer.gender, DASH)}</td>
            </tr>
            <tr>
                <th scope="row">Date of Birth:</th>
                <td>${StringUtil.requireNonNullElse(
                        StringUtil.toLocalDateFormat(customer.dateOfBirth, DMY_SLASH_DATE_FORMAT), DASH)}</td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="mx-auto d-flex justify-content-center">
        <a class="btn btn-secondary me-2"
           href="<c:url value='<%= EndpointConstant.UPDATE_CUSTOMER %>'/>?id=${customer.customerId}"
           role="button">Update</a>
        <a class="btn btn-danger" onclick="confirmDelete('${customer.customerId}')" role="button">Delete</a>
    </div>
</main>
</body>
</html>
