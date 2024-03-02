<%-- 
    Document   : profile
    Created on : Feb 18, 2024, 10:41:18 AM
    Author     : Jackson Tai
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<jsp:useBean id="StringUtil" class="util.StringUtil"/>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="/shared/component/head_source.jsp" %>
    <title>Create Working Rota</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<main class="my-2 mx-auto overflow-x-auto" style="width: 80%">
    <h1 class="text-center">Create Working Rota</h1>
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item">
                <a href="<c:url value='<%= EndpointConstant.VIEW_WORKING_ROTA %>'/>">Working Rota</a>
            </li>
            <li class="breadcrumb-item active" aria-current="page">Create</li>
        </ol>
    </nav>
    <c:if test="${not empty maxWorkingRota and maxWorkingRota eq true}">
        <div class="alert alert-warning" role="alert">
            There is no week available to create working rota. You can only create working rota for the next 4 weeks.
        </div>
    </c:if>
    <c:if test="${not empty maxWorkingRota and maxWorkingRota eq false}">
        <form action="<c:url value='<%= EndpointConstant.CREATE_WORKING_ROTA %>'/>" method="GET" class="d-flex">
            <div class="me-2">
                <label for="week" class="form-label">Select Week:</label>
                <select id="week" name="week" class="form-select" style="max-width: 12rem">
                    <c:forEach var="week" items="${weeks}">
                        <option value="${week}" ${param.week != null and param.week eq week ? 'selected' : ''}>
                                ${StringUtil.convertDateFormat(week, DMY_SLASH_DATE_FORMAT)}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <button type="submit" class="btn btn-primary align-self-end">Apply</button>
        </form>
        <form action="<c:url value='<%= EndpointConstant.CREATE_WORKING_ROTA %>'/>" method="POST">
            <div class="w-100 mx-auto overflow-x-auto">
                <table class="table w-100 my-4 mx-auto">
                    <colgroup>
                        <col span="1" style="width: 3rem;">
                        <col span="1" style="width: 3rem;">
                        <col span="1" style="width: 6rem;">
                        <col span="1" style="width: 6rem;">
                        <col span="1" style="width: 6rem;">
                        <col span="1" style="width: 6rem;">
                        <col span="1" style="width: 6rem;">
                        <col span="1" style="width: 6rem;">
                        <col span="1" style="width: 6rem;">
                    </colgroup>
                    <thead>
                    <tr>
                        <th scope="col" class="text-center">No</th>
                        <th scope="col" class="text-center">Vet</th>
                        <c:forEach var="day" begin="0" end="6">
                            <th scope="col" class="text-center">
                                    ${weekDays[day]}
                                <br>
                                <span class="fw-light">
                                        ${StringUtil.convertDateFormat(weekDates[day], DMY_SLASH_DATE_FORMAT)}
                                </span>
                            </th>
                        </c:forEach>
                    </tr>
                    </thead>
                    <c:forEach items="${vets}" var="vet" varStatus="rowNum">
                        <tr>
                            <td class="text-center">${rowNum.index + 1}</td>
                            <td>
                                <a href="<c:url value='<%= EndpointConstant.VIEW_STAFF %>'/>?id=${vet.clinicUserId}"
                                   target="_blank" role="button" class="d-block mx-auto text-center">${vet.fullName}</a>
                            </td>
                            <c:forEach items="${weekDates}" var="weekDate">
                                <td class="text-center">
                                    <input type="checkbox" class="largeCheckbox" name="${vet.clinicUserId}_${weekDate}"
                                           value="${vet.clinicUserId}_${weekDate}"></td>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <div class="mx-auto my-4 d-flex justify-content-center">
                <a class="btn btn-light d-block me-2" href="<c:url value='<%= EndpointConstant.VIEW_WORKING_ROTA %>'/>"
                   role="button">Cancel</a>
                <button type="submit" class="btn btn-primary">Confirm</button>
            </div>
        </form>
    </c:if>

</main>
<%@ include file="/shared/component/footer.jsp" %>
</body>
</html>
