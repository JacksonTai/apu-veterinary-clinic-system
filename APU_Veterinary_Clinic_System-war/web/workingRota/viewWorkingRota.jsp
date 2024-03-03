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
    <title>Working Rota</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<main class="my-2 mx-auto overflow-x-auto" style="width: 80%">
    <h1 class="text-center">Working Rota</h1>
    <div class="d-flex justify-content-between flex-wrap mb-3">
        <form action="<c:url value='<%= EndpointConstant.VIEW_WORKING_ROTA %>'/>" method="GET" class="d-flex">
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
            <button type="submit" class="btn btn-primary align-self-end">View</button>
        </form>
        <a class="btn btn-primary align-self-end" href="<c:url value='<%= EndpointConstant.CREATE_WORKING_ROTA %>'/>"
           role="button">
            Create Working Rota
        </a>
    </div>
    <div class="w-100 mx-auto overflow-x-auto">
        <table class="table w-100 my-4 mx-auto">
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
                            ${weekDays[day]}
                        <br>
                        <span class="fw-light">
                                ${StringUtil.convertDateFormat(weekDates[day], DMY_SLASH_DATE_FORMAT)}
                        </span>
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
                        <td class="text-center pe-none">
                            <input type="checkbox" class="largeCheckbox"
                                ${vet.workingDays.contains(weekDate.toString()) ? 'checked' : ''}>
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
</main>
<%@ include file="/shared/component/footer.jsp" %>
</body>
</html>
