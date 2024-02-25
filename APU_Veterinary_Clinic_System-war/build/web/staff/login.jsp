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
    <title>Staff Login</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<main class="w-75 my-2 mx-auto overflow-x-auto d-flex flex-column align-items-center justify-content-center">
    <h1 class="text-center">Staff Login</h1>
    <form action="<c:url value='<%= EndpointConstant.STAFF_LOGIN %>'/>" method="POST" class="my-2 mx-auto"
          style="width: 15rem;">
        <div class="mb-3">
            <label for="email" class="form-label">Email:</label>
            <input type="text" class="form-control" id="email" name="email"
                   value="${not empty param.email ? param.email : ''}">
            <c:if test="${not empty emailError}">
                <span style="color: red;">${emailError}</span>
            </c:if>
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Password:</label>
            <input type="password" class="form-control" id="password" name="password"
                   value="${not empty param.password ? param.password : ''}">
            <c:if test="${not empty passwordError}">
                <span style="color: red;">${passwordError}</span>
            </c:if>
        </div>
        <div class="mb-3">
            <c:if test="${not empty invalidCredentialError}">
                <span style="color: red;">${invalidCredentialError}</span>
            </c:if>
        </div>
        <button type="submit" class="btn btn-primary d-block mx-auto">Login</button>
    </form>
</main>
<%@ include file="/shared/component/footer.jsp" %>
</body>
</html>
