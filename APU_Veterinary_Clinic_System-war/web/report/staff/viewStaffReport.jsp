<%-- 
    Document   : home
    Created on : Feb 6, 2024, 9:45:27 AM
    Author     : Jackson Tai
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/shared/component/head_source.jsp" %>
    <title>Staff Report</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<main class="w-75 my-2 mx-auto overflow-x-auto">
    <h1 class="text-center">Staff Report</h1>
    <div class="container text-center">
        <div class="row">
            <div class="col-12">
                <div class="shadow p-5 bg-white rounded d-flex justify-content-center align-items-center m-3">
                    <div class="text-center">
                        <h2 class="fs-4 fw-normal">Total Staff</h2>
                        <p class="fs-1 mb-0">${totalStaff}</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-4">
                <div class="shadow p-5 bg-white rounded d-flex justify-content-center align-items-center m-3">
                    <div class="text-center">
                        <h2 class="fs-4 fw-normal">Vet</h2>
                        <p class="fs-1 mb-0">${totalVet}</p>
                    </div>
                </div>
            </div>
            <div class="col-lg-4">
                <div class="shadow p-5 bg-white rounded d-flex justify-content-center align-items-center m-3">
                    <div class="text-center">
                        <h2 class="fs-4 fw-normal">Receptionist</h2>
                        <p class="fs-1 mb-0">${totalReceptionist}</p>
                    </div>
                </div>
            </div>
            <div class="col-lg-4">
                <div class="shadow p-5 bg-white rounded d-flex justify-content-center align-items-center m-3">
                    <div class="text-center">
                        <h2 class="fs-4 fw-normal">Managing Staff</h2>
                        <p class="fs-1 mb-0">${totalManagingStaff}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<%@ include file="/shared/component/footer.jsp" %>
</body>
</html>
