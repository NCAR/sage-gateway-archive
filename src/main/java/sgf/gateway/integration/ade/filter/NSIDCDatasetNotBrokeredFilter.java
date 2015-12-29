package sgf.gateway.integration.ade.filter;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.integration.ade.opensearch.Entry;
import sgf.gateway.model.metadata.Dataset;

public class NSIDCDatasetNotBrokeredFilter {

    private final DatasetRepository datasetRepository;
    private final String nsidcDataCenterName;

    public NSIDCDatasetNotBrokeredFilter(DatasetRepository datasetRepository, String nsidcDataCenterName) {
        super();
        this.datasetRepository = datasetRepository;
        this.nsidcDataCenterName = nsidcDataCenterName;
    }

    public Boolean filter(Entry payload) {

        Boolean pass = true;

        if (payload.getDataCenter().equalsIgnoreCase(nsidcDataCenterName)) {
            pass = !datasetBrokered(payload.getDatasetId());
        }

        return pass;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private Boolean datasetBrokered(String authoritativeIdentifier) {

        Dataset dataset = findDataset(authoritativeIdentifier);

        return (dataset != null);

    }

    private Dataset findDataset(final String authoritativeIdentifier) {
        Dataset dataset = datasetRepository.findByAuthoritativeIdentifier(authoritativeIdentifier);
        return dataset;
    }
}
