package sgf.gateway.model.metadata.dataaccess;

import sgf.gateway.model.metadata.inventory.LogicalFile;

import java.io.Serializable;
import java.net.URI;

public interface FileAccessPoint {

    Serializable getIdentifier();

    LogicalFile getLogicalFile();

    URI getEndpoint();
}
