package sgf.gateway.search.provider.solr;

import org.apache.solr.client.solrj.response.QueryResponse;
import sgf.gateway.search.api.Criteria;
import sgf.gateway.search.api.SearchResult;

public interface SolrResultsFactory {
    SearchResult getSearchResult(Criteria searchCriteria, QueryResponse queryResponse);
}
