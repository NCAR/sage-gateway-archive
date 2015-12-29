package sgf.gateway.search.filter.persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.safehaus.uuid.UUID;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.Dataset;

public class DatasetLoader extends AbstractLoader {

    private static final Log LOG = LogFactory.getLog(DatasetLoader.class);

    private DatasetRepository datasetRepository;

    public DatasetLoader(String type, DatasetRepository datasetRepository) {
        super(type);

        this.datasetRepository = datasetRepository;
    }

    @Override
    public Object load(String identifier) {

        Dataset dataset = this.datasetRepository.get(UUID.valueOf(identifier));

        if (LOG.isDebugEnabled()) {
            LOG.debug("Loaded Dataset: " + dataset.getShortName());
        }

        return dataset;
    }

}
