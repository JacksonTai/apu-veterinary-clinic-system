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
    <title>Customer Profile</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<main class="w-75 my-2 mx-auto overflow-x-auto">
    <h1 class="text-center">Customer Profile</h1>
    <c:if test="${not empty sessionScope.clinicUser}">
        <c:if test="${sessionScope.clinicUser.userRole eq RECEPTIONIST}">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item">
                        <a href="<c:url value='<%= EndpointConstant.VIEW_CUSTOMER %>'/>">Customers</a>
                    </li>
                    <li class="breadcrumb-item active" aria-current="page">View</li>
                </ol>
            </nav>
        </c:if>
    </c:if>
    <c:if test="${not empty notFoundMessage}">
        <%@ include file="/shared/component/notFound.jsp" %>
    </c:if>
    <c:if test="${empty notFoundMessage}">
        <h2>Customer Details</h2>
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
                    <td>${StringUtil.requireNonNullElse(customer.customerId, DASH)}</td>
                </tr>
                <tr>
                    <th scope="row">Full name:</th>
                    <td>${StringUtil.requireNonNullElse(customer.fullName, DASH)}</td>
                </tr>
                <tr>
                    <th scope="row">Email:</th>
                    <td>${StringUtil.requireNonNullElse(customer.email, DASH)}</td>
                </tr>
                <tr>
                    <th scope="row">Phone number:</th>
                    <td>${StringUtil.requireNonNullElse(customer.phoneNumber, DASH)}</td>
                </tr>
                <tr>
                    <th scope="row">Gender:</th>
                    <td>${StringUtil.requireNonNullElse(customer.gender, DASH)}</td>
                </tr>
                <tr>
                    <th scope="row">Date of Birth:</th>
                    <td>${StringUtil.requireNonNullElse(
                            StringUtil.convertDateFormat(customer.dateOfBirth, DMY_SLASH_DATE_FORMAT), DASH)}</td>
                </tr>
                <tr>
                    <th scope="row">Address:</th>
                    <td>${StringUtil.requireNonNullElse(customer.address, DASH)}</td>
                </tr>
                </tbody>
            </table>
        </div>
        <c:if test="${not empty sessionScope.clinicUser}">
            <c:if test="${sessionScope.clinicUser.userRole eq RECEPTIONIST}">
                <div class="mx-auto d-flex justify-content-end">
                    <a class="btn btn-secondary me-2"
                       href="<c:url value='<%= EndpointConstant.UPDATE_CUSTOMER %>'/>?id=${customer.customerId}"
                       role="button">Update
                    </a>
                    <a class="btn btn-danger" onclick="confirmDelete({
                            entityId: '${customer.customerId}',
                            entityName: 'customer',
                            deleteEndpoint: endpoints.DELETE_CUSTOMER,
                            successRedirectEndpoint: endpoints.VIEW_CUSTOMER
                            })" role="button">Delete
                    </a>
                </div>
            </c:if>
        </c:if>
        <h2 class="mt-5 mb-3">Pet Owned</h2>
        <c:choose>
            <c:when test="${empty pets}">
                <div class="d-flex flex-column align-items-center justify-content-center">
                    <img src="${pageContext.request.contextPath}/asset/img/noRecord.png" alt="No Record Illustration"
                         width="25%">
                    <p class="text-center fs-5">The customer does not have any pets yet</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="w-100 mx-auto overflow-x-auto">
                    <%@ include file="/pet/shared/petTable.jsp" %>
                </div>
            </c:otherwise>
        </c:choose>
    </c:if>
</main>
<%@ include file="/shared/component/footer.jsp" %>
</body>
</html>
