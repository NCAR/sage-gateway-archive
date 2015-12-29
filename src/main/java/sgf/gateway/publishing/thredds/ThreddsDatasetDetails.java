package sgf.gateway.publishing.thredds;

import org.safehaus.uuid.UUID;

import java.net.URI;

public interface ThreddsDatasetDetails {
    URI getAuthoritativeSourceURI();

    String getParentShortName();

    UUID getUserID();

    UUID getDatasetID();

    void setDatasetID(UUID datasetID);
}
