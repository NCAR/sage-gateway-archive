package sgf.gateway.search.provider.solr.query.configurer;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.util.ClientUtils;
import sgf.gateway.search.api.Criteria;
import sgf.gateway.search.api.Operation;
import sgf.gateway.search.api.SearchParameter;
import sgf.gateway.search.api.SearchParameters;

import java.util.ArrayList;
import java.util.List;


public class SolrQuerySearchParameterConfigurer implements SolrQueryConfigurer {

    protected final Operation operation;

    public SolrQuerySearchParameterConfigurer(Operation operation) {
        this.operation = operation;
    }

    @Override
    public void configure(SolrQuery query, Criteria criteria) {
        query.setQuery(this.formulateQueryFromParameters(criteria.getSearchParameters()));
    }

    protected String formulateQueryFromParameters(SearchParameters parameters) {

        List<String> queryConditions = new ArrayList<String>();

        for (SearchParameter parameter : parameters) {
            queryConditions.add(this.formulateCondition(parameter.getName(), parameter.getValue()));
        }

        return this.join(queryConditions, this.operation);
    }

    protected String formulateCondition(String fieldName, String fieldValue) {
        String condition = fieldName + ":" + this.escapeValue(fieldValue);
        return condition;
    }

    protected String join(List<String> queryElements, Operation operation) {
        String joinedQueries = StringUtils.join(queryElements, " " + operation + " ");
        return joinedQueries;
    }

    protected String escapeValue(String value) {
        return ClientUtils.escapeQueryChars(value);
    }
}
