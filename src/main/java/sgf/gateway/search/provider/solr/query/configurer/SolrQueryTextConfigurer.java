package sgf.gateway.search.provider.solr.query.configurer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import sgf.gateway.search.api.Criteria;
import sgf.gateway.search.provider.solr.query.FreeTextQueryStrategy;
import sgf.gateway.search.provider.solr.query.SolrQueryFactory;

public class SolrQueryTextConfigurer implements SolrQueryConfigurer {

    protected static final Log LOG = LogFactory.getLog(SolrQueryFactory.class);

    protected final FreeTextQueryStrategy freeTextQueryStrategy;

    public SolrQueryTextConfigurer(FreeTextQueryStrategy freeTextQueryStrategy) {
        super();
        this.freeTextQueryStrategy = freeTextQueryStrategy;
    }

    @Override
    public void configure(SolrQuery query, Criteria criteria) {

        String freeTextQuery = freeTextQueryStrategy.getFreeTextQuery(criteria.getFreeText());
        LOG.debug("Text query: " + freeTextQuery);

        query.setQuery(freeTextQuery);
    }
}
