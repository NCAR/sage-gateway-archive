package sgf.gateway.search.filter;

import sgf.gateway.search.api.Criteria;
import sgf.gateway.search.api.SearchQuery;
import sgf.gateway.search.api.SearchResult;

public abstract class SearchFilterChain implements SearchQuery {

    protected SearchQuery next;

    public SearchFilterChain(SearchQuery next) {
        this.next = next;
    }

    @Override
    public SearchResult execute(Criteria criteria) {
        return next.execute(criteria);
    }
}
