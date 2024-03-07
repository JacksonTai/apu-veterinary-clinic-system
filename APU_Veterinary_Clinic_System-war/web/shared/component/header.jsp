<%-- 
    Document   : header
    Created on : Feb 6, 2024, 9:29:02 AM
    Author     : Jackson Tai
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constant.EndpointConstant" %>
<%@ page import="constant.GlobalConstant" %>
<%@ page import="constant.UserRole" %>
<c:set var="VET" value="<%= UserRole.VET %>"/>
<c:set var="RECEPTIONIST" value="<%= UserRole.RECEPTIONIST %>"/>
<c:set var="MANAGING_STAFF" value="<%= UserRole.MANAGING_STAFF %>"/>
<c:set var="DMY_SLASH_DATE_FORMAT" value="<%= GlobalConstant.DMY_SLASH_DATE_FORMAT %>"/>
<c:set var="DASH" value="<%= GlobalConstant.DASH %>"/>

<header>
    <nav class="navbar navbar-expand-lg bg-body-tertiary mb-4">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">
                <img src="${pageContext.request.contextPath}/asset/img/logo.png" alt="Logo" width="30" height="24"
                     class="d-inline-block align-text-top">
                AVCS
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarScroll"
                    aria-controls="navbarScroll" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarScroll">
                <ul class="navbar-nav me-auto my-2 my-lg-0 navbar-nav-scroll" style="--bs-scroll-height: 100px;">
                    <c:if test="${not empty sessionScope.clinicUser}">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page"
                               href="<c:url value='<%= EndpointConstant.STAFF_HOME %>'/>">
                                Home
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page"
                               href="<c:url value='<%= EndpointConstant.VIEW_PROFILE %>'/>">
                                Profile
                            </a>
                        </li>
                        <c:if test="${sessionScope.clinicUser.userRole eq RECEPTIONIST}">
                            <li class="nav-item">
                                <a class="nav-link" aria-current="page"
                                   href="<c:url value='<%= EndpointConstant.VIEW_CUSTOMER %>'/>">
                                    Customer
                                </a>
                            </li>
                        </c:if>
                        <c:if test="${sessionScope.clinicUser.userRole eq RECEPTIONIST ||
                                    sessionScope.clinicUser.userRole eq VET}">
                            <li class="nav-item">
                                <a class="nav-link" aria-current="page"
                                   href="<c:url value='<%= EndpointConstant.VIEW_APPOINTMENT %>'/>">
                                    Appointment
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" aria-current="page"
                                   href="<c:url value='<%= EndpointConstant.VIEW_PET %>'/>">
                                    Pet
                                </a>
                            </li>
                        </c:if>
                        <c:if test="${sessionScope.clinicUser.userRole eq MANAGING_STAFF}">
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" role="button"
                                   data-bs-toggle="dropdown"
                                   aria-expanded="false">
                                    Staff
                                </a>
                                <ul class="dropdown-menu">
                                    <li>
                                        <a class="dropdown-item"
                                           href="<c:url value='<%= EndpointConstant.VIEW_STAFF %>'/>">
                                            Record
                                        </a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item"
                                           href="<c:url value='<%= EndpointConstant.VIEW_STAFF_APPROVAL %>'/>?type=pending">
                                            Approval
                                        </a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item"
                                           href="<c:url value='<%= EndpointConstant.VIEW_EXPERTISE %>'/>">
                                            Expertise
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" role="button"
                                   data-bs-toggle="dropdown"
                                   aria-expanded="false">
                                    Report
                                </a>
                                <ul class="dropdown-menu">
                                    <li>
                                        <a class="dropdown-item"
                                           href="<c:url value='<%= EndpointConstant.STAFF_REGISTER %>'/>">
                                            Staff
                                        </a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item"
                                           href="<c:url value='<%= EndpointConstant.VIEW_CUSTOMER_REPORT %>'/>">
                                            Customer
                                        </a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item"
                                           href="<c:url value='<%= EndpointConstant.STAFF_REGISTER %>'/>">
                                            Appointment
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" aria-current="page"
                                   href="<c:url value='<%= EndpointConstant.VIEW_WORKING_ROTA %>'/>">
                                    Working Rota
                                </a>
                            </li>
                        </c:if>
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page"
                               href="<c:url value='<%= EndpointConstant.LOGOUT %>'/>">
                                Log out
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${empty sessionScope.clinicUser}">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page"
                               href="${pageContext.request.contextPath}">
                                Home
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page"
                               href="<c:url value='<%= EndpointConstant.STAFF_LOGIN %>'/>">
                                Login
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page"
                               href="<c:url value='<%= EndpointConstant.STAFF_REGISTER %>'/>">
                                Register
                            </a>
                        </li>
                    </c:if>
                </ul>
            </div>
        </div>
    </nav>

</header>
