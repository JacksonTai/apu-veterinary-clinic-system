<%@ page import="java.time.LocalDate" %>
<footer class="w-100 mt-5 d-flex flex-column align-items-center justify-content-center bg-light"
        style="height: 10rem">
    <img src="${pageContext.request.contextPath}/asset/img/logo.png" alt="Logo" width="30" height="24"
         class="d-inline-block align-text-top">
    <p class="font-weight-bold mt-2">@ <%= LocalDate.now().getYear() %> APU Veterinary Clinic System</p>
</footer>