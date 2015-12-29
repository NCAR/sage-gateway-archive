package sgf.gateway.publishing.thredds.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sgf.gateway.publishing.thredds.ThreddsDataServer;
import sgf.gateway.publishing.thredds.ThreddsDataServerException;
import thredds.catalog.InvCatalog;
import thredds.catalog.InvCatalogFactory;
import thredds.catalog.InvDataset;

import java.net.URI;

public class ThreddsDataServerImpl implements ThreddsDataServer {

    private final static Logger LOG = LoggerFactory.getLogger(ThreddsDataServerImpl.class);

    @Override
    public InvDataset get(URI authoritativeSourceURI) {

        InvCatalog invCatalog = createInvCatalog(authoritativeSourceURI);
        errorCheckInvCatalog(invCatalog, authoritativeSourceURI);

        InvDataset invDataset = getInvDataset(invCatalog);

        return invDataset;
    }

    private InvCatalog createInvCatalog(URI authoritativeSourceURI) {

        InvCatalogFactory factory = new InvCatalogFactory("default", true);
        InvCatalog invCatalog = factory.readXML(authoritativeSourceURI);

        return invCatalog;
    }

    private void errorCheckInvCatalog(InvCatalog invCatalog, URI authoritativeSourceURI) {

        StringBuilder sb = new StringBuilder();

        if (!invCatalog.check(sb)) {
            LOG.debug("check failed after making InvCatalog from source: " + authoritativeSourceURI);
            throw new ThreddsDataServerException("Error, invalid catalog " + authoritativeSourceURI + ":" + sb);
        }
    }

    private InvDataset getInvDataset(InvCatalog invCatalog) {

        InvDataset invDataset = invCatalog.getDatasets().get(0);

        return invDataset;
    }
}
