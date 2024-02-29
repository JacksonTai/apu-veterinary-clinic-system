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
    <title>Staff</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<main class="w-75 my-2 mx-auto overflow-x-auto">
    <h1 class="text-center">Staff</h1>
    <div class="d-flex justify-content-end mb-3">
        <a class="btn btn-primary" href="<c:url value='<%= EndpointConstant.CREATE_STAFF %>'/>" role="button">
            Create Staff</a>
    </div>
    <c:set var="role" value="${param.role == null ? 'vet' : param.role}"/>
    <ul class="nav nav-tabs">
        <li class="nav-item">
            <a class="nav-link ${role eq 'vet' ? 'active' : ''}" aria-current="page"
               href="<c:url value='${EndpointConstant.VIEW_STAFF}'/>?role=vet">
                Vet
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${role eq 'receptionist' ? 'active' : ''}"
               href="<c:url value='${EndpointConstant.VIEW_STAFF}'/>?role=receptionist">
                Receptionist
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${role eq 'managingStaff' ? 'active' : ''}"
               href="<c:url value='${EndpointConstant.VIEW_STAFF}'/>?role=managingStaff">
                Managing Staff
            </a>
        </li>
    </ul>
    <c:choose>
        <c:when test="${empty staffs}">
            <div class="d-flex flex-column align-items-center justify-content-center">
                <img src="${pageContext.request.contextPath}/asset/img/noRecord.png" alt="No Record Illustration"
                     width="35%">
                <h2 class="text-center">There are no staff records currently</h2>
            </div>
        </c:when>
        <c:otherwise>
            <table class="table table-bordered w-100 h-100 mb-2">
                <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Full Name</th>
                    <th scope="col">Email</th>
                    <th scope="col">Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${staffs}" var="staff">
                    <tr>
                        <td>${StringUtil.requireNonNullElse(staff.clinicUserId, DASH)}</td>
                        <td>${StringUtil.requireNonNullElse(staff.fullName, DASH)}</td>
                        <td>${StringUtil.requireNonNullElse(staff.email, DASH)}</td>
                        <td>
                            <a class="btn btn-light btn-sm"
                               href="<c:url value='<%= EndpointConstant.VIEW_STAFF %>'/>?id=${staff.clinicUserId}"
                               role="button">
                                View
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <%@ include file="/shared/component/pagination.jsp" %>
        </c:otherwise>
    </c:choose>
</main>
<%@ include file="/shared/component/footer.jsp" %>
</body>
</html>
