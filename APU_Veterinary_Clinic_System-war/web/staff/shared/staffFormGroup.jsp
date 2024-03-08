<c:if test="${not empty formType and formType eq 'UPDATE'}">
    <input type="hidden" name="formSubmitted" value="true">
    <input type="hidden" name="id" value="${staff.clinicUserId ? staff.clinicUserId : param.id}">
</c:if>
<div class="mb-3">
    <label for="fullName" class="form-label">Full Name:</label>
    <input type="text" class="form-control" id="fullName" name="fullName"
           value="${formType eq 'UPDATE' ? (param.formSubmitted != null ? param.fullName : staff.fullName) :
                                            (not empty param.fullName ? param.fullName : '')}">
    <c:if test="${not empty fullNameError}">
        <span style="color: red;">${fullNameError}</span>
    </c:if>
</div>
<div class="mb-3">
    <label for="email" class="form-label">Email:</label>
    <input type="text" class="form-control" id="email" name="email" placeholder="e.g. xxxx@gmail.com"
           value="${formType eq 'UPDATE' ? (param.formSubmitted != null ? param.email : staff.email) :
                                            (not empty param.email ? param.email : '')}">
    <c:if test="${not empty emailError}">
        <span style="color: red;">${emailError}</span>
    </c:if>
</div>
<c:if test="${formType eq 'CREATE'}">
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
            <c:forEach var="role" items="${roleList}">
                <option ${formType eq 'UPDATE' ? ((param.formSubmitted != null ? (param.userRole == role ? 'selected' : '') :
                        (staff.userRole == role ? 'selected' : ''))) :
                        (param.userRole == role ? 'selected' : '')}
                        value="${role}">${role}</option>
            </c:forEach>
        </select>
    </div>
</c:if>
