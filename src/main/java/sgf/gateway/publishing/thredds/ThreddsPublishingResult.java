package sgf.gateway.publishing.thredds;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.security.User;

import java.net.URI;

public class ThreddsPublishingResult {

    private final User user;
    private final URI authoritativeSourceURI;

    private Dataset dataset;

    public ThreddsPublishingResult(User user, URI authoritativeSourceURI) {
        super();
        this.user = user;
        this.authoritativeSourceURI = authoritativeSourceURI;
    }

    public User getUser() {
        return this.user;
    }

    public URI getAuthoritativeSourceURI() {
        return this.authoritativeSourceURI;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    public Dataset getDataset() {
        return this.dataset;
    }
}
