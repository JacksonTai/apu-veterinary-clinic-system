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
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
            var tooltipList = tooltipTriggerList.map(function (element) {
                return new bootstrap.Tooltip(element);
            });
        });
    </script>
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
    <div class="alert alert-primary alert-dismissible fade show mt-3" role="alert">
        <strong>Info:</strong>
        <br>
        - You can only create working rota for the next 4 weeks.
        <br>
        - At least 5 expertises are required for each day.
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    <c:if test="${not empty maxWorkingRota and maxWorkingRota eq true}">
        <div class="alert alert-warning mt-4" role="alert">
            There is no week available to create working rota. You can only create working rota for the next 4 weeks.
        </div>
    </c:if>
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
            <table class="table w-100 my-3 mx-auto">
                <colgroup>
                    <col span="1" style="width: 3rem;">
                    <col span="1" style="width: 7rem;">
                    <col span="1" style="width: 7rem;">
                    <col span="1" style="width: 7rem;">
                    <col span="1" style="width: 7rem;">
                    <col span="1" style="width: 7rem;">
                    <col span="1" style="width: 7rem;">
                    <col span="1" style="width: 7rem;">
                </colgroup>
                <thead>
                <tr>
                    <th scope="col" class="text-center">Vet</th>
                    <c:forEach var="day" begin="0" end="6">
                        <th scope="col" class="text-center">
                            <div>
                                    ${weekDays[day]}
                                <br>
                                <span class="fw-light">
                                        ${StringUtil.convertDateFormat(weekDates[day], DMY_SLASH_DATE_FORMAT)}
                                </span>
                            </div>
                        </th>
                    </c:forEach>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${vets}" var="vet" varStatus="rowNum">
                    <tr>
                        <td>
                            <a href="<c:url value='<%= EndpointConstant.VIEW_STAFF %>'/>?id=${vet.clinicUserId}"
                               target="_blank" role="button" class="d-block mx-auto text-center">${vet.fullName}</a>
                        </td>
                        <c:forEach items="${weekDates}" var="weekDate">
                            <td class="text-center">
                                <input ${vet.workingDays.contains(weekDate.toString()) ? 'checked' : ''}
                                        type="checkbox" class="largeCheckbox"
                                        name="${vet.clinicUserId}_${weekDate}"
                                        value="${vet.clinicUserId}_${weekDate}">
                            </td>
                        </c:forEach>
                    </tr>
                </c:forEach>
                <tr>
                    <th class="text-center">Expertises Covered:</th>
                    <c:forEach items="${weekDates}" var="weekDate">
                        <td class="text-center">

                            <c:set var="expertises" value="${dateToExpertisesMap[weekDate]}"/>
                            <c:if test="${not empty expertises}">
                                <c:if test="${expertises.size() < 5}">
                                    <span class="badge text-bg-danger mb-2">Expertises Shortage</span>
                                </c:if>
                                <c:if test="${expertises.size() >= 5}">
                                    <span class="badge text-bg-success mb-2">Sufficient Expertises</span>
                                </c:if>
                                <c:forEach items="${expertises}" var="expertise">
                                    <div class="badge text-bg-light text-wrap mb-1 lh-base w-100">${expertise}</div>
                                </c:forEach>
                            </c:if>
                            <c:if test="${empty expertises}">
                                -
                            </c:if>
                        </td>
                    </c:forEach>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="mx-auto my-4 d-flex justify-content-center">
            <a class="btn btn-light d-block me-2" href="<c:url value='<%= EndpointConstant.VIEW_WORKING_ROTA %>'/>"
               role="button">Cancel</a>
            <button type="submit" class="btn btn-primary">Confirm</button>
        </div>
    </form>

</main>
<%@ include file="/shared/component/footer.jsp" %>
</body>
</html>
