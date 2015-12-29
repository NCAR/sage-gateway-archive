package sgf.gateway.model.metadata.factory;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.DatasetVersion;
import sgf.gateway.model.security.User;

import java.net.URI;

public interface DatasetVersionFactory {

    DatasetVersion create(String versionIdentifier, Dataset dataset, User publisher, String label, String comment, URI authoritativeSourceURI);
}
