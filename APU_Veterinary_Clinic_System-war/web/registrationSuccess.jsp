<%--
    Document   : registrationCompletion
    Created on : Feb 15, 2024, 7:28:37 PM
    Author     : Jackson Tai
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="constant.EndpointConstant" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/shared/component/head_source.jsp" %>
    <title>Registration Success</title>
</head>
<body>
<main class="vw-100 d-flex justify-content-center" style="margin-top: 8rem">
    <div>
        <img src="${pageContext.request.contextPath}/asset/image/logo.png" alt="Registration success"
             class="d-block mx-auto">
        <h1 class="text-center">Registration Success!</h1>
        <div class="mx-auto d-flex justify-content-center">
            <a class="btn btn-light me-2" href="${pageContext.request.contextPath}" role="button">Home</a>
            <a class="btn btn-light" href="<c:url value='<%= EndpointConstant.STAFF_LOGIN %>'/>" role="button">Login</a>
        </div>
    </div>
</main>
</body>
</html>
