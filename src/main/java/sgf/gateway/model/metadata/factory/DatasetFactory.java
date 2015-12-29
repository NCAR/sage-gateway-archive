package sgf.gateway.model.metadata.factory;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.security.User;

import java.net.URI;
import java.util.Date;

public interface DatasetFactory {

    Dataset createDataset(String name, String primaryIdentifier, Dataset parent, User creator, String versionIdentifier, String versionLabel, String creationComment,
                          Date creationDate, URI authoritativeSourceURI, Boolean brokered, String authoritativeIdentifier);

    // FIXME - This should be a specialization used by only CADIS, not a CADIS specific method.
    Dataset createCadisDataset(String shortName, String title, Dataset parent, Boolean brokered);

    // FIXME - This should be a specialization used by only CADIS, not a CADIS specific method.
    Dataset createCadisDataset(String shortName, String title, Dataset parent, Boolean brokered, String authoritativeIdentifier);

    // FIXME - This should be a specialization used by only CADIS, not a CADIS specific method.
    Dataset createCadisProject(String shortName, String title, Dataset parent, Boolean brokered, String authoritativeIdentifier);
}
