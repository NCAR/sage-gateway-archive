package sgf.gateway.search.provider.solr.query.configurer;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.params.FacetParams;
import sgf.gateway.search.api.Criteria;

import java.util.List;

public class SolrQueryFacetConfigurer implements SolrQueryConfigurer {

    private final List<String> facetFields;

    protected String facetSort = FacetParams.FACET_SORT_INDEX; // facet constraints sorted on index.
    protected Integer facetMinCount = 1; // exclude facet constraints whose hit count is less than facetMinCount
    protected Integer facetLimit = -1; // limit of the number of facet constraints returned to facetLimit

    public SolrQueryFacetConfigurer(List<String> facetFields) {
        super();
        this.facetFields = facetFields;
    }

    @Override
    public void configure(SolrQuery query, Criteria criteria) {
        configureForFaceting(query);
        addFacetFields(query);
    }

    protected void configureForFaceting(SolrQuery query) {
        query.setFacet(true);
        query.setFacetSort(facetSort);
        query.setFacetMinCount(facetMinCount);
        query.setFacetLimit(facetLimit);
    }

    protected void addFacetFields(SolrQuery query) {
        for (String field : this.facetFields) {
            query.addFacetField(field);
        }
    }
}
