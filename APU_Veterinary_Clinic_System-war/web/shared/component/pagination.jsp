<nav aria-label="Page navigation" class="mt-4">
    <ul class="pagination justify-content-center">
        <c:choose>
            <c:when test="${not empty existingParams}">
                <c:set var="otherParams" value="${existingParams}&" />
            </c:when>
        </c:choose>
        <c:set var="previousHref" value="?${otherParams}page=${currentPage - 1}" />
        <li class="page-item ${previousDisabled ? 'disabled' : ''}">
            <a class="page-link" href="${previousHref}"
               aria-label="Previous" ${previousDisabled ? 'tabindex="-1" aria-disabled="true"' : ''}>
                <span aria-hidden="true">&laquo;</span>
            </a>
        </li>
        <c:forEach begin="1" end="${totalPages}" var="page">
            <c:set var="currentHref" value="?${otherParams}page=${page}" />
            <li class="page-item ${page eq currentPage ? 'active' : ''}"
                aria-current="${page eq currentPage ? 'page' : null}">
                <a class="page-link" href="${currentHref}">${page}</a>
            </li>
        </c:forEach>
        <c:set var="nextHref" value="?${otherParams}page=${currentPage + 1}" />
        <li class="page-item ${nextDisabled ? 'disabled' : ''}">
            <a class="page-link" href="${nextHref}"
               aria-label="Next" ${nextDisabled ? 'tabindex="-1" aria-disabled="true"' : ''}>
                <span aria-hidden="true">&raquo;</span>
            </a>
        </li>
    </ul>
</nav>