package sgf.gateway.search.provider.solr.query.configurer;

import sgf.gateway.search.api.Criteria;
import sgf.gateway.search.api.Operation;

import java.util.ArrayList;
import java.util.List;

public class SolrQueryExcludeDataCenterFilterBuilder extends SolrQueryFilterBuilderAbstract {

    private String filter;

    public SolrQueryExcludeDataCenterFilterBuilder(String dataCenterField, List<String> excludedDataCenters) {
        super();
        this.buildFilter(dataCenterField, excludedDataCenters);
    }

    @Override
    public String build(Criteria criteria) {
        return this.filter;
    }

    private void buildFilter(String dataCenterField, List<String> excludedDataCenters) {

        if (!excludedDataCenters.isEmpty()) {
            this.filter = this.formatExclusion(this.aggregateConditions(dataCenterField, excludedDataCenters));
        }
    }

    private List<String> aggregateConditions(String dataCenterField, List<String> excludedDataCenters) {

        List<String> conditions = new ArrayList<String>();

        for (String dataCenter : excludedDataCenters) {
            conditions.add(this.formatCondition(dataCenterField, dataCenter));
        }

        return conditions;
    }

    private String formatCondition(String dataCenterField, String dataCenter) {
        return dataCenterField + ":" + this.escapeValue(dataCenter);
    }

    private String formatExclusion(List<String> conditions) {
        if (conditions.size() == 1) {
            return "-" + conditions.get(0);
        } else {
            return "-(" + this.join(conditions, Operation.OR) + ")";
        }
    }
}
