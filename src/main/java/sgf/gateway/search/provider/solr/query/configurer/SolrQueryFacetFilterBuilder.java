package sgf.gateway.search.provider.solr.query.configurer;

import sgf.gateway.search.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SolrQueryFacetFilterBuilder extends SolrQueryFilterBuilderAbstract {

    protected final Map<String, String> fieldMap;
    protected final Operation filterOperation;

    public SolrQueryFacetFilterBuilder(Map<String, String> fieldMap, Operation filterOperation) {
        super();
        this.fieldMap = fieldMap;
        this.filterOperation = filterOperation;
    }

    @Override
    public String build(Criteria criteria) {

        String filter = null;

        if (null != criteria.getFacets() && !criteria.getFacets().isEmpty()) {
            List<String> fieldQueries = getFieldQueries(criteria.getFacets(), filterOperation);
            filter = join(fieldQueries, filterOperation);
        }

        return filter;
    }

    protected List<String> getFieldQueries(Facets facets, Operation operation) {

        List<String> fieldQueries = new ArrayList<String>();

        for (Facet facet : facets) {
            String fieldQuery = getFieldQuery(facet, operation);
            fieldQueries.add(fieldQuery);
        }

        return fieldQueries;
    }

    protected String getFieldQuery(Facet facet, Operation operation) {

        List<String> constraints = escapeConstraints(facet.getConstraints());

        String fieldQuery = this.fieldMap.get(facet.getName()) + ":(" + join(constraints, operation) + ")";

        return fieldQuery;
    }

    protected List<String> escapeConstraints(List<Constraint> constraints) {

        List<String> escapedConstraints = new ArrayList<String>();

        for (Constraint constraint : constraints) {
            escapedConstraints.add(escapeValue(constraint.getName()));
        }

        return escapedConstraints;
    }
}
