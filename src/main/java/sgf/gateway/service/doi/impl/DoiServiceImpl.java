package sgf.gateway.service.doi.impl;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.event.DatasetStoredEvent;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.service.doi.DataciteDoiRequest;
import sgf.gateway.service.doi.DoiMetadata;
import sgf.gateway.service.doi.DoiService;
import sgf.gateway.service.doi.RemoteDoiFacade;

public class DoiServiceImpl implements DoiService, ApplicationEventPublisherAware {

    private final RemoteDoiFacade remoteDoiFacade;
    private final DatasetRepository datasetRepository;

    private ApplicationEventPublisher applicationEventPublisher;

    public DoiServiceImpl(RemoteDoiFacade remoteDoiFacade, DatasetRepository datasetRepository) {

        this.remoteDoiFacade = remoteDoiFacade;
        this.datasetRepository = datasetRepository;
    }

    @Override
    public void mintDoi(DataciteDoiRequest dataciteDoiRequest) {

        String datasetShortName = this.transactMintDoi(dataciteDoiRequest);

        this.fireDatasetStoredEvent(datasetShortName);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private String transactMintDoi(DataciteDoiRequest dataciteDoiRequest) {

        String doi = this.remoteDoiFacade.mintDoi(dataciteDoiRequest);

        Dataset dataset = this.datasetRepository.get(dataciteDoiRequest.getDatasetIdentifier());

        dataset.setDOI(doi);

        return dataset.getShortName();
    }

    private void fireDatasetStoredEvent(String shortName) {

        this.applicationEventPublisher.publishEvent(new DatasetStoredEvent(this, shortName));
    }

    @Override
    public DoiMetadata getDoiMetadata(String doiIdentifier) {

        DoiMetadata doiMetadata = this.remoteDoiFacade.getDoiMetadata(doiIdentifier);

        return doiMetadata;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateDoi(DataciteDoiRequest dataciteDoiRequest) {

        this.remoteDoiFacade.updateDoi(dataciteDoiRequest);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {

        this.applicationEventPublisher = applicationEventPublisher;
    }
}
