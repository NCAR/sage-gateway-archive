package sgf.gateway.search.provider.solr.query.configurer;

import org.apache.solr.client.solrj.SolrQuery;
import sgf.gateway.search.api.Criteria;

public interface SolrQueryConfigurer {
    void configure(SolrQuery query, Criteria criteria);
}
