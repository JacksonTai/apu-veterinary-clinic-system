<%-- 
    Document   : index.jsp
    Created on : Feb 6, 2024, 9:35:09 AM
    Author     : Jackson Tai
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/shared/component/head_source.jsp" %>
    <title>AVCS Back-office System</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<main class="w-75 my-2 mx-auto overflow-x-auto d-flex flex-column align-items-center justify-content-center">
    <img src="${pageContext.request.contextPath}/asset/img/home.png" alt="Illustration picture"
         class="d-inline-block align-text-top">
    <h1 class="text-center">"Veterinary care for both domestic and exotic pets"</h1>
    <p class="text-center fs-5">Asia Pacific University (APU) Veterinary Clinic and Boarding (AVCS) Back-office
        System</p>
</main>
<%@ include file="/shared/component/footer.jsp" %>
</body>
</html>
