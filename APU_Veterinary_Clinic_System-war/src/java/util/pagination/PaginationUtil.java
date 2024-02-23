package util.pagination;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * A utility class to handle pagination logic using an AbstractFacade, simplifying
 * the implementation of pagination across different entities of the application.
 * It dynamically fetches a subset of entities based on the current page and
 * the specified page size, and prepares necessary data for rendering pagination controls.
 */
public class PaginationUtil {

    /**
     * Applies pagination logic based on the provided {@link PaginationConfig} and
     * updates the request with necessary attributes for displaying a paginated list
     * of entities along with pagination controls.
     *
     * @param <T> The type of entity for which pagination is being applied.
     * @param request The {@link HttpServletRequest} object, used for reading and manipulating request data.
     * @param response The {@link HttpServletResponse} object, used for sending redirects in case of out-of-range page requests.
     * @param config The {@link PaginationConfig} object containing all necessary configuration for pagination.
     * @throws IOException If an I/O error occurs during redirection.
     */
    public static <T> void applyPagination(HttpServletRequest request, HttpServletResponse response,
                                           PaginationConfig<T> config) throws IOException {
        int defaultPage = 1;
        int pageSize = 10;

        String pageParam = request.getParameter("page");
        int pageNumber;

        try {
            pageNumber = pageParam != null ? Integer.parseInt(pageParam) : defaultPage;
        } catch (NumberFormatException e) {
            pageNumber = defaultPage;
        }

        int totalItems = config.getFacade().count();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        // Redirect to the last page if the requested page number exceeds the total number of pages.
        if (pageNumber > totalPages && totalPages > 0) {
            response.sendRedirect(request.getContextPath() + config.getViewPageEndpoint() + "?page=" + totalPages);
            return;
        }
        // Redirect to the first page if the requested page number is less than 1.
        if (pageNumber <= 0) {
            response.sendRedirect(request.getContextPath() + config.getViewPageEndpoint());
            return;
        }

        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = startIndex + pageSize - 1;

        // Fetch the list of entities for the current page.
        java.util.List<T> items = config.getFacade().findRange(new int[]{startIndex, endIndex});

        request.setAttribute(config.getEntityAttribute(), items);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", pageNumber);
        request.setAttribute("previousDisabled", pageNumber == 1);
        request.setAttribute("nextDisabled", pageNumber == totalPages);

        try {
            request.getRequestDispatcher(config.getViewJspPath() + ".jsp").forward(request, response);
        } catch (javax.servlet.ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
