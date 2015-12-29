package sgf.gateway.publishing.thredds;

import thredds.catalog.InvDataset;

import java.net.URI;

public interface ThreddsDataServer {
    InvDataset get(URI authoritativeSourceURI);
}
