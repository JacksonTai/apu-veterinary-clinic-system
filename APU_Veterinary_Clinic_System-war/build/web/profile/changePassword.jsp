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
    <title>Change Password</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<main class="w-75 my-2 mx-auto overflow-x-auto d-flex flex-column align-items-center justify-content-center">
    <h1 class="text-center">Change Password</h1>
    <form action="<c:url value='<%= EndpointConstant.CHANGE_PASSWORD %>'/>" method="POST" class="my-2 mx-auto"
          style="width: 15rem;">
        <div class="mb-3">
            <label for="currentPassword" class="form-label">Current Password:</label>
            <input type="password" class="form-control" id="currentPassword" name="currentPassword"
                   value="${not empty param.currentPassword ? param.currentPassword : ''}">
            <c:if test="${not empty invalidCredentialError}">
                <span style="color: red;">${invalidCredentialError}</span>
            </c:if>
        </div>
        <div class="mb-3">
            <label for="newPassword" class="form-label">New Password:</label>
            <input type="password" class="form-control" id="newPassword" name="newPassword"
                   value="${not empty param.newPassword ? param.newPassword : ''}">
            <c:if test="${not empty passwordError}">
                <span style="color: red;">${passwordError}</span>
            </c:if>
        </div>
        <div class="mb-3">
            <label for="confirmPassword" class="form-label">Confirm Password:</label>
            <input type="password" class="form-control" id="confirmPassword" name="confirmPassword"
                   value="${not empty param.confirmPassword ? param.confirmPassword : ''}">
            <c:if test="${not empty confirmPasswordError}">
                <span style="color: red;">${confirmPasswordError}</span>
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
