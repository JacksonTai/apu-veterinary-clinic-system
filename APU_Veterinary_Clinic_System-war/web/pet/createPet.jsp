<%-- 
    Document   : login
    Created on : Feb 6, 2024, 9:28:44 AM
    Author     : Jackson Tai
--%>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="/shared/component/head_source.jsp" %>
    <title>Create Pet Profile</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<main class="w-75 my-2 mx-auto overflow-x-auto">
    <h1 class="text-center">Create Pet Profile</h1>
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="<c:url value='<%= EndpointConstant.VIEW_PET %>'/>">Pets</a></li>
            <li class="breadcrumb-item active" aria-current="page">Create</li>
        </ol>
    </nav>
    <form action="<c:url value='<%= EndpointConstant.CREATE_PET %>'/>" method="POST" class="my-2 mx-auto"
          style="max-width: 30rem;">
        <div class="mb-3">
            <label for="species" class="form-label">Species:</label>
            <input type="text" class="form-control" id="species" name="species"
                   value="${not empty param.species ? param.species : ''}">
            <c:if test="${not empty speciesError}">
                <span style="color: red;">${speciesError}</span>
            </c:if>
        </div>
        <div class="mb-3">
            <label for="breed" class="form-label">Breed:</label>
            <input type="text" class="form-control" id="breed" name="breed"
                   value="${not empty param.breed ? param.breed : ''}">
            <c:if test="${not empty breedError}">
                <span style="color: red;">${breedError}</span>
            </c:if>
        </div>
        <div class="mb-3">
            <label for="name" class="form-label">Name:</label>
            <input type="text" class="form-control" id="name" name="name"
                   value="${not empty param.name ? param.name : ''}">
            <c:if test="${not empty nameError}">
                <span style="color: red;">${nameError}</span>
            </c:if>
        </div>
        <div class="mb-3">
            <label for="healthStatus" class="form-label">Health Status:</label>
            <select id="healthStatus" name="healthStatus" class="form-select">
                <option value="Healthy" ${param.healthStatus == 'Healthy' ? 'selected' : ''}>
                    Healthy
                </option>
                <option value="Sick" ${param.healthStatus == 'Sick' ? 'selected' : ''}>
                    Sick
                </option>
                <option value="Dead" ${param.healthStatus == 'Dead' ? 'selected' : ''}>
                    Dead
                </option>
                <option value="Recovering" ${param.healthStatus == 'Recovering' ? 'selected' : ''}>
                    Recovering
                </option>
                <option value="Under Observation" ${param.healthStatus == 'Under Observation' ? 'selected' : ''}>
                    Under Observation
                </option>
                <option value="Critical Condition" ${param.healthStatus == 'Critical Condition' ? 'selected' : ''}>
                    Critical Condition
                </option>
            </select>
            <c:if test="${not empty healthStatusError}">
                <span style="color: red;">${healthStatusError}</span>
            </c:if>
        </div>
        <div class="mb-3">
            <label for="customerDetails" class="form-label">Customer Details (Owner):</label>
            <input type="text" class="form-control" id="customerDetails" name="customerDetails"
                   placeholder="Full name / ID / Phone number / Email address"
                   value="${not empty param.customerDetails ? param.customerDetails : ''}">
            <c:if test="${not empty customerDetailsError}">
                <span style="color: red;">${customerDetailsError}</span>
            </c:if>
        </div>
        <div class="mx-auto d-flex justify-content-center">
            <a class="btn btn-light d-block me-2" href="<c:url value='<%= EndpointConstant.VIEW_PET %>'/>"
               role="button">Cancel</a>
            <button type="submit" class="btn btn-primary">Confirm</button>
        </div>
    </form>
</main>
<%@ include file="/shared/component/footer.jsp" %>
</body>
</html>
