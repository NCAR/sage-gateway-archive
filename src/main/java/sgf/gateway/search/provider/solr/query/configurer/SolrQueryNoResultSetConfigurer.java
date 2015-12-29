package sgf.gateway.search.provider.solr.query.configurer;

import org.apache.solr.client.solrj.SolrQuery;
import sgf.gateway.search.api.Criteria;

public class SolrQueryNoResultSetConfigurer implements SolrQueryConfigurer {

    @Override
    public void configure(SolrQuery query, Criteria criteria) {
        query.setRows(0);
        query.setStart(0);
    }
}
