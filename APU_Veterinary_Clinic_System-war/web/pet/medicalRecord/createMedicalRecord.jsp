<%-- 
    Document   : login
    Created on : Feb 6, 2024, 9:28:44 AM
    Author     : Jackson Tai
--%>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="/shared/component/head_source.jsp" %>
    <title>Add Medical Record</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<main class="w-75 my-2 mx-auto overflow-x-auto">
    <h1 class="text-center">Add Medical Record</h1>
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="<c:url value='<%= EndpointConstant.VIEW_PET %>'/>">Pet</a>
            </li>
            <li class="breadcrumb-item"><a href="<c:url value='<%= EndpointConstant.VIEW_PET %>'/>?id=${pet.petId
             ? pet.petId : param.id}">View</a></li>
            <li class="breadcrumb-item active" aria-current="page">Add Medical Record</li>
        </ol>
    </nav>
    <form action="<c:url value='<%= EndpointConstant.CREATE_PET_MEDICAL_RECORD %>'/>" method="POST" class="my-2 mx-auto"
          style="max-width: 30rem;">
        <input type="hidden" name="formSubmitted" value="true">
        <input type="hidden" name="id" value="${pet.petId ? pet.petId : param.id}">
        <div class="mb-3">
            <label for="diagnosis" class="form-label">Diagnosis:</label>
            <input type="text" class="form-control" id="diagnosis" name="diagnosis"
                   value="${not empty param.diagnosis ? param.diagnosis : ''}">
            <c:if test="${not empty diagnosisError}">
                <span style="color: red;">${diagnosisError}</span>
            </c:if>
        </div>
        <div class="mb-3">
            <label for="prognosis" class="form-label">Prognosis:</label>
            <input type="text" class="form-control" id="prognosis" name="prognosis"
                   value="${not empty param.prognosis ? param.prognosis : ''}">
            <c:if test="${not empty prognosisError}">
                <span style="color: red;">${prognosisError}</span>
            </c:if>
        </div>
        <div class="mx-auto d-flex justify-content-center">
            <a class="btn btn-light d-block me-2" href="<c:url value='<%= EndpointConstant.VIEW_PET %>'/>?id=${pet.petId
             ? pet.petId : param.id}" role="button">Cancel</a>
            <button type="submit" class="btn btn-primary">Confirm</button>
        </div>
    </form>
</main>
<%@ include file="/shared/component/footer.jsp" %>
</body>
</html>
