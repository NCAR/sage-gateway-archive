package sgf.gateway.integration.filter;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.service.metadata.DatasetDetails;

public class DatasetOfDatacenterFilter {

    private final DatasetRepository datasetRepository;

    public DatasetOfDatacenterFilter(DatasetRepository datasetRepository) {
        super();
        this.datasetRepository = datasetRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean filter(DatasetDetails details) {
        Boolean pass = !datasetExists(details.getAuthoritativeIdentifier()) || datasetOfDatacenter(details);
        return pass;
    }

    private Boolean datasetExists(String authoritativeIdentifier) {

        Boolean exists = false;
        Dataset dataset = findDataset(authoritativeIdentifier);

        if (dataset != null) {
            exists = true;
        }

        return exists;
    }

    private Boolean datasetOfDatacenter(DatasetDetails details) {

        Dataset dataset = findDataset(details.getAuthoritativeIdentifier());
        Boolean ofDatacenter = dataset.getDataCenter().getShortName().equals(details.getDataCenterName());

        return ofDatacenter;
    }

    private Dataset findDataset(final String authoritativeIdentifier) {
        Dataset dataset = datasetRepository.findByAuthoritativeIdentifier(authoritativeIdentifier);
        return dataset;
    }
}
