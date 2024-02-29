<%-- 
    Document   : login
    Created on : Feb 6, 2024, 9:28:44 AM
    Author     : Jackson Tai
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="/shared/component/head_source.jsp" %>
    <title>Staff Registration</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<main class="w-75 my-2 mx-auto overflow-x-auto d-flex flex-column align-items-center justify-content-center">
    <h1 class="text-center">Staff Registration</h1>
    <form action="<c:url value='<%= EndpointConstant.STAFF_REGISTER %>'/>" method="POST" class="my-2 mx-auto"
          style="width: 15rem;">
        <%@ include file="shared/staffFormGroup.jsp" %>
        <button type="submit" class="btn btn-primary d-block mx-auto">Register</button>
    </form>
</main>
<%@ include file="/shared/component/footer.jsp" %>
</body>
</html>
