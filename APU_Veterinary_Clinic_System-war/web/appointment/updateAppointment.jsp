<%-- 
    Document   : login
    Created on : Feb 6, 2024, 9:28:44 AM
    Author     : Jackson Tai
--%>
<jsp:useBean id="StringUtil" class="util.StringUtil"/>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="/shared/component/head_source.jsp" %>
    <title>Update Appointment</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<main class="w-75 my-2 mx-auto overflow-x-auto">
    <h1 class="text-center">Update Appointment</h1>
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item">
                <a href="<c:url value='<%= EndpointConstant.VIEW_APPOINTMENT %>'/>">Appointment</a>
            </li>
            <li class="breadcrumb-item">
                <a href="<c:url value='<%= EndpointConstant.VIEW_CUSTOMER %>'/>?id=${
            appointment.appointmentId ? appointment.appointmentId : param.id}">View</a>
            </li>
            <li class="breadcrumb-item active" aria-current="page">Update</li>
        </ol>
    </nav>
    <c:if test="${not empty notFoundMessage}">
        <%@ include file="/shared/component/notFound.jsp" %>
    </c:if>
    <c:if test="${empty notFoundMessage}">
        <form action="<c:url value='<%= EndpointConstant.UPDATE_APPOINTMENT %>'/>" method="POST" class="my-2 mx-auto"
              style="max-width: 30rem;">
            <input type="hidden" name="formSubmitted" value="true">
            <input type="hidden" name="id" value="${appointment.appointmentId ? appointment.appointmentId : param.id}">
            <div class="mb-3">
                <label for="appointmentId" class="form-label">ID:</label>
                <input type="text" class="form-control" id="appointmentId" name="appointmentId" disabled
                       value="${StringUtil.requireNonNullElse(appointment.appointmentId, DASH)}">
            </div>
            <div class="mb-3">
                <label for="appointmentDate" class="form-label">Date:</label>
                <input type="text" class="form-control" id="appointmentDate" name="appointmentDate" disabled
                       value="${StringUtil.requireNonNullElse(
                            StringUtil.convertDateFormat(appointment.appointmentDate, DMY_SLASH_DATE_FORMAT), DASH)}">
            </div>
            <div class="mb-3">
                <label for="assignedVet" class="form-label">Assigned Vet:</label>
                <input type="text" class="form-control" id="assignedVet" name="assignedVet" disabled
                       value="${StringUtil.requireNonNullElse(appointment.assignedVet.fullName, DASH)}">
            </div>
            <div class="mb-3">
                <label for="customer" class="form-label">Customer:</label>
                <input type="text" class="form-control" id="customer" name="customer" disabled
                       value="${StringUtil.requireNonNullElse(appointment.customer.fullName, DASH)}">
            </div>
            <div class="mb-3">
                <label for="pet" class="form-label">Pet:</label>
                <input type="text" class="form-control" id="pet" name="pet" disabled
                       value="${StringUtil.requireNonNullElse(appointment.pet.name, DASH)}">
            </div>
            <div class="mb-3">
                <label for="appointmentStatus" class="form-label">Status:</label>
                <select id="appointmentStatus" name="appointmentStatus" class="form-select">
                    <option value="Scheduled"
                        ${param.formSubmitted != null ? (param.appointmentStatus == 'Scheduled' ? 'selected' : '') :
                                (appointment.appointmentStatus == 'Scheduled' ? 'selected' : '')}>
                        Scheduled
                    </option>
                    <option value="Ongoing"
                        ${param.formSubmitted != null ? (param.appointmentStatus == 'Ongoing' ? 'selected' : '') :
                                (appointment.appointmentStatus == 'Ongoing' ? 'selected' : '')}>
                        Ongoing
                    </option>
                    <option value="Completed"
                        ${param.formSubmitted != null ? (param.appointmentStatus == 'Completed' ? 'selected' : '') :
                                (appointment.appointmentStatus == 'Completed' ? 'selected' : '')}>
                        Completed
                    </option>
                    <option value="Cancelled"
                        ${param.formSubmitted != null ? (param.appointmentStatus == 'Cancelled' ? 'selected' : '') :
                                (appointment.appointmentStatus == 'Cancelled' ? 'selected' : '')}>
                        Cancelled
                    </option>
                </select>
                <c:if test="${not empty appointmentStatusError}">
                    <span style="color: red;">${appointmentStatusError}</span>
                </c:if>
            </div>
            <div class="mx-auto d-flex justify-content-center">
                <a class="btn btn-light d-block me-2"
                   href="<c:url value='<%= EndpointConstant.VIEW_APPOINTMENT %>'/>?id=${appointment.appointmentId ?
                appointment.appointmentId : param.id}" role="button">Cancel</a>
                <button type="submit" class="btn btn-primary">Confirm</button>
            </div>
        </form>
    </c:if>
</main>
<%@ include file="/shared/component/footer.jsp" %>
</body>
</html>
