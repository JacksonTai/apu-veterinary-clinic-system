<%-- 
    Document   : profile
    Created on : Feb 18, 2024, 10:41:18 AM
    Author     : Jackson Tai
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/shared/component/head_source.jsp" %>
    <title>Update Profile</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<main class="w-75 my-2 mx-auto overflow-x-auto">
    <h1 class="text-center">Update Profile</h1>
    <form action="<c:url value='<%= EndpointConstant.UPDATE_PROFILE %>'/>" method="POST" class="my-2 mx-auto"
          style="width: 15rem;">
        <input type="hidden" name="formSubmitted" value="true">
        <div class="mb-3">
            <label for="email" class="form-label">Email:</label>
            <input type="text" class="form-control" id="email" name="email"
                   value="${param.formSubmitted != null ? param.email : clinicUser.email}">
            <c:if test="${not empty emailError}">
                <span style="color: red;">${emailError}</span>
            </c:if>
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Password:</label>
            <input type="password" class="form-control" id="password" name="password"
                   value="${not empty param.password ? param.password : ''}">
            <c:if test="${not empty invalidCredentialError}">
                <span style="color: red;">${invalidCredentialError}</span>
            </c:if>
        </div>
        <div class="mx-auto d-flex justify-content-center">
            <a class="btn btn-light d-block me-2" href="<c:url value='<%= EndpointConstant.VIEW_PROFILE %>'/>"
               role="button">Cancel</a>
            <button type="submit" class="btn btn-primary">Confirm</button>
        </div>
    </form>
</main>
<%@ include file="/shared/component/footer.jsp" %>
</body>
</html>
