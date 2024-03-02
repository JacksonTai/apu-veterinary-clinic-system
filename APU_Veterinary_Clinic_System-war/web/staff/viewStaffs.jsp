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
    <script src="${pageContext.request.contextPath}/asset/js/shared/submitForm.js" defer></script>
    <script src="${pageContext.request.contextPath}/asset/js/constant/endpointConstant.js" defer></script>
    <script src="${pageContext.request.contextPath}/asset/js/staff/searchStaff.js" defer></script>
    <title>Staff Records</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<main class="w-75 my-2 mx-auto overflow-x-auto">
    <h1 class="text-center">Staff Records</h1>
    <div class="d-flex justify-content-between flex-wrap mb-3">
        <form method="POST" class="d-flex" id="searchStaffForm">
            <div class="me-2">
                <input type="text" name="searchInput" class="form-control mb-1" placeholder="ID / Full Name / Email"
                       value="${not empty param.searchInput ? param.searchInput : ''}">
                <span style="color: red;" id="searchError"></span>
                <a href="<c:url value='<%= EndpointConstant.VIEW_STAFF %>'/>" target="_blank" id="searchResult"
                   class="d-none mt-2 btn btn-outline-primary btn-sm"></a>
            </div>
            <button type="submit" class="btn btn-primary align-self-start">Search</button>
        </form>
        <a class="btn btn-primary align-self-start" href="<c:url value='<%= EndpointConstant.CREATE_STAFF %>'/>"
           role="button">
            Create Staff
        </a>
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
            <div class="w-100 mx-auto overflow-x-auto">
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
            </div>
            <%@ include file="/shared/component/pagination.jsp" %>
        </c:otherwise>
    </c:choose>
</main>
<%@ include file="/shared/component/footer.jsp" %>
</body>
</html>
