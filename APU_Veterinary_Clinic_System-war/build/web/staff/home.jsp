<%-- 
    Document   : home
    Created on : Feb 6, 2024, 9:45:27 AM
    Author     : Jackson Tai
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/shared/component/head_source.jsp" %>
        <title>Staff - Home</title>
    </head>
    <body>
        <%@ include file="/shared/component/header.jsp" %>
        staff home works
        ${sessionScope.clinicUser}
    </body>
</html>
