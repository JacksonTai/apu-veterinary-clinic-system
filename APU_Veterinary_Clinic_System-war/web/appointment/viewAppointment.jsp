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
    <title>View Appointment</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<main class="w-75 my-2 mx-auto overflow-x-auto">
    <h1 class="text-center">View Appointment</h1>
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item">
                <a href="<c:url value='<%= EndpointConstant.VIEW_APPOINTMENT %>'/>">Appointment</a>
            </li>
            <li class="breadcrumb-item active" aria-current="page">View</li>
        </ol>
    </nav>
    <c:if test="${not empty notFoundMessage}">
        <%@ include file="/shared/component/notFound.jsp" %>
    </c:if>
    <c:if test="${empty notFoundMessage}">
        <h2>Appointment Details</h2>
        <div class="w-100 mx-auto overflow-x-auto">
            <table class="table w-100">
                <colgroup>
                    <col span="1" style="width: 9rem;">
                    <col span="1" style="width: auto;">
                </colgroup>
                <thead>
                <tr>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <th scope="row">ID:</th>
                    <td>${StringUtil.requireNonNullElse(appointment.appointmentId, DASH)}</td>
                </tr>
                <tr>
                    <th scope="row">Date:</th>
                    <td>${StringUtil.requireNonNullElse(
                            StringUtil.convertDateFormat(appointment.appointmentDate, DMY_SLASH_DATE_FORMAT), DASH)}
                    </td>
                </tr>
                <tr>
                    <th scope="row">Assigned Vet:</th>
                    <td>
                        <a href="<c:url value='<%= EndpointConstant.VIEW_STAFF %>'/>?id=${appointment.assignedVet.clinicUserId}"
                           target="_blank">${StringUtil.requireNonNullElse(appointment.assignedVet.fullName, DASH)}
                        </a>
                    </td>
                </tr>
                <tr>
                    <th scope="row">Customer:</th>
                    <td>
                        <a href="<c:url value='<%= EndpointConstant.VIEW_CUSTOMER %>'/>?id=${appointment.customer.customerId}"
                           target="_blank">${StringUtil.requireNonNullElse(appointment.customer.fullName, DASH)}
                        </a>
                    </td>
                </tr>
                <tr>
                    <th scope="row">Pet:</th>
                    <td>
                        <a href="<c:url value='<%= EndpointConstant.VIEW_PET %>'/>?id=${appointment.pet.petId}"
                           target="_blank">${StringUtil.requireNonNullElse(appointment.pet.name, DASH)}
                        </a>
                    </td>
                </tr>
                <tr>
                    <th scope="row">Status:</th>
                    <td>${StringUtil.requireNonNullElse(appointment.appointmentStatus, DASH)}</td>
                </tr>
                </tbody>
            </table>
        </div>
        <c:if test="${not empty sessionScope.clinicUser}">
            <c:if test="${sessionScope.clinicUser.userRole eq RECEPTIONIST}">
                <div class="mx-auto d-flex justify-content-end">
                    <a class="btn btn-secondary me-2"
                       href="<c:url value='<%= EndpointConstant.UPDATE_APPOINTMENT %>'/>?id=${appointment.appointmentId}"
                       role="button">Update
                    </a>
                </div>
            </c:if>
        </c:if>
        <h2 class="mt-5 mb-3">Medical Records</h2>
        <c:choose>
            <c:when test="${empty pets}">
                <div class="d-flex flex-column align-items-center justify-content-center">
                    <img src="${pageContext.request.contextPath}/asset/img/noRecord.png" alt="No Record Illustration"
                         width="25%">
                    <p class="text-center fs-5">The pet does not have any medical records yet</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="w-100 mx-auto overflow-x-auto">
                    <table class="table table-bordered w-100 my-2">
                        <thead>
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Diagnosis</th>
                            <th scope="col">Prognosis</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${medicalRecords}" var="medicalRecord">
                            <tr>
                                <td>${StringUtil.requireNonNullElse(medicalRecord.medicalRecordId, DASH)}</td>
                                <td>${StringUtil.requireNonNullElse(medicalRecord.diagnosis, DASH)}</td>
                                <td>${StringUtil.requireNonNullElse(medicalRecord.prognosis, DASH)}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
    </c:if>
</main>
<%@ include file="/shared/component/footer.jsp" %>
</body>
</html>
