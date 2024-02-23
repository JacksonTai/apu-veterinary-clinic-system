<%-- 
    Document   : login
    Created on : Feb 6, 2024, 9:28:44 AM
    Author     : Jackson Tai
--%>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="/shared/component/head_source.jsp" %>
    <title>Update Pet Profile</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<main class="w-75 my-2 mx-auto overflow-x-auto">
    <h1 class="text-center">Update Pet Profile</h1>
    <form action="<c:url value='<%= EndpointConstant.UPDATE_PET %>'/>" method="POST" class="my-2 mx-auto"
          style="max-width: 30rem;">
        <input type="hidden" name="formSubmitted" value="true">
        <input type="hidden" name="id" value="${pet.petId ? pet.petId : param.id}">
        <div class="mb-3">
            <label for="species" class="form-label">Species:</label>
            <input type="text" class="form-control" id="species" name="species"
                   value="${param.formSubmitted != null ? param.species : pet.species}">
            <c:if test="${not empty speciesError}">
                <span style="color: red;">${speciesError}</span>
            </c:if>
        </div>
        <div class="mb-3">
            <label for="breed" class="form-label">Breed:</label>
            <input type="text" class="form-control" id="breed" name="breed"
                   value="${param.formSubmitted != null ? param.breed : pet.breed}">
            <c:if test="${not empty breedError}">
                <span style="color: red;">${breedError}</span>
            </c:if>
        </div>
        <div class="mb-3">
            <label for="name" class="form-label">Name:</label>
            <input type="text" class="form-control" id="name" name="name"
                   value="${param.formSubmitted != null ? param.name : pet.name}">
            <c:if test="${not empty nameError}">
                <span style="color: red;">${nameError}</span>
            </c:if>
        </div>
        <div class="mb-3">
            <label for="healthStatus" class="form-label">Health Status:</label>
            <select id="healthStatus" name="healthStatus" class="form-select">
                <option value="Healthy" ${param.formSubmitted != null ?
                        (param.healthStatus == 'Healthy' ? 'selected' : '') :
                        (pet.healthStatus == 'Healthy' ? 'selected' : '')}>
                    Healthy
                </option>
                <option value="Sick" ${param.formSubmitted != null ?
                        (param.healthStatus == 'Sick' ? 'selected' : '') :
                        (pet.healthStatus == 'Sick' ? 'selected' : '')}>
                    Sick
                </option>
                <option value="Dead" ${param.formSubmitted != null ?
                        (param.healthStatus == 'Dead' ? 'selected' : '') :
                        (pet.healthStatus == 'Dead' ? 'selected' : '')}>
                    Dead
                </option>
                <option value="Recovering" ${param.formSubmitted != null ?
                        (param.healthStatus == 'Recovering' ? 'selected' : '') :
                        (pet.healthStatus == 'Recovering' ? 'selected' : '')}>
                    Recovering
                </option>
                <option value="Under Observation" ${param.formSubmitted != null ?
                        (param.healthStatus == 'Under Observation' ? 'selected' : '') :
                        (pet.healthStatus == 'Under Observation' ? 'selected' : '')}>
                    Under Observation
                </option>
                <option value="Critical Condition" ${param.formSubmitted != null ?
                        (param.healthStatus == 'Critical Condition' ? 'selected' : '') :
                        (pet.healthStatus == 'Critical Condition' ? 'selected' : '')}>
                    Critical Condition
                </option>
            </select>
            <c:if test="${not empty healthStatusError}">
                <span style="color: red;">${healthStatusError}</span>
            </c:if>
        </div>
        <div class="mx-auto d-flex justify-content-center">
            <a class="btn btn-light d-block me-2" href="<c:url value='<%= EndpointConstant.VIEW_PET %>'/>?id=${pet.petId
             ? pet.petId : param.id}" role="button">Cancel</a>
            <button type="submit" class="btn btn-primary">Confirm</button>
        </div>
    </form>
</main>
</body>
</html>
