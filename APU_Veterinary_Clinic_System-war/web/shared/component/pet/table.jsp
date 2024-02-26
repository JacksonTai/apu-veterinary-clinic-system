<jsp:useBean id="StringUtil" class="util.StringUtil"/>
<table class="table table-bordered w-100 my-2">
    <thead>
    <tr>
        <th scope="col">ID</th>
        <th scope="col">Species</th>
        <th scope="col">Breed</th>
        <th scope="col">Name</th>
        <th scope="col">Health Status</th>
        <th scope="col">Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${pets}" var="pet">
        <tr>
            <td>${StringUtil.requireNonNullElse(pet.petId, DASH)}</td>
            <td>${StringUtil.requireNonNullElse(pet.species, DASH)}</td>
            <td>${StringUtil.requireNonNullElse(pet.breed, DASH)}</td>
            <td>${StringUtil.requireNonNullElse(pet.name, DASH)}</td>
            <td>${StringUtil.requireNonNullElse(pet.healthStatus, DASH)}</td>
            <td>
                <a class="btn btn-light btn-sm"
                   href="<c:url value='<%= EndpointConstant.VIEW_PET %>'/>?id=${pet.petId}"
                   role="button">View</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>