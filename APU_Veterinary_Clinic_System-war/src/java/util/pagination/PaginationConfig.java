package util.pagination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import repository.AbstractFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Configuration class for pagination, encapsulating all necessary settings and parameters.
 * This class uses the builder pattern for easy instantiation and setup.
 *
 * @param <T> The type of entity for which the pagination configuration is defined.
 */
@Getter
public class PaginationConfig<T> {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final String entityAttribute;
    private final String viewPageEndpoint;
    private final String viewJspPath;
    private final AbstractFacade<T> facade;
    private final String namedQuery;
    private final Map<String, String> queryParams;

    /**
     * Creates an instance of PaginationConfig with specified settings for pagination.
     *
     * @param request          The HttpServletRequest object.
     * @param response         The HttpServletResponse object.
     * @param entityAttribute  The request attribute name to store the fetched entities.
     * @param viewPageEndpoint The base view page URL for redirects.
     * @param viewJspPath      The JSP path for forwarding after fetching entities.
     * @param facade           The AbstractFacade used for fetching entities and counting.
     * @param namedQuery       The named query to be used for fetching entities.
     * @param queryParams      The query parameters for the named query.
     */
    private PaginationConfig(HttpServletRequest request, HttpServletResponse response, String entityAttribute,
                             String viewPageEndpoint, String viewJspPath, AbstractFacade<T> facade, String namedQuery,
                             Map<String, String> queryParams) {
        this.request = request;
        this.response = response;
        this.entityAttribute = entityAttribute;
        this.viewPageEndpoint = viewPageEndpoint;
        this.viewJspPath = viewJspPath;
        this.facade = facade;
        this.namedQuery = namedQuery;
        this.queryParams = queryParams;
    }

    public static class Builder<T> {
        private HttpServletRequest request;
        private HttpServletResponse response;
        private String entityAttribute;
        private String viewPageEndpoint;
        private String viewJspPath;
        private AbstractFacade<T> facade;
        private String namedQuery;
        private Map<String, String> queryParams;

        public Builder<T> request(HttpServletRequest request) {
            this.request = request;
            return this;
        }

        public Builder<T> response(HttpServletResponse response) {
            this.response = response;
            return this;
        }

        public Builder<T> entityAttribute(String entityAttribute) {
            this.entityAttribute = entityAttribute;
            return this;
        }

        public Builder<T> viewPageEndpoint(String viewPageEndpoint) {
            this.viewPageEndpoint = viewPageEndpoint;
            return this;
        }

        public Builder<T> viewJspPath(String viewJspPath) {
            this.viewJspPath = viewJspPath;
            return this;
        }

        public Builder<T> facade(AbstractFacade<T> facade) {
            this.facade = facade;
            return this;
        }

        public Builder<T> namedQuery(String namedQuery) {
            this.namedQuery = namedQuery;
            return this;
        }

        public Builder<T> queryParams(Map<String, String> queryParams) {
            this.queryParams = queryParams;
            return this;
        }

        public PaginationConfig<T> build() {
            return new PaginationConfig<>(request, response, entityAttribute, viewPageEndpoint, viewJspPath, facade,
                    namedQuery, queryParams);
        }
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

}
