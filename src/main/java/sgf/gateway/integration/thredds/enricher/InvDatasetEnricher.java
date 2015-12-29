package sgf.gateway.integration.thredds.enricher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thredds.catalog.InvCatalog;
import thredds.catalog.InvCatalogFactory;
import thredds.catalog.InvDataset;

import java.net.URI;

public class InvDatasetEnricher {

    private final static Logger LOG = LoggerFactory.getLogger(InvDatasetEnricher.class);

    public InvDataset getDatasetInvDataset(URI payload) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("enrichment request received, creating InvDataset for ThreddsDatasetPayload source " + payload);
        }

        InvDataset invDataset = tryGetInvDataset(payload);

        return invDataset;
    }

    private InvDataset tryGetInvDataset(URI source) {

        InvDataset invDataset;

        try {
            invDataset = getInvDataset(source);
        } catch (Exception e) {
            LOG.error("Could not get InvDataset for " + source, e);
            throw new RuntimeException(e);
        }

        return invDataset;
    }

    private InvDataset getInvDataset(URI source) {

        InvCatalogFactory factory = new InvCatalogFactory("default", true);
        InvCatalog invCatalog = factory.readXML(source);

        StringBuilder sb = new StringBuilder();
        if (!invCatalog.check(sb)) {
            LOG.debug("check failed after making InvCatalog from source: " + source);
            throw new RuntimeException("Error, invalid catalog " + source + ":" + sb);
        }

        InvDataset invDataset = invCatalog.getDatasets().get(0);

        return invDataset;
    }
}
