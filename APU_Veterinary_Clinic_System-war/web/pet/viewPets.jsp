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
    <title>Customer</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<main class="w-75 my-2 mx-auto overflow-x-auto">
    <h1 class="text-center">Pets</h1>
    <a class="btn btn-primary" href="<c:url value='<%= EndpointConstant.CREATE_PET %>'/>" role="button">
        Add Pet</a>
    <c:choose>
        <c:when test="${empty pets}">
            <p class="text-center">There are no pet records currently</p>
        </c:when>
        <c:otherwise>
            <table class="table table-bordered w-100 my-2">
                <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Species</th>
                    <th scope="col">Breed</th>
                    <th scope="col">Name</th>
                    <th scope="col">Health Status</th>
                    <th scope="col">Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${pets}" var="pet">
                    <tr>
                        <td>${StringUtil.requireNonNullElse(pet.petId, DASH)}</td>
                        <td>${StringUtil.requireNonNullElse(pet.species, DASH)}</td>
                        <td>${StringUtil.requireNonNullElse(pet.breed, DASH)}</td>
                        <td>${StringUtil.requireNonNullElse(pet.name, DASH)}</td>
                        <td>${StringUtil.requireNonNullElse(pet.healthStatus, DASH)}</td>
                        <td>
                            <a class="btn btn-light btn-sm"
                               href="<c:url value='<%= EndpointConstant.VIEW_PET %>'/>?id=${pet.petId}"
                               role="button">View</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <%@ include file="/shared/component/pagination.jsp" %>
        </c:otherwise>
    </c:choose>
</main>
</body>
</html>
