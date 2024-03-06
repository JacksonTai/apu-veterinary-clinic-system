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
    <script src="${pageContext.request.contextPath}/asset/js/customer/searchCustomer.js" defer></script>
    <title>Appointment</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<main class="w-75 my-2 mx-auto overflow-x-auto">
    <h1 class="text-center">Create Appointment</h1>
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item">
                <a href="<c:url value='<%= EndpointConstant.VIEW_APPOINTMENT %>'/>">Appointment</a>
            </li>
            <li class="breadcrumb-item active" aria-current="page">
                <a href="<c:url value='<%= EndpointConstant.CREATE_APPOINTMENT %>'/>?step=appointmentDetails">
                    Create (Appointment Details)
                </a>
            </li>
            <c:if test="${param.step eq 'assignVet'}">
                <li class="breadcrumb-item active" aria-current="page">
                    <a href="<c:url value='<%= EndpointConstant.CREATE_APPOINTMENT %>'/>?step=assignVet">Assign Vet</a>
                </li>
            </c:if>
        </ol>
    </nav>
    <c:if test="${param.step eq 'appointmentDetails' or empty param.step}">
        <div class="mx-auto" style="max-width: 30rem;">
            <c:set var="appointmentDetails" value="${sessionScope.appointmentDetails}"/>
            <c:set var="customerDetails" value="${appointmentDetails.customerDetails}"/>
            <c:set var="customer" value="${appointmentDetails.customer}"/>
            <c:set var="availablePets" value="${appointmentDetails.availablePets}"/>
            <c:set var="selectedPetId" value="${appointmentDetails.selectedPetId}"/>
            <c:set var="selectedWeek" value="${appointmentDetails.selectedWeek}"/>
            <c:set var="sessionSelectedExpertises" value="${appointmentDetails.selectedExpertises}"/>

            <h2 class="fs-4 mb-3 align-self-start">Appointment Details</h2>
            <div class="alert alert-primary mb-3" role="alert">
                Please confirm the customer details to view the available pets.
            </div>
            <form id="searchCustomerForm" class="my-2 d-flex flex-column align-items-center justify-content-center"
                  method="POST">
                <div class="mb-3 w-100">
                    <label for="customerDetailsAjax" class="form-label">Customer Details:</label>
                    <div class="d-flex justify-content-between mb-1">
                        <input type="text" class="form-control" id="customerDetailsAjax" name="customerDetailsAjax"
                               placeholder="Full name / ID / Phone number / Email address"
                               value="${not empty customerDetails ? customerDetails : ''}">
                        <button type="submit" class="btn btn-primary ms-2">Confirm</button>
                    </div>
                    <span class="d-block mb-1" style="color: red;" id="searchError">
                            ${not empty customerDetailsError ? customerDetailsError : null}
                    </span>
                    <c:set var="customerUrlId" value="?id=${customer.customerId}"/>
                    <a href="<c:url value='<%= EndpointConstant.VIEW_CUSTOMER %>'/>${not empty customer ? customerUrlId : ''}"
                       class="${empty customer ? 'd-none ': ''} btn btn-outline-primary btn-sm my-1" id="searchResult"
                       target="_blank">
                            ${customer.fullName}
                    </a>
                    <span class="fst-italic d-block">
                  <span class="text-body-secondary">
                       <a href="<c:url value='<%= EndpointConstant.CREATE_CUSTOMER %>'/>" target="_blank" role="button">
                           Click here
                       </a>
                      to add a new customer profile if it is not in the system.
                  </span>
                </span>
                </div>
            </form>
            <form action="<c:url value='<%= EndpointConstant.CREATE_APPOINTMENT %>'/>" method="POST"
                  class="my-2 mx-auto d-flex flex-column align-items-center justify-content-center">
                <input type="hidden" name="customerDetails" id="customerDetails"
                       value="${not empty customerDetails ? customerDetails : ''}">
                <input type="hidden" name="customerId" id="customerId"
                       value="${not empty customer ? customer.customerId : ''}">
                <div class="mb-3 w-100">
                    <div class="mb-1">
                        <label for="petId" class="form-label">Select Pet:</label>
                        <select id="petId" name="petId" class="form-select w-100">
                            <c:forEach var="availablePet" items="${availablePets}">
                                <option ${selectedPetId != null and selectedPetId eq availablePet.petId ? 'selected' : ''}
                                        value="${availablePet.petId}">
                                        ${availablePet.name}
                                </option>
                            </c:forEach>
                            <option id="disabledPetOption" disabled ${selectedPetId == null ? 'selected': ''}>
                                Please select a pet
                            </option>
                        </select>
                        <span class="d-block mb-1" style="color: red;">
                                ${not empty petSelectError ? petSelectError : null}
                        </span>
                    </div>
                    <span class="fst-italic">
                          <span class="text-body-secondary">
                               <a href="<c:url value='<%= EndpointConstant.CREATE_PET %>'/>" target="_blank"
                                  role="button">Click here</a>
                               to add a new pet profile if the pet is not in the list.
                          </span>
                    </span>
                </div>
                <div class="mb-3 w-100">
                    <label for="week" class="form-label">Select Week:</label>
                    <select id="week" name="week" class="form-select w-100">
                        <c:forEach var="week" items="${weeks}">
                            <option value="${week}"
                                ${not empty selectedWeek and selectedWeek eq week ? 'selected' :
                                        not empty param.week and param.week eq week ? 'selected' : ''}>
                                    ${StringUtil.convertDateFormat(week, DMY_SLASH_DATE_FORMAT)}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="mb-3 w-100">
                    <label class="form-label">Expertises needed (Optional):</label>
                    <div class="btn-group d-flex flex-wrap" role="group"
                         aria-label="Basic checkbox toggle button group">
                        <c:forEach items="${expertises}" var="expertise">
                            <c:set var="isChecked" value="false"/>
                            <c:forEach items="${not empty sessionSelectedExpertises and
                                sessionSelectedExpertises.size() > 0 ? sessionSelectedExpertises : selectedExpertises}"
                                       var="vetExpertise">
                                <c:if test="${vetExpertise.expertiseId eq expertise.expertiseId}">
                                    <c:set var="isChecked" value="true"/>
                                </c:if>
                            </c:forEach>
                            <input type="checkbox" class="btn-check" autocomplete="off"
                                   name="expertise_${expertise.expertiseId}" id="expertise_${expertise.expertiseId}"
                                   <c:if test="${isChecked}">checked</c:if>>
                            <label class="btn btn-outline-primary m-1 rounded-0"
                                   for="expertise_${expertise.expertiseId}">${expertise.name}</label>
                        </c:forEach>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">Find Vets</button>
            </form>
        </div>
    </c:if>
    <c:if test="${param.step eq 'assignVet'}">
        <c:set var="dateToVetsMap" value="${sessionScope.assignVet.dateToVetsMap}"/>
        <c:set var="noAvailableVets" value="${sessionScope.assignVet.noAvailableVets}"/>
        <c:set var="selectedExpertises" value="${sessionScope.assignVet.selectedExpertises}"/>

        <c:if test="${noAvailableVets}">
            <div class="mt-5 d-flex flex-column align-items-center mw-100">
                <img src="${pageContext.request.contextPath}/asset/gif/notFound.gif" alt="Not Found GIF"
                     class="d-block">
                <p class="fs-4 text-center">
                    No vets are available with the selected expertises on the selected week.
                </p>
            </div>
        </c:if>
        <c:if test="${noAvailableVets eq false}">
            <h2 class="fs-4 align-self-start mb-3 mt-2">Assign Vet</h2>
            <span class="d-block mb-1" style="color: red;">
                    ${sessionScope.assignVetError}
            </span>
            <form action="<c:url value='<%= EndpointConstant.CREATE_APPOINTMENT %>'/>" method="POST"
                  class="mb-3 mx-auto">
                <div class="d-flex justify-content-center flex-wrap">
                    <c:forEach var="entry" items="${dateToVetsMap}">
                        <c:if test="${not empty entry.value}">
                            <ul class="list-group m-3" style="width: 20rem">
                                <li class="list-group-item active" aria-current="true">
                                        ${StringUtil.convertDateFormat(entry.key, DMY_SLASH_DATE_FORMAT)}
                                </li>
                                <c:forEach var="vet" items="${entry.value}">
                                    <li class="list-group-item">
                                        <div class="mb-2">
                                            <input class="form-check-input me-1" type="radio" name="selectedVet"
                                                   value="${vet.clinicUserId}_${entry.key}"
                                                   id="${vet.clinicUserId}_${entry.key}">
                                            <label class="form-check-label" for="${vet.clinicUserId}_${entry.key}">
                                                <a href="<c:url value='<%= EndpointConstant.VIEW_STAFF %>'/>?id=${vet.clinicUserId}"
                                                   target="_blank">
                                                        ${vet.fullName}
                                                </a>
                                            </label>
                                        </div>
                                        <c:forEach var="expertise" items="${vet.expertises}">
                                            <div class="badge text-wrap mb-1 lh-base w-100
                                            text-bg-${selectedExpertises.contains(expertise) ? 'secondary' : 'light'}">
                                                    ${expertise.name}
                                            </div>
                                        </c:forEach>
                                    </li>
                                </c:forEach>
                            </ul>
                        </c:if>
                    </c:forEach>
                </div>
                <div class="w-100 d-flex justify-content-center">
                    <button type="submit" class="btn btn-primary mt-2">Create Appointment</button>
                </div>
            </form>
        </c:if>
    </c:if>
</main>
<%@ include file="/shared/component/footer.jsp" %>
</body>
</html>
