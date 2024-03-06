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
    <title>Appointment</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<main class="w-75 my-2 mx-auto overflow-x-auto">
    <h1 class="text-center">Appointment</h1>
    <div class="d-flex justify-content-end mb-3">
        <a class="btn btn-primary" href="<c:url value='<%= EndpointConstant.CREATE_APPOINTMENT %>'/>" role="button">
            Create Appointment</a>
    </div>
    <c:set var="status" value="${param.status == null ? 'scheduled' : param.status}"/>
    <ul class="nav nav-tabs">
        <li class="nav-item">
            <a class="nav-link ${status eq 'scheduled' ? 'active' : ''}" aria-current="page"
               href="<c:url value='${EndpointConstant.VIEW_APPOINTMENT}'/>?status=scheduled">
                Scheduled
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${status eq 'ongoing' ? 'active' : ''}"
               href="<c:url value='${EndpointConstant.VIEW_APPOINTMENT}'/>?status=ongoing">
                Ongoing
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${status eq 'completed' ? 'active' : ''}"
               href="<c:url value='${EndpointConstant.VIEW_APPOINTMENT}'/>?status=completed">
                Completed
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${status eq 'cancelled' ? 'active' : ''}"
               href="<c:url value='${EndpointConstant.VIEW_APPOINTMENT}'/>?status=cancelled">
                Cancelled
            </a>
        </li>
    </ul>
    <c:choose>
        <c:when test="${empty appointments}">
            <div class="d-flex flex-column align-items-center justify-content-center">
                <img src="${pageContext.request.contextPath}/asset/img/noRecord.png" alt="No Record Illustration"
                     width="35%">
                <h2 class="text-center">There are no appointment records currently</h2>
            </div>
        </c:when>
        <c:otherwise>
            <div class="w-100 mx-auto overflow-x-auto">
                <table class="table table-bordered w-100 mb-2">
                    <thead>
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Date</th>
                        <th scope="col">Assigned Vet</th>
                        <th scope="col">Customer</th>
                        <th scope="col">Pet</th>
                        <th scope="col">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${appointments}" var="appointment">
                        <tr>
                            <td>${StringUtil.requireNonNullElse(appointment.appointmentId, DASH)}</td>
                            <td>${StringUtil.requireNonNullElse(
                                    StringUtil.convertDateFormat(appointment.appointmentDate, DMY_SLASH_DATE_FORMAT),
                                    DASH)}
                            </td>
                            <td>
                                <a href="<c:url value='<%= EndpointConstant.VIEW_STAFF %>'/>?id=${appointment.assignedVet.clinicUserId}"
                                   target="_blank">${StringUtil.requireNonNullElse(appointment.assignedVet.fullName, DASH)}
                                </a>
                            </td>
                            <td>
                                <a href="<c:url value='<%= EndpointConstant.VIEW_CUSTOMER %>'/>?id=${appointment.customer.customerId}"
                                   target="_blank">${StringUtil.requireNonNullElse(appointment.customer.fullName, DASH)}
                                </a>
                            </td>
                            <td>
                                <a href="<c:url value='<%= EndpointConstant.VIEW_PET %>'/>?id=${appointment.pet.petId}"
                                   target="_blank">${StringUtil.requireNonNullElse(appointment.pet.name, DASH)}
                                </a>
                            </td>
                            <td>
                                <a class="btn btn-light btn-sm" role="button"
                                   href="<c:url value='<%= EndpointConstant.VIEW_APPOINTMENT %>'/>?id=${appointment.appointmentId}">
                                    View
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <%@ include file="/shared/component/pagination.jsp" %>
        </c:otherwise>
    </c:choose>
</main>
<%@ include file="/shared/component/footer.jsp" %>
</body>
</html>
