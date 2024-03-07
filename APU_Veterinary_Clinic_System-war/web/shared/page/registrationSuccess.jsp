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
        <img src="${pageContext.request.contextPath}/asset/img/logo.png" alt="Registration success"
             class="d-block mx-auto">
        <h1 class="text-center">Registration Success</h1>
        <p class="fs-4 text-center">Your account will be approved soon!</p>
        <div class="mx-auto d-flex justify-content-center">
            <a class="btn btn-light" href="${pageContext.request.contextPath}" role="button">Home</a>
        </div>
    </div>
</main>
</body>
</html>
