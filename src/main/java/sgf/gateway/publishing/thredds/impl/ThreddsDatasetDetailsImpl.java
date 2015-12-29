package sgf.gateway.publishing.thredds.impl;

import org.safehaus.uuid.UUID;
import sgf.gateway.publishing.thredds.ThreddsDatasetDetails;

import java.io.Serializable;
import java.net.URI;

public class ThreddsDatasetDetailsImpl implements ThreddsDatasetDetails, Serializable {

    private static final long serialVersionUID = 1L;

    private final URI authoritativeSourceURI;
    private final String parentShortName;
    private final UUID userID;

    private UUID datasetID;

    public ThreddsDatasetDetailsImpl(URI authoritativeSourceURI, String parentShortName, UUID userID) {
        super();
        this.authoritativeSourceURI = authoritativeSourceURI;
        this.parentShortName = parentShortName;
        this.userID = userID;
    }

    @Override
    public URI getAuthoritativeSourceURI() {
        return this.authoritativeSourceURI;
    }

    @Override
    public String getParentShortName() {
        return this.parentShortName;
    }

    @Override
    public UUID getUserID() {
        return this.userID;
    }

    @Override
    public UUID getDatasetID() {
        return this.datasetID;
    }

    @Override
    public void setDatasetID(UUID datasetID) {
        this.datasetID = datasetID;
    }

    @Override
    public String toString() {
        return "User " + this.userID + " publishing " + this.authoritativeSourceURI;
    }
}
