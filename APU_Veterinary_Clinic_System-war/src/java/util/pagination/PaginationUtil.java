package util.pagination;

import lombok.SneakyThrows;
import repository.AbstractFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

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
     * @param <T>    The type of entity for which pagination is being applied.
     * @param config The {@link PaginationConfig} object containing all necessary configuration for pagination.
     */
    @SneakyThrows
    public static <T> void applyPagination(PaginationConfig<T> config) {

        int defaultPage = 1;
        int pageSize = 10;

        HttpServletRequest request = config.getRequest();
        HttpServletResponse response = config.getResponse();

        String pageParam = config.getRequest().getParameter("page");
        int pageNumber;

        try {
            pageNumber = pageParam != null ? Integer.parseInt(pageParam) : defaultPage;
        } catch (NumberFormatException e) {
            pageNumber = defaultPage;
        }

        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = startIndex + pageSize - 1;

        String namedQuery = config.getNamedQuery();
        Map<String, String> queryParams = config.getQueryParams();

        // Fetch the list of entities for the current page.
        AbstractFacade<T> facade = config.getFacade();
        List<T> items = facade.findResultsByAttributes(new int[]{startIndex, endIndex}, namedQuery, queryParams);
        int totalItems = namedQuery != null && queryParams != null ?
                facade.count(namedQuery.replace("find", "count"), queryParams) :
                facade.count();

        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        // Exclude the page parameter from the existing parameters
        String existingParams = request.getQueryString();
        if (existingParams != null && !existingParams.isEmpty()) {
            existingParams = existingParams.replaceAll("&?page=\\d+", "");
        }

        // Redirect to the first page if the requested page number is less than 1.
        if (pageNumber <= 0) {
            response.sendRedirect(request.getContextPath() + config.getViewPageEndpoint());
            return;
        }

        // Redirect to the last page if the requested page number exceeds the total number of pages.
        if (pageNumber > totalPages && totalPages > 0) {

            // Check if there are existing parameters
            if (existingParams != null && !existingParams.isEmpty()) {
                response.sendRedirect(request.getContextPath() + config.getViewPageEndpoint() + "&page=" +
                        totalPages);
            }
            if (existingParams == null || existingParams.isEmpty()) {
                response.sendRedirect(request.getContextPath() + config.getViewPageEndpoint() + "?page=" +
                        totalPages);
            }
            return;
        }

        request.setAttribute(config.getEntityAttribute(), items);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", pageNumber);
        request.setAttribute("existingParams", existingParams);
        request.setAttribute("previousDisabled", pageNumber == 1);
        request.setAttribute("nextDisabled", pageNumber == totalPages);
        request.getRequestDispatcher(config.getViewJspPath() + ".jsp").forward(request, response);
    }
}
