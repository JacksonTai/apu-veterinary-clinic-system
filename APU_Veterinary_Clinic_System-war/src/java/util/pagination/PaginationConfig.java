package util.pagination;

import lombok.Builder;
import lombok.Getter;
import repository.AbstractFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Configuration class for pagination, encapsulating all necessary settings and parameters.
 * This class uses the builder pattern for easy instantiation and setup.
 *
 * @param <T> The type of entity for which the pagination configuration is defined.
 */
@Getter
@Builder
public class PaginationConfig<T> {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final String entityAttribute;
    private final String viewPageEndpoint;
    private final String viewJspPath;
    private final AbstractFacade<T> facade;

    /**
     * Creates an instance of PaginationConfig with specified settings for pagination.
     *
     * @param request The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     * @param entityAttribute The request attribute name to store the fetched entities.
     * @param viewPageEndpoint The base view page URL for redirects.
     * @param viewJspPath The JSP path for forwarding after fetching entities.
     * @param facade The AbstractFacade used for fetching entities and counting.
     */
}