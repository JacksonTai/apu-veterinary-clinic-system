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
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="<c:url value='<%= EndpointConstant.INDEX %>'/>">
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
                            <a class="dropdown-item" href="<c:url value='<%= EndpointConstant.CUSTOMER_LOGIN %>'/>">
                                Login
                            </a>
                        </li>
                        <li>
                            <a class="dropdown-item" href="<c:url value='<%= EndpointConstant.CUSTOMER_REGISTER %>'/>">
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
                            <a class="dropdown-item" href="<c:url value='<%= EndpointConstant.STAFF_LOGIN %>'/>">
                                Login
                            </a>
                        </li>
                        <li>
                            <a class="dropdown-item" href="<c:url value='<%= EndpointConstant.STAFF_REGISTER %>'/>">
                                Register
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>
