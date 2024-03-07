<%-- 
    Document   : home
    Created on : Feb 6, 2024, 9:45:27 AM
    Author     : Jackson Tai
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/shared/component/head_source.jsp" %>
    <title>Staff - Home</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<main class="w-75 my-2 mx-auto overflow-x-auto">
    <div class="d-flex flex-column justify-content-center align-items-center" style="margin-top: 10%">
        <c:choose>
            <c:when test="${sessionScope.clinicUser.userRole eq MANAGING_STAFF}">
                <img src="${pageContext.request.contextPath}/asset/img/managingStaffHome.png" alt="Home Illustration">
            </c:when>
            <c:when test="${sessionScope.clinicUser.userRole eq RECEPTIONIST}">
                <img src="${pageContext.request.contextPath}/asset/img/receptionistHome.png" alt="Home Illustration">
            </c:when>
            <c:when test="${sessionScope.clinicUser.userRole eq VET}">
                <img src="${pageContext.request.contextPath}/asset/img/vetHome.png" alt="Home Illustration">
            </c:when>
        </c:choose>
        <h2 class="fs-4 mt-2">Hi ${sessionScope.clinicUser.fullName}, Welcome Back!</h2>
    </div>
</main>
<%@ include file="/shared/component/footer.jsp" %>
</body>
</html>
