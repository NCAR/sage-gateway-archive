package sgf.gateway.search.provider.solr.query.configurer;

import sgf.gateway.search.api.Facets;
import sgf.gateway.search.api.Operation;

import java.util.Map;

public class SolrQueryFacetConstOperAndOnlyFilterBuilder extends SolrQueryFacetConstraintOperationFilterBuilder {

    public SolrQueryFacetConstOperAndOnlyFilterBuilder(Map<String, String> fieldMap) {
        super(fieldMap);
    }

    @Override
    protected String buildFilter(Facets facets) {
        String filterQuery = getFilterQueryByConstraintOperation(facets, Operation.AND);
        return filterQuery;
    }
}
