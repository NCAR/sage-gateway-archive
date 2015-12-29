package sgf.gateway.integration.ade.filter;

import org.springframework.util.StringUtils;
import sgf.gateway.integration.ade.opensearch.Entry;

import java.util.Set;

public class DataCenterFilter {

    private final Set<String> excludedDataCenters;

    public DataCenterFilter(Set<String> excludedDataCenters) {
        super();
        this.excludedDataCenters = excludedDataCenters;
    }

    public Boolean filter(Entry payload) {
        Boolean pass = dataCenterSpecified(payload) && !dataCenterExcluded(payload);
        return pass;
    }

    private Boolean dataCenterSpecified(Entry payload) {
        return StringUtils.hasText(payload.getDataCenter());
    }

    private Boolean dataCenterExcluded(Entry payload) {
        return excludedDataCenters.contains(payload.getDataCenter());
    }
}
