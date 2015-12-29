package sgf.gateway.search.api;

public interface SearchResult {

    Criteria getCriteria();

    Long getResultCount();

    Facets getFacets();

    Results getResults();

    Results getMoreLikeThisResults();
}
