<nav aria-label="pagination navigation" class="my-2 mx-auto">
    <ul class="pagination justify-content-center">
        <li class="page-item ${previousDisabled ? 'disabled' : ''}">
            <a class="page-link"
               href="?page=${currentPage - 1}" ${previousDisabled ? 'tabindex="-1" aria-disabled="true"' : ''}>Previous</a>
        </li>
        <c:forEach begin="1" end="${totalPages}" var="page">
            <li class="page-item ${page eq currentPage ? 'active' : ''}"
                aria-current="${page eq currentPage ? 'page' : null}">
                <a class="page-link" href="?page=${page}">${page}</a>
            </li>
        </c:forEach>
        <li class="page-item ${nextDisabled ? 'disabled' : ''}">
            <a class="page-link"
               href="?page=${currentPage + 1}" ${nextDisabled ? 'tabindex="-1" aria-disabled="true"' : ''}>Next</a>
        </li>
    </ul>
</nav>