<%-- 
    Document   : login
    Created on : Feb 6, 2024, 9:28:44 AM
    Author     : Jackson Tai
--%>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="/shared/component/head_source.jsp" %>
    <title>Update Staff</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<main class="w-75 my-2 mx-auto overflow-x-auto">
    <h1 class="text-center">Update Staff</h1>
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item">
                <a href="<c:url value='<%= EndpointConstant.VIEW_STAFF %>'/>">Staff</a>
            </li>
            <li class="breadcrumb-item">
                <a href="<c:url value='<%= EndpointConstant.VIEW_STAFF %>'/>?id=${staff.clinicUserId ?
               staff.clinicUserId : param.id}">View</a>
            </li>
            <li class="breadcrumb-item active" aria-current="page">Update</li>
        </ol>
    </nav>
    <form action="<c:url value='<%= EndpointConstant.UPDATE_STAFF %>'/>" method="POST" class="my-2 mx-auto"
          style="max-width: 30rem;">
        <%@ include file="shared/staffFormGroup.jsp" %>
        ${staff.userRole}
        <c:if test="${staff.userRole eq VET}">
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
        <div class="mx-auto d-flex justify-content-center">
            <a class="btn btn-light d-block me-2"
               href="<c:url value='<%= EndpointConstant.VIEW_STAFF %>'/>?id=${staff.clinicUserId ?
               staff.clinicUserId : param.id}" role="button">Cancel</a>
            <button type="submit" class="btn btn-primary">Confirm</button>
        </div>
    </form>
</main>
<%@ include file="/shared/component/footer.jsp" %>
</body>
</html>
