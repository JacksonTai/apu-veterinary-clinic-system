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
    <title>Customer Report</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<main class="w-75 my-2 mx-auto overflow-x-auto">
    <h1 class="text-center">Customer Report</h1>
    <div class="d-flex justify-content-center align-items-center flex-column flex-lg-row">
        <div class="d-flex flex-column">
            <div class="shadow p-5 bg-white rounded d-flex justify-content-center align-items-center m-3">
                <div class="text-center">
                    <h2 class="fs-4 fw-normal">Total Customer</h2>
                    <p class="fs-1 mb-0">${totalCustomers}</p>
                </div>
            </div>
            <div class="shadow p-5 bg-white rounded d-flex justify-content-center align-items-center m-3">
                <div class="text-center">
                    <h2 class="fs-4 fw-normal mb-0">Gender Ratio</h2>
                    <p class="fw-light fs-5 mb-0">
                        <span class="text-primary">Male</span> to <span class="text-danger">Female</span>
                    </p>
                    <div class="d-flex justify-content-between align-items-center">
                        <div class="text-center d-flex justify-content-center align-items-center flex-column me-3">
                            <p class="fs-4 mb-0 text-primary">${maleRatio}%</p>
                            <p class="fw-light fs-5 mb-0 text-primary">${totalMaleCustomer}&nbsp;</p>
                        </div>
                        <div class="text-center d-flex justify-content-center align-items-center flex-column">
                            <p class="fs-4 mb-0 text-danger">${femaleRatio}%</p>
                            <p class="fw-light fs-5 mb-0 text-danger">${totalFemaleCustomer}&nbsp;</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="shadow p-4 bg-white rounded m-3 h-100" style="min-width: 25rem">
            <h2 class="fs-4 fw-normal">
                <u>Average</u>
            </h2>
            <div class="mb-5 text-center">
                <h3 class="fs-5 fw-normal">Customer Age</h3>
                <p class="fw-light">The average customers' age based on date of birth.</p>
                <p class="text-center fs-1 mb-5">${averageAge}</p>
            </div>
            <div class="mb-5 text-center">
                <h3 class="fs-5 fw-normal">Customer's Pet Count</h3>
                <p class="fw-light">The average number of pet owned by customers.</p>
                <p class="text-center fs-1 mb-5">${averagePetOwned}</p>
            </div>
        </div>
    </div>
</main>
<%@ include file="/shared/component/footer.jsp" %>
</body>
</html>
