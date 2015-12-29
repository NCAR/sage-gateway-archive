package sgf.gateway.search.filter;

import sgf.gateway.search.api.*;
import sgf.gateway.search.core.CriteriaImpl;
import sgf.gateway.search.core.FacetsImpl;
import sgf.gateway.search.core.ResultsImpl;
import sgf.gateway.search.core.SearchResultImpl;


public class EmptySearchQuery implements SearchQuery {

    @Override
    public SearchResult execute(Criteria criteria) {

        Criteria emptyCriteria = new CriteriaImpl();
        Facets emptyFacets = new FacetsImpl();
        Results emptyResults = new ResultsImpl();
        Results emptyMoreLikeThisResults = new ResultsImpl();

        SearchResultImpl searchResult = new SearchResultImpl(emptyCriteria, 0L, emptyFacets, emptyResults);

        searchResult.setMoreLikeThisResults(emptyMoreLikeThisResults);

        return searchResult;
    }
}
