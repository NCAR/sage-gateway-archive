package sgf.gateway.service.share;

import org.safehaus.uuid.UUID;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.Dataset;


public class ShareDataServiceImpl implements ShareDataService {

    private RemoteShareDataFacade remoteFacade;
    private DatasetRepository datasetRepository;

    public ShareDataServiceImpl(RemoteShareDataFacade remoteFacade, DatasetRepository datasetRepository) {

        this.remoteFacade = remoteFacade;
        this.datasetRepository = datasetRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void shareDataset(UUID datasetId) {

        Dataset dataset = datasetRepository.get(datasetId);

        remoteFacade.pushToShare(dataset);
    }

}
