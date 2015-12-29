package sgf.gateway.search.provider.solr.query.configurer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.util.StringUtils;
import sgf.gateway.search.api.Criteria;
import sgf.gateway.search.provider.solr.query.SolrQueryFactory;

import java.util.List;

public class SolrQueryFilterConfigurer implements SolrQueryConfigurer {

    protected static final Log LOG = LogFactory.getLog(SolrQueryFactory.class);

    private final List<SolrQueryFilterBuilder> filterBuilders;

    public SolrQueryFilterConfigurer(List<SolrQueryFilterBuilder> filterBuilders) {
        super();
        this.filterBuilders = filterBuilders;
    }

    @Override
    public void configure(SolrQuery query, Criteria criteria) {

        for (SolrQueryFilterBuilder filterBuilder : filterBuilders) {

            String filter = filterBuilder.build(criteria);

            if (StringUtils.hasText(filter)) {
                query.addFilterQuery(filter);
                logFilter(filter);
            }
        }
    }

    private void logFilter(String filterQuery) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Filter query: " + filterQuery);
        }
    }
}
