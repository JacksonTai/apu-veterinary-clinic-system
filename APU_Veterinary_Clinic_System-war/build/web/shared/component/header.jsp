<%-- 
    Document   : header
    Created on : Feb 6, 2024, 9:29:02 AM
    Author     : Jackson Tai
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constant.EndpointConstant" %>

<nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
        <a class="navbar-brand" href="<c:url value='<%= EndpointConstant.INDEX %>'/>">AVCS</a>
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
                    <c:if test="${sessionScope.clinicUser.userType eq 'Vet'}">
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page"
                               href="<c:url value='<%= EndpointConstant.VIEW_PET %>'/>">
                                Pet
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${sessionScope.clinicUser.userType eq 'Receptionist'}">
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page"
                               href="<c:url value='<%= EndpointConstant.VIEW_CUSTOMER %>'/>">
                                Customer
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${sessionScope.clinicUser.userType eq 'Receptionist' ||
                                    sessionScope.clinicUser.userType eq 'Vet'}">
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page"
                               href="<c:url value='<%= EndpointConstant.VIEW_APPOINTMENT %>'/>">
                                Appointment
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${sessionScope.clinicUser.userType eq 'ManagingStaff'}">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" role="button"
                               data-bs-toggle="dropdown"
                               aria-expanded="false">
                                Report
                            </a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a class="dropdown-item"
                                       href="<c:url value='<%= EndpointConstant.STAFF_LOGIN %>'/>">
                                        Customer
                                    </a>
                                </li>
                                <li>
                                    <a class="dropdown-item"
                                       href="<c:url value='<%= EndpointConstant.STAFF_REGISTER %>'/>">
                                        Staff
                                    </a>
                                </li>
                                <li>
                                    <a class="dropdown-item"
                                       href="<c:url value='<%= EndpointConstant.STAFF_REGISTER %>'/>">
                                        Appointment
                                    </a>
                                </li>
                                <li>
                                    <a class="dropdown-item"
                                       href="<c:url value='<%= EndpointConstant.STAFF_REGISTER %>'/>">
                                        Pet
                                    </a>
                                </li>
                            </ul>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page"
                               href="<c:url value='<%= EndpointConstant.VIEW_IAM %>'/>">
                                IAM
                            </a>
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
                           href="<c:url value='<%= EndpointConstant.INDEX %>'/>">
                            Home
                        </a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                           aria-expanded="false">
                            Customer
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <a class="dropdown-item"
                                   href="<c:url value='<%= EndpointConstant.CUSTOMER_LOGIN %>'/>">
                                    Login
                                </a>
                            </li>
                            <li>
                                <a class="dropdown-item"
                                   href="<c:url value='<%= EndpointConstant.CUSTOMER_REGISTER %>'/>">
                                    Register
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                           aria-expanded="false">
                            Staff
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <a class="dropdown-item"
                                   href="<c:url value='<%= EndpointConstant.STAFF_LOGIN %>'/>">
                                    Login
                                </a>
                            </li>
                            <li>
                                <a class="dropdown-item"
                                   href="<c:url value='<%= EndpointConstant.STAFF_REGISTER %>'/>">
                                    Register
                                </a>
                            </li>
                        </ul>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>
