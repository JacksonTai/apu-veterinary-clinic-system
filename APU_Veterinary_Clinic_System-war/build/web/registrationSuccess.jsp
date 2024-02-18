<%--
    Document   : registrationCompletion
    Created on : Feb 15, 2024, 7:28:37 PM
    Author     : Jackson Tai
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="constant.EndpointConstant" %>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/shared/component/head_source.jsp" %>
        <title>Registration Success</title>
    </head>
    <body>
        <h1>Registration Success!</h1>
        <a class="dropdown-item" href="<c:url value='<%= EndpointConstant.INDEX %>'/>">
            Home
        </a>
        <a class="dropdown-item" href="<c:url value='<%= EndpointConstant.STAFF_LOGIN %>'/>">
            Login
        </a>
    </body>
</html>
