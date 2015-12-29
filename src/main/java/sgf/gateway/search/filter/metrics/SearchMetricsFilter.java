package sgf.gateway.search.filter.metrics;

import org.springframework.util.StringUtils;
import sgf.gateway.model.security.User;
import sgf.gateway.search.api.Criteria;
import sgf.gateway.search.api.Facet;
import sgf.gateway.search.api.SearchQuery;
import sgf.gateway.search.api.SearchResult;
import sgf.gateway.search.filter.SearchFilterChain;
import sgf.gateway.service.metrics.UserMetricsService;
import sgf.gateway.service.security.RuntimeUserService;

public class SearchMetricsFilter extends SearchFilterChain {

    private final UserMetricsService userMetricsService;
    private final RuntimeUserService runtimeUserService;

    /**
     * @param next               SearchProvider
     * @param userMetricsService
     */
    public SearchMetricsFilter(SearchQuery next, UserMetricsService userMetricsService, RuntimeUserService runtimeUserService) {

        super(next);

        this.userMetricsService = userMetricsService;
        this.runtimeUserService = runtimeUserService;
    }

    @Override
    public SearchResult execute(Criteria criteria) {

        SearchResult searchResult = next.execute(criteria);

        captureMetric(criteria, searchResult);

        return searchResult;
    }

    private void captureMetric(Criteria criteria, SearchResult searchResult) {

        if (isUserSearch(criteria, searchResult)) {

            User user = this.runtimeUserService.getUser();

            this.userMetricsService.storeUserSearch(user, criteria, searchResult);
        }
    }

    private Boolean isUserSearch(Criteria criteria, SearchResult searchResult) {

        Boolean userSearch = false;

        if (criteria.getResultSize() == 0) {
            // loading the search page or clearing search results
        } else {

            Boolean facetConstrained = false;

            for (Facet facet : criteria.getFacets()) {
                if (null != facet.getConstraints() && !facet.getConstraints().isEmpty()) {
                    facetConstrained = true;
                }
            }

            if (StringUtils.hasText(criteria.getFreeText()) || facetConstrained) {
                userSearch = true;
            }

        }

        return userSearch;
    }
}
