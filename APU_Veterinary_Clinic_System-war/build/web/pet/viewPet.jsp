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
    <script src="${pageContext.request.contextPath}/asset/js/shared/confirmDeleteModal.js"></script>
    <title>Pet Profile</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<main class="w-75 my-2 mx-auto overflow-x-auto">
    <h1 class="text-center">Pet Profile</h1>
    <div class="mx-auto" style="max-width: 30rem">
        <a href="<c:url value='<%= EndpointConstant.VIEW_PET %>'/>" role="button">Back</a>
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
                <td>${StringUtil.requireNonNullElse(pet.petId, DASH)}</td>
            </tr>
            <tr>
                <th scope="row">Species:</th>
                <td>${StringUtil.requireNonNullElse(pet.species, DASH)}</td>
            </tr>
            <tr>
                <th scope="row">Breed:</th>
                <td>${StringUtil.requireNonNullElse(pet.breed, DASH)}</td>
            </tr>
            <tr>
                <th scope="row">Name:</th>
                <td>${StringUtil.requireNonNullElse(pet.name, DASH)}</td>
            </tr>
            <tr>
                <th scope="row">Health status:</th>
                <td>${StringUtil.requireNonNullElse(pet.healthStatus, DASH)}</td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="mx-auto d-flex justify-content-center">
        <a class="btn btn-secondary me-2"
           href="<c:url value='<%= EndpointConstant.UPDATE_PET %>'/>?id=${pet.petId}"
           role="button">Update</a>
        <a class="btn btn-danger" onclick="confirmDelete({
                entityId: '${pet.petId}',
                entityName: 'pet',
                deleteEndpoint: endpoints.DELETE_PET,
                successRedirectEndpoint: endpoints.VIEW_PET
                })" role="button">Delete</a>
    </div>
</main>
</body>
</html>
