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
    <script src="${pageContext.request.contextPath}/asset/js/constant/endpointConstant.js" defer></script>
    <title>Staff Approval</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<main class="w-75 my-2 mx-auto overflow-x-auto">
    <h1 class="text-center">Staff Approval</h1>
    <c:set var="type" value="${param.type == null ? 'vet' : param.type}"/>
    <ul class="nav nav-tabs">
        <li class="nav-item">
            <a class="nav-link ${type eq 'pending' ? 'active' : ''}" aria-current="page"
               href="<c:url value='${EndpointConstant.VIEW_STAFF_APPROVAL}'/>?type=pending">
                Pending
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${type eq 'approved' ? 'active' : ''}"
               href="<c:url value='${EndpointConstant.VIEW_STAFF_APPROVAL}'/>?type=approved">
                Approved
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${type eq 'rejected' ? 'active' : ''}"
               href="<c:url value='${EndpointConstant.VIEW_STAFF_APPROVAL}'/>?type=rejected">
                Rejected
            </a>
        </li>
    </ul>
    <c:choose>
        <c:when test="${empty staffApprovals}">
            <div class="d-flex flex-column align-items-center justify-content-center">
                <img src="${pageContext.request.contextPath}/asset/img/noRecord.png" alt="No Record Illustration"
                     width="35%">
                <h2 class="text-center">There are no staff approval records currently</h2>
            </div>
        </c:when>
        <c:otherwise>
            <table class="table table-bordered w-100 h-100 mb-2">
                <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Maker ID</th>
                    <th scope="col">Checker ID</th>
                    <th scope="col">Module</th>
                    <th scope="col">Type</th>
                    <th scope="col">Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${staffApprovals}" var="staffApproval">
                    <tr>
                        <td>${StringUtil.requireNonNullElse(staffApproval.makerCheckerId, DASH)}</td>
                        <td>
                            <a href="<c:url value='<%= EndpointConstant.VIEW_STAFF %>'/>?id=${staffApproval.makerId}"
                               target="_blank" role="button">
                                    ${staffApproval.makerId}
                            </a>
                        </td>
                        <td>
                            <c:if test="${not empty staffApproval.checkerId}">
                                <a href="<c:url value='<%= EndpointConstant.VIEW_STAFF %>'/>?id=${staffApproval.checkerId}"
                                   target="_blank" role="button">
                                        ${staffApproval.checkerId}
                                </a>
                            </c:if>
                            <c:if test="${empty staffApproval.checkerId}">
                                ${DASH}
                            </c:if>
                        </td>
                        <td>${StringUtil.requireNonNullElse(StringUtil.toTitleCase(staffApproval.module), DASH)}</td>
                        <td>${StringUtil.requireNonNullElse(StringUtil.toTitleCase(staffApproval.actionType), DASH)}</td>
                        <td>
                            <a class="btn btn-light btn-sm"
                               href="<c:url value='<%= EndpointConstant.VIEW_STAFF_APPROVAL %>'/>?id=${staffApproval.makerCheckerId}"
                               role="button">
                                View
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <%@ include file="/shared/component/pagination.jsp" %>
        </c:otherwise>
    </c:choose>
</main>
<%@ include file="/shared/component/footer.jsp" %>
</body>
</html>
