<%-- 
    Document   : home
    Created on : Feb 6, 2024, 9:45:27 AM
    Author     : Jackson Tai
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
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
                <table class="table table-bordered w-100 my-2">
                    <thead>
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Date</th>
                        <th scope="col">Customer</th>
                        <th scope="col">Assigned Vet</th>
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
                            <td>${StringUtil.requireNonNullElse(appointment.customerId, DASH)}</td>
                            <td>${StringUtil.requireNonNullElse(appointment.vetId, DASH)}</td>
                            <td>${StringUtil.requireNonNullElse(appointment.petId, DASH)}</td>
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
