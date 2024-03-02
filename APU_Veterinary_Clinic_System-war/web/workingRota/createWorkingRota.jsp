<%-- 
    Document   : profile
    Created on : Feb 18, 2024, 10:41:18 AM
    Author     : Jackson Tai
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="/shared/component/head_source.jsp" %>
    <title>Working Rota</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<main class="my-2 mx-auto overflow-x-auto" style="width: 80%">
    <h1 class="text-center">Create Working Rota</h1>
    <form action="<c:url value='<%= EndpointConstant.CREATE_WORKING_ROTA %>'/>" method="post">
        <label for="weekDate" class="form-label">Select Week:</label>
        <select id="weekDate" name="weekDate" class="form-select" style="max-width: 12rem">
            <c:forEach var="date" items="${weekDates}">
                <option value="${date}">${date}</option>
            </c:forEach>
        </select>
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
                    <th scope="col" class="text-center">Monday</th>
                    <th scope="col" class="text-center">Tuesday</th>
                    <th scope="col" class="text-center">Wednesday</th>
                    <th scope="col" class="text-center">Thursday</th>
                    <th scope="col" class="text-center">Friday</th>
                    <th scope="col" class="text-center">Saturday</th>
                    <th scope="col" class="text-center">Sunday</th>
                </tr>
                </thead>
                <c:forEach items="${vets}" var="vet" varStatus="rowNum">
                    <tr>
                        <td class="text-center">${rowNum.index + 1}</td>
                        <td>
                            <a href="<c:url value='<%= EndpointConstant.VIEW_STAFF %>'/>?id=${vet.clinicUserId}"
                               target="_blank" role="button" class="d-block mx-auto text-center">${vet.fullName}</a>
                        </td>
                        <c:forEach var="day" begin="1" end="7">
                            <td class="text-center">
                                <input type="checkbox" class="largeCheckbox" name="${vet.clinicUserId}_${day}"
                                       id="${vet.clinicUserId}_${day}">
                            </td>
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
</main>
<%@ include file="/shared/component/footer.jsp" %>
</body>
</html>
