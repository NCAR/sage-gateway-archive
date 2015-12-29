package sgf.gateway.integration.filter;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.service.metadata.DatasetDetails;

public class BrokeredDatasetFilter {

    private final DatasetRepository datasetRepository;

    public BrokeredDatasetFilter(DatasetRepository datasetRepository) {
        super();
        this.datasetRepository = datasetRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean filter(DatasetDetails details) {

        String authId = details.getAuthoritativeIdentifier();
        Boolean pass = !this.datasetExists(authId) || this.datasetIsBrokered(authId);

        return pass;
    }

    private Boolean datasetIsBrokered(String authoritativeIdentifier) {
        Dataset dataset = this.findDataset(authoritativeIdentifier);
        return dataset.isBrokered();
    }

    private Boolean datasetExists(String authoritativeIdentifier) {

        Boolean exists = false;
        Dataset dataset = findDataset(authoritativeIdentifier);

        if (dataset != null) {
            exists = true;
        }

        return exists;
    }

    private Dataset findDataset(final String authoritativeIdentifier) {
        Dataset dataset = datasetRepository.findByAuthoritativeIdentifier(authoritativeIdentifier);
        return dataset;
    }
}
