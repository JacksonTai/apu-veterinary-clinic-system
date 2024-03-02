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
    <script src="${pageContext.request.contextPath}/asset/js/constant/endpointConstant.js"></script>
    <script src="${pageContext.request.contextPath}/asset/js/shared/confirmDeleteModal.js"></script>
    <title>Expertises</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<main class="w-75 my-2 mx-auto overflow-x-auto">
    <h1 class="text-center">Expertises</h1>
    <div class="d-flex justify-content-end mb-3">
        <a class="btn btn-primary" href="<c:url value='<%= EndpointConstant.CREATE_EXPERTISE %>'/>" role="button">
            Add Expertise</a>
    </div>
    <c:choose>
        <c:when test="${empty expertises}">
            <div class="d-flex flex-column align-items-center justify-content-center">
                <img src="${pageContext.request.contextPath}/asset/img/noRecord.png" alt="No Record Illustration"
                     width="35%">
                <h2 class="text-center">There are no expertise records currently</h2>
            </div>
        </c:when>
        <c:otherwise>
            <div class="w-100 mx-auto overflow-x-auto">
                <table class="table table w-100 my-2 mx-auto" style="max-width: 30rem">
                    <thead>
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Name</th>
                        <th scope="col">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${expertises}" var="expertise">
                        <tr>
                            <td>${StringUtil.requireNonNullElse(expertise.expertiseId, DASH)}</td>
                            <td>${StringUtil.requireNonNullElse(expertise.name, DASH)}</td>
                            <td>
                                <a class="btn btn-danger btn-sm" onclick="confirmDelete({
                                        entityId: '${expertise.expertiseId}',
                                        entityName: 'expertise',
                                        deleteEndpoint: endpoints.DELETE_EXPERTISE,
                                        successRedirectEndpoint: endpoints.VIEW_EXPERTISE
                                        })" role="button">Delete
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <%@ include file="/shared/component/pagination.jsp" %>
        </c:otherwise>
    </c:choose>
</main>
<%@ include file="/shared/component/footer.jsp" %>
</body>
</html>
