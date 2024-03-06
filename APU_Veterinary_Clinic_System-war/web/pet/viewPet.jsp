<%-- 
    Document   : home
    Created on : Feb 6, 2024, 9:45:27 AM
    Author     : Jackson Tai
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<jsp:useBean id="StringUtil" class="util.StringUtil"/>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="/shared/component/head_source.jsp" %>
    <script>
        const contextPath = "${pageContext.request.contextPath}";
    </script>
    <script src="${pageContext.request.contextPath}/asset/js/constant/endpointConstant.js"></script>
    <script src="${pageContext.request.contextPath}/asset/js/shared/confirmDeleteModal.js"></script>
    <title>View Pet</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<main class="w-75 my-2 mx-auto overflow-x-auto">
    <h1 class="text-center">View Pet</h1>
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="<c:url value='<%= EndpointConstant.VIEW_PET %>'/>">Pets</a></li>
            <li class="breadcrumb-item active" aria-current="page">View</li>
        </ol>
    </nav>
    <c:if test="${not empty notFoundMessage}">
        <%@ include file="/shared/component/notFound.jsp" %>
    </c:if>
    <c:if test="${empty notFoundMessage}">
        <div class="w-100 mx-auto overflow-x-auto" style="max-width: 30rem">
            <table class="table">
                <thead>
                <tr>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <th scope="row">ID:</th>
                    <td>${StringUtil.requireNonNullElse(pet.petId, DASH)}</td>
                </tr>
                <tr>
                    <th scope="row">Species:</th>
                    <td>${StringUtil.requireNonNullElse(pet.species, DASH)}</td>
                </tr>
                <tr>
                    <th scope="row">Breed:</th>
                    <td>${StringUtil.requireNonNullElse(pet.breed, DASH)}</td>
                </tr>
                <tr>
                    <th scope="row">Name:</th>
                    <td>${StringUtil.requireNonNullElse(pet.name, DASH)}</td>
                </tr>
                <tr>
                    <th scope="row">Health status:</th>
                    <td>${StringUtil.requireNonNullElse(pet.healthStatus, DASH)}</td>
                </tr>
                <tr>
                    <th scope="row">Owner:</th>
                    <td>
                        <a href="<c:url value='<%= EndpointConstant.VIEW_CUSTOMER %>'/>?id=${customer.customerId}"
                           target="_blank">${StringUtil.requireNonNullElse(customer.fullName, DASH)}
                        </a>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <c:if test="${not empty sessionScope.clinicUser}">
            <c:if test="${sessionScope.clinicUser.userRole eq RECEPTIONIST}">
                <div class="mx-auto d-flex justify-content-center">
                    <a class="btn btn-secondary me-2"
                       href="<c:url value='<%= EndpointConstant.UPDATE_PET %>'/>?id=${pet.petId}"
                       role="button">Update
                    </a>
                    <a class="btn btn-danger" onclick="confirmDelete({
                            entityId: '${pet.petId}',
                            entityName: 'pet',
                            deleteEndpoint: endpoints.DELETE_PET,
                            successRedirectEndpoint: endpoints.VIEW_PET
                            })" role="button">Delete
                    </a>
                </div>
            </c:if>
        </c:if>
        <h2 class="mt-5 mb-3 text-center">Medical Records</h2>
        <c:if test="${not empty sessionScope.clinicUser}">
            <c:if test="${sessionScope.clinicUser.userRole eq VET}">
                <div class="mx-auto d-flex justify-content-end">
                    <a href="<c:url value='<%= EndpointConstant.CREATE_PET_MEDICAL_RECORD %>'/>?id=${pet.petId}"
                       role="button" class="btn btn-primary">Create Medical Record
                    </a>
                </div>
            </c:if>
        </c:if>
        <c:if test="${empty medicalRecords}">
            <div class="d-flex flex-column align-items-center justify-content-center">
                <img src="${pageContext.request.contextPath}/asset/img/noRecord.png"
                     alt="No Record Illustration" width="25%">
                <p class="text-center fs-5">The pet does not have any medical records yet</p>
            </div>
        </c:if>
        <c:if test="${not empty medicalRecords}">
            <div class="w-100 mx-auto overflow-x-auto">
                <table class="table table-bordered w-100 my-2">
                    <colgroup>
                        <col span="1" style="width: auto;">
                        <col span="1" style="width: auto;">
                        <col span="1" style="width: 15%;">
                        <col span="1" style="width: auto;">
                        <col span="1" style="width: 50%;">
                    </colgroup>
                    <thead>
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Date</th>
                        <th scope="col">Recording Vet</th>
                        <th scope="col">Diagnosis</th>
                        <th scope="col">Prognosis</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${medicalRecords}" var="medicalRecord">
                        <tr>
                            <td>${StringUtil.requireNonNullElse(medicalRecord.medicalRecordId, DASH)}</td>
                            <td>${StringUtil.requireNonNullElse(StringUtil.convertDateFormat(
                                    medicalRecord.medicalRecordDate, DMY_SLASH_DATE_FORMAT), DASH)}
                            </td>
                            <td>
                                <a href="<c:url value='<%= EndpointConstant.VIEW_STAFF %>'/>?id=${medicalRecord.recordingVet.clinicUserId}"
                                   target="_blank">
                                        ${StringUtil.requireNonNullElse(medicalRecord.recordingVet.fullName, DASH)}
                                </a>
                            </td>
                            <td>${StringUtil.requireNonNullElse(medicalRecord.diagnosis, DASH)}</td>
                            <td>${StringUtil.requireNonNullElse(medicalRecord.prognosis, DASH)}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </c:if>
</main>
<%@ include file="/shared/component/footer.jsp" %>
</body>
</html>
