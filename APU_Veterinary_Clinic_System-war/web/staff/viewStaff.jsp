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
    <title>View Staff</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<main class="w-75 my-2 mx-auto overflow-x-auto">
    <h1 class="text-center">View Staff</h1>
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item">
                <a href="<c:url value='<%= EndpointConstant.VIEW_STAFF %>'/>">Staff</a>
            </li>
            <li class="breadcrumb-item active" aria-current="page">View</li>
        </ol>
    </nav>
    <c:if test="${not empty notFoundMessage}">
        <%@ include file="/shared/component/notFound.jsp" %>
    </c:if>
    <c:if test="${empty notFoundMessage}">
        <div class="mx-auto">
            <table class="table">
                <colgroup>
                    <col span="1" style="width: 9rem;">
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
                    <td>${StringUtil.requireNonNullElse(staff.clinicUserId, DASH)}</td>
                </tr>
                <tr>
                    <th scope="row">Full name:</th>
                    <td>${StringUtil.requireNonNullElse(staff.fullName, DASH)}</td>
                </tr>
                <tr>
                    <th scope="row">Email:</th>
                    <td>${StringUtil.requireNonNullElse(staff.email, DASH)}</td>
                </tr>
                </tbody>
            </table>
        </div>
        <c:if test="${not empty isOwnProfile and isOwnProfile eq false}">
            <div class="mx-auto d-flex justify-content-end">
                <a class="btn btn-secondary me-2"
                   href="<c:url value='<%= EndpointConstant.UPDATE_STAFF %>'/>?id=${staff.clinicUserId}"
                   role="button">Update
                </a>
                <a class="btn btn-danger" onclick="confirmDelete({
                        entityId: '${staff.clinicUserId}',
                        entityName: 'staff',
                        deleteEndpoint: endpoints.DELETE_STAFF,
                        successRedirectEndpoint: endpoints.VIEW_STAFF
                        })" role="button">Delete
                </a>
            </div>
        </c:if>
    </c:if>
</main>
<%@ include file="/shared/component/footer.jsp" %>
</body>
</html>
