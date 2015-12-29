package sgf.gateway.search.provider.solr.query.configurer;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import sgf.gateway.search.api.Criteria;
import sgf.gateway.search.api.SortOption;
import sgf.gateway.search.api.SortTargets;

public class SolrQuerySortOptionConfigurer implements SolrQueryConfigurer {

    protected final SortTargets sortTargets;

    public SolrQuerySortOptionConfigurer(SortTargets sortTargets) {
        super();
        this.sortTargets = sortTargets;
    }

    @Override
    public void configure(SolrQuery query, Criteria criteria) {
        if (null != criteria.getSortOptions()) {
            addSortOptions(query, criteria);
        }
    }

    protected void addSortOptions(SolrQuery query, Criteria criteria) {
        for (SortOption sortOption : criteria.getSortOptions()) {

            String fieldName = sortTargets.getFieldName(sortOption.getName());
            SolrQuery.ORDER order;

            if (sortOption.isDescending()) {
                order = SolrQuery.ORDER.desc;
            } else {
                order = SolrQuery.ORDER.asc;
            }

            SortClause sortClause = new SortClause(fieldName, order);
            query.addSort(sortClause);
        }
    }
}
