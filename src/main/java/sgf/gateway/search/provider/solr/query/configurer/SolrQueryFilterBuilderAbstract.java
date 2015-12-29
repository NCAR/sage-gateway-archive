package sgf.gateway.search.provider.solr.query.configurer;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.util.ClientUtils;
import sgf.gateway.search.api.Criteria;
import sgf.gateway.search.api.Operation;

import java.util.List;

public abstract class SolrQueryFilterBuilderAbstract implements SolrQueryFilterBuilder {

    @Override
    public abstract String build(Criteria criteria);

    protected String join(List<String> queryElements, Operation operation) {
        String joinedQueries = StringUtils.join(queryElements, " " + operation + " ");
        return joinedQueries;
    }

    protected String escapeValue(String value) {
        return ClientUtils.escapeQueryChars(value);
    }
}
