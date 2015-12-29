package sgf.gateway.search.provider.solr.query.configurer;

import sgf.gateway.search.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SolrQueryFacetConstraintOperationFilterBuilder extends SolrQueryFilterBuilderAbstract {

    protected final Map<String, String> fieldMap;

    public SolrQueryFacetConstraintOperationFilterBuilder(Map<String, String> fieldMap) {
        this.fieldMap = fieldMap;
    }

    @Override
    public String build(Criteria criteria) {

        String filter = null;

        if (null != criteria.getFacets() && !criteria.getFacets().isEmpty()) {
            filter = buildFilter(criteria.getFacets());
        }

        return filter;
    }

    protected String buildFilter(Facets facets) {

        String filterQueryAndOperation = getFilterQueryByConstraintOperation(facets, Operation.AND);
        String filterQueryOrOperation = getFilterQueryByConstraintOperation(facets, Operation.OR);
        String filterQuery = getFilterQuery(filterQueryAndOperation, filterQueryOrOperation);

        return filterQuery;
    }

    protected String getFilterQuery(String filterQueryAndOperation, String filterQueryOrOperation) {

        String filterQuery;

        if (null != filterQueryAndOperation && null != filterQueryOrOperation) {
            filterQuery = filterQueryAndOperation + " " + Operation.AND + " (" + filterQueryOrOperation + ")";
        } else if (null != filterQueryAndOperation) {
            filterQuery = filterQueryAndOperation;
        } else {
            filterQuery = filterQueryOrOperation;
        }

        return filterQuery;
    }

    protected String getFilterQueryByConstraintOperation(Facets facets, Operation operation) {

        String filterQuery = null;

        List<String> fieldQueries = new ArrayList<String>();

        for (Facet facet : facets) {

            List<String> constraints = escapeConstraints(selectConstraintsByOperation(facet, operation));

            if (!constraints.isEmpty()) {
                fieldQueries.add(this.fieldMap.get(facet.getName()) + ":(" + join(constraints, operation) + ")");
            }
        }

        if (!fieldQueries.isEmpty()) {
            filterQuery = join(fieldQueries, operation);
        }

        return filterQuery;
    }

    protected List<Constraint> selectConstraintsByOperation(Facet facet, Operation operation) {

        List<Constraint> constraints = new ArrayList<Constraint>();

        for (Constraint constraint : facet.getConstraints()) {
            if (operation.equals(constraint.getOperation())) {
                constraints.add(constraint);
            }
        }

        return constraints;
    }

    protected List<String> escapeConstraints(List<Constraint> constraints) {

        List<String> escapedConstraints = new ArrayList<String>();

        for (Constraint constraint : constraints) {
            escapedConstraints.add(escapeValue(constraint.getName()));
        }

        return escapedConstraints;
    }
}
