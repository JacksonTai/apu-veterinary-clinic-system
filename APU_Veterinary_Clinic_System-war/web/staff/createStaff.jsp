<%--
    Document   : login
    Created on : Feb 6, 2024, 9:28:44 AM
    Author     : Jackson Tai
--%>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="/shared/component/head_source.jsp" %>
    <title>Create Staff</title>
</head>
<body>
<%@ include file="/shared/component/header.jsp" %>
<main class="w-75 my-2 mx-auto overflow-x-auto">
    <h1 class="text-center">Create Staff</h1>
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item">
                <a href="<c:url value='<%= EndpointConstant.VIEW_STAFF %>'/>">Staff</a>
            </li>
            <li class="breadcrumb-item active" aria-current="page">Create</li>
        </ol>
    </nav>
    <form action="<c:url value='<%= EndpointConstant.CREATE_STAFF %>'/>" method="POST" class="my-2 mx-auto"
          style="max-width: 30rem;">
        <%@ include file="shared/staffFormGroup.jsp" %>
        <div class="mx-auto d-flex justify-content-center">
            <a class="btn btn-light d-block me-2"
               href="<c:url value='<%= EndpointConstant.VIEW_STAFF %>'/>" role="button">Cancel</a>
            <button type="submit" class="btn btn-primary">Confirm</button>
        </div>
    </form>
</main>
<%@ include file="/shared/component/footer.jsp" %>
</body>
</html>
