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
<main class="w-75 my-2 p-2 mx-auto overflow-x-auto">
    <h1 class="text-center">Update Profile</h1>
    <form action="<c:url value='<%= EndpointConstant.UPDATE_PROFILE %>'/>" method="POST" class="my-2 mx-auto"
          style="max-width: 30rem;">
        <c:if test="${not empty pendingMcExist && pendingMcExist eq true}">
            <div class="alert alert-warning mt-4" role="alert">
                This is your last profile update information, you may update again until it is approved.
            </div>
        </c:if>
        <c:if test="${noChanges}">
            <div class="alert alert-warning mt-4" role="alert">
                ${noChangesMessage}
            </div>
        </c:if>
        <input type="hidden" name="formSubmitted" value="true">
        <div class="mb-3 p-1">
            <label for="fullName" class="form-label">Full Name:</label>
            <input type="text" class="form-control" id="fullName" name="fullName"
                   value="${param.formSubmitted != null ? param.fullName : clinicUser.fullName}">
            <c:if test="${not empty fullNameError}">
                <span style="color: red;">${fullNameError}</span>
            </c:if>
        </div>
        <div class="mb-3 p-1">
            <label for="email" class="form-label">Email:</label>
            <input type="text" class="form-control" id="email" name="email"
                   value="${param.formSubmitted != null ? param.email : clinicUser.email}">
            <c:if test="${not empty emailError}">
                <span style="color: red;">${emailError}</span>
            </c:if>
        </div>
        <c:if test="${not empty sessionScope.clinicUser}">
            <c:if test="${sessionScope.clinicUser.userRole eq VET}">
                <div class="mb-3">
                    <label class="form-label">Expertises:</label>
                    <div class="btn-group d-flex flex-wrap" role="group"
                         aria-label="Basic checkbox toggle button group">
                        <c:forEach items="${expertises}" var="expertise">
                            <c:set var="isChecked" value="false"/>
                            <c:forEach items="${selectedExpertises}" var="vetExpertise">
                                <c:if test="${vetExpertise.expertiseId eq expertise.expertiseId}">
                                    <c:set var="isChecked" value="true"/>
                                </c:if>
                            </c:forEach>
                            <input type="checkbox" class="btn-check" autocomplete="off"
                                   name="expertise_${expertise.expertiseId}" id="expertise_${expertise.expertiseId}"
                                   <c:if test="${isChecked}">checked</c:if>>
                            <label class="btn btn-outline-primary m-1 rounded-0"
                                   for="expertise_${expertise.expertiseId}">${expertise.name}</label>
                        </c:forEach>
                    </div>
                </div>
            </c:if>
        </c:if>
        <div class="mb-3 p-1">
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
