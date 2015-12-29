package sgf.gateway.search.provider.solr.query.configurer;

import org.apache.solr.client.solrj.SolrQuery;
import sgf.gateway.search.api.Criteria;

public class SolrQueryResultSetConfigurer implements SolrQueryConfigurer {

    @Override
    public void configure(SolrQuery query, Criteria criteria) {
        query.setRows(criteria.getResultSize());
        query.setStart(criteria.getStartIndex());
    }
}
