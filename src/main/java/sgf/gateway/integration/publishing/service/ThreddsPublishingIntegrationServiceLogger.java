package sgf.gateway.integration.publishing.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sgf.gateway.publishing.thredds.ThreddsDatasetDetails;

public class ThreddsPublishingIntegrationServiceLogger {

    private ThreddsPublishingIntegrationService threddsPublishingIntegrationService;

    private final static Logger LOG = LoggerFactory.getLogger(ThreddsPublishingIntegrationService.class);

    public ThreddsPublishingIntegrationServiceLogger(ThreddsPublishingIntegrationService threddsPublishingIntegrationService) {

        this.threddsPublishingIntegrationService = threddsPublishingIntegrationService;
    }


    public ThreddsDatasetDetails publishThreddsCatalog(ThreddsDatasetDetails details) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Publishing Started: " + details.getAuthoritativeSourceURI() + " User: " + details.getUserID() + " Parent Dataset: " + details.getParentShortName());
        }

        details = this.threddsPublishingIntegrationService.publishThreddsCatalog(details);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Publishing Completed: " + details.getAuthoritativeSourceURI() + " User: " + details.getUserID() + " Parent Dataset: " + details.getParentShortName());
        }

        return details;
    }


}
