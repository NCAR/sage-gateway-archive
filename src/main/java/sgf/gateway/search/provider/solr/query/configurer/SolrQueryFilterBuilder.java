package sgf.gateway.search.provider.solr.query.configurer;

import sgf.gateway.search.api.Criteria;

public interface SolrQueryFilterBuilder {
    String build(Criteria criteria);
}
