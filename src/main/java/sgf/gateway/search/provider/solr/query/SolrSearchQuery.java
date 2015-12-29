package sgf.gateway.search.provider.solr.query;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrException;
import org.apache.solr.search.SyntaxError;
import sgf.gateway.search.api.Criteria;
import sgf.gateway.search.api.SearchQuery;
import sgf.gateway.search.api.SearchResult;
import sgf.gateway.search.core.SearchQueryException;
import sgf.gateway.search.core.SearchQuerySyntaxError;
import sgf.gateway.search.provider.solr.SolrResultsFactory;

public class SolrSearchQuery implements SearchQuery {

    private final SolrServer solrServer;
    private final SolrQueryFactory solrQueryFactory;
    private final SolrResultsFactory solrResultsFactory;

    public SolrSearchQuery(final SolrServer solrServer, final SolrQueryFactory solrQueryFactory, final SolrResultsFactory solrResultsFactory) {
        super();
        this.solrQueryFactory = solrQueryFactory;
        this.solrResultsFactory = solrResultsFactory;
        this.solrServer = solrServer;
    }

    @Override
    public SearchResult execute(Criteria criteria) {

        SolrQuery solrQuery = createSolrQuery(criteria);
        SearchResult results = executeSolrQuery(solrQuery, criteria);

        return results;
    }

    private SolrQuery createSolrQuery(Criteria criteria) {
        SolrQuery solrQuery = solrQueryFactory.createQuery(criteria);
        return solrQuery;
    }

    private SearchResult executeSolrQuery(SolrQuery solrQuery, Criteria criteria) {

        QueryResponse solrResponse = trySolrQuery(solrQuery);
        SearchResult searchResult = solrResultsFactory.getSearchResult(criteria, solrResponse);

        return searchResult;
    }

    private QueryResponse trySolrQuery(SolrQuery solrQuery) {

        QueryResponse solrResponse;

        try {
            solrResponse = solrServer.query(solrQuery);
        } catch (Exception e) {
            throw packageSolrException(e);
        }

        return solrResponse;
    }

    private SearchQueryException packageSolrException(Exception e) {

        SearchQueryException packagedException;

        if (e instanceof SolrException && e.getCause() instanceof SyntaxError) {
            SearchQuerySyntaxError syntaxException = new SearchQuerySyntaxError(e.getCause().getMessage());
            packagedException = new SearchQueryException(syntaxException);
        } else {
            packagedException = new SearchQueryException(e);
        }

        return packagedException;
    }
}
