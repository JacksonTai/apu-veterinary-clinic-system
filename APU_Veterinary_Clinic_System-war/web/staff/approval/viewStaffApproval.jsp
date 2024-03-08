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
    <script src="${pageContext.request.contextPath}/asset/js/shared/submitForm.js" defer></script>
    <script src="${pageContext.request.contextPath}/asset/js/constant/endpointConstant.js" defer></script>
    <script src="${pageContext.request.contextPath}/asset/js/staff/approval/viewStaffApproval.js" defer></script>
    <title>View Staff Approval</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<main class="w-75 my-2 mx-auto overflow-x-auto">
    <h1 class="text-center">View Staff Approval</h1>
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item">
                <a href="<c:url value='<%= EndpointConstant.VIEW_STAFF_APPROVAL %>'/>">Staff Approval</a>
            </li>
            <li class="breadcrumb-item active" aria-current="page">View</li>
        </ol>
    </nav>
    <c:if test="${not empty notFoundMessage}">
        <%@ include file="/shared/component/notFound.jsp" %>
    </c:if>
    <c:if test="${empty notFoundMessage}">
        <div class="mx-auto">
            <table class="table">
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
                    <td>${StringUtil.requireNonNullElse(staffApproval.makerCheckerId, DASH)}</td>
                </tr>
                <tr>
                    <th scope="row">Maker:</th>
                    <td>
                        <a href="<c:url value='<%= EndpointConstant.VIEW_STAFF %>'/>?id=${currentMakerValue.clinicUserId}"
                           target="_blank" role="button">
                                ${StringUtil.requireNonNullElse(currentMakerValue.fullName, DASH)}
                        </a>
                    </td>
                </tr>
                <tr>
                    <th scope="row">Checker:</th>
                    <td>
                        <c:if test="${not empty checker.clinicUserId}">
                            <a href="<c:url value='<%= EndpointConstant.VIEW_STAFF %>'/>?id=${checker.clinicUserId}"
                               target="_blank" role="button">
                                    ${checker.fullName}
                            </a>
                        </c:if>
                        <c:if test="${empty checker.clinicUserId}">
                            ${DASH}
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <th scope="row">Module:</th>
                    <td>${StringUtil.requireNonNullElse(StringUtil.toTitleCase(staffApproval.module), DASH)}</td>
                </tr>
                <tr>
                    <th scope="row">Action Type:</th>
                    <td>${StringUtil.requireNonNullElse(StringUtil.toTitleCase(staffApproval.actionType), DASH)}</td>
                </tr>
                <tr>
                    <th scope="row">Status:</th>
                    <td>${StringUtil.requireNonNullElse(StringUtil.toTitleCase(staffApproval.status), DASH)}</td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="w-100 mx-auto overflow-x-auto">
            <table class="table table mt-5 w-100">
                <thead>
                <tr>
                    <th scope="col"></th>
                    <th scope="col">Original Value</th>
                    <th scope="col">New Value</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${not empty currentMakerValue.clinicUserId or not empty newMakerValue.clinicUserId}">
                    <tr>
                        <th scope="row">ID:</th>
                        <td>${StringUtil.requireNonNullElse(currentMakerValue.clinicUserId, DASH)}</td>
                        <td>${StringUtil.requireNonNullElse(newMakerValue.clinicUserId, DASH)}</td>
                    </tr>
                </c:if>
                <c:if test="${not empty currentMakerValue.fullName or not empty newMakerValue.fullName}">
                    <tr>
                        <th scope="row">Full Name:</th>
                        <td>${StringUtil.requireNonNullElse(currentMakerValue.fullName, DASH)}</td>
                        <td>${StringUtil.requireNonNullElse(newMakerValue.fullName, DASH)}</td>
                    </tr>
                </c:if>
                <c:if test="${not empty currentMakerValue.email or not empty newMakerValue.email}">
                    <tr>
                        <th scope="row">Email:</th>
                        <td>${StringUtil.requireNonNullElse(currentMakerValue.email, DASH)}</td>
                        <td>${StringUtil.requireNonNullElse(newMakerValue.email, DASH)}</td>
                    </tr>
                </c:if>
                <c:if test="${not empty currentMakerValue.userRole or not empty newMakerValue.userRole}">
                    <tr>
                        <th scope="row">User Role:</th>
                        <td>${StringUtil.requireNonNullElse(currentMakerValue.userRole, DASH)}</td>
                        <td>${StringUtil.requireNonNullElse(newMakerValue.userRole, DASH)}</td>
                    </tr>
                </c:if>
                <c:if test="${not empty currentMakerValue.status or not empty newMakerValue.status}">
                    <tr>
                        <th scope="row">Account Status:</th>
                        <td>${StringUtil.requireNonNullElse(currentMakerValue.status, DASH)}</td>
                        <td>${StringUtil.requireNonNullElse(newMakerValue.status, DASH)}</td>
                    </tr>
                </c:if>
                <c:if test="${not empty currentExpertises and not empty newExpertises}">
                    <tr>
                        <th scope="row">Expertises:</th>
                        <td>${StringUtil.requireNonNullElse(currentExpertises, DASH)}</td>
                        <td>${StringUtil.requireNonNullElse(newExpertises, DASH)}</td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
        <c:if test="${not empty isOwnApproval and isOwnApproval eq false and staffApproval.status eq 'PENDING'}">
            <div class="mx-auto mt-4 d-flex justify-content-center">
                <form id="staffApprovalForm" method="POST">
                    <input type="hidden" name="approvalAction" id="approvalAction" value="asdf">
                    <input type="hidden" name="mcId" id="mcId" value="${staffApproval.makerCheckerId}">
                    <button type="submit" class="btn btn-outline-danger" onclick="handleButtonClick('reject')">
                        Reject
                    </button>
                    <button type="submit" class="btn btn-outline-primary" onclick="handleButtonClick('approve')">
                        Approve
                    </button>
                </form>
            </div>
        </c:if>
    </c:if>
</main>
<%@ include file="/shared/component/footer.jsp" %>
</body>
</html>
