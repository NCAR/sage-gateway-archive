package sgf.gateway.search.provider.solr.query;

import org.apache.solr.client.solrj.SolrQuery;
import sgf.gateway.search.api.Criteria;

public interface SolrQueryFactory {

    SolrQuery createQuery(Criteria criteria);
}
