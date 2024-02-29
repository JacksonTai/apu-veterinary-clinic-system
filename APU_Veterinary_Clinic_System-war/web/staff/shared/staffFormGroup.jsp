<div class="mb-3">
    <label for="fullName" class="form-label">Full Name:</label>
    <input type="text" class="form-control" id="fullName" name="fullName"
           value="${not empty param.fullName ? param.fullName : ''}">
    <c:if test="${not empty fullNameError}">
        <span style="color: red;">${fullNameError}</span>
    </c:if>
</div>
<div class="mb-3">
    <label for="email" class="form-label">Email:</label>
    <input type="text" class="form-control" id="email" name="email"
           value="${not empty param.email ? param.email : ''}">
    <c:if test="${not empty emailError}">
        <span style="color: red;">${emailError}</span>
    </c:if>
</div>
<div class="mb-3">
    <label for="password" class="form-label">Password:</label>
    <input type="password" class="form-control" id="password" name="password"
           value="${not empty param.password ? param.password : ''}">
    <c:if test="${not empty passwordError}">
        <span style="color: red;">${passwordError}</span>
    </c:if>
</div>
<div class="mb-3">
    <label for="userRole" class="form-label">User Role:</label>
    <select name="userRole" id="userRole" class="form-select">
        <option value=${RECEPTIONIST} ${param.userRole eq RECEPTIONIST ? 'selected' : ''}>Receptionist
        </option>
        <option value="${VET}" ${param.userRole eq VET ? 'selected' : ''}>Vet</option>
    </select>
</div>