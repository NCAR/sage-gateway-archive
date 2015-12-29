package sgf.gateway.integration.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sgf.gateway.service.metadata.CadisDatasetService;
import sgf.gateway.service.metadata.DatasetDetails;

public class DatasetLoadService {

    private final static Logger LOG = LoggerFactory.getLogger(DatasetLoadService.class);

    private final CadisDatasetService datasetService;

    public DatasetLoadService(CadisDatasetService datasetService) {
        super();
        this.datasetService = datasetService;
    }

    public void load(DatasetDetails payload) {

        if (LOG.isInfoEnabled()) {
            LOG.info("loading dataset payload: " + payload);
        }

        datasetService.saveOrUpdate(payload);
    }
}