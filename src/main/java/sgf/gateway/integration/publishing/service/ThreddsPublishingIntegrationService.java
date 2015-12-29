package sgf.gateway.integration.publishing.service;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.publishing.thredds.ThreddsDatasetDetails;
import sgf.gateway.publishing.thredds.ThreddsPublishingService;

public class ThreddsPublishingIntegrationService {

    private final ThreddsPublishingService publishingService;

    public ThreddsPublishingIntegrationService(ThreddsPublishingService publishingService) {
        super();
        this.publishingService = publishingService;
    }

    public ThreddsDatasetDetails publishThreddsCatalog(ThreddsDatasetDetails details) {

        Dataset dataset = this.publishingService.publishThreddsCatalog(details);

        details.setDatasetID(dataset.getIdentifier());

        return details;
    }
}
