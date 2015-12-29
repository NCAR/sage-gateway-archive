package sgf.gateway.search.core;

import sgf.gateway.search.api.Criteria;
import sgf.gateway.search.api.Facets;
import sgf.gateway.search.api.Results;
import sgf.gateway.search.api.SearchResult;

public class SearchResultImpl implements SearchResult {

    private Criteria criteria;
    private Long resultCount;
    private Facets facets;
    private Results results;
    private Results moreLikeThisResults;

    public SearchResultImpl(Criteria criteria, Long resultCount, Facets facets, Results results) {
        super();
        this.criteria = criteria;
        this.resultCount = resultCount;
        this.facets = facets;
        this.results = results;
    }

    public Criteria getCriteria() {
        return criteria;
    }

    public Long getResultCount() {
        return resultCount;
    }

    public Facets getFacets() {
        return facets;
    }

    public Results getResults() {
        return results;
    }

    public void setMoreLikeThisResults(Results moreLikeThisResults) {
        this.moreLikeThisResults = moreLikeThisResults;
    }

    public Results getMoreLikeThisResults() {
        return this.moreLikeThisResults;
    }
}
