package sgf.gateway.model.file.endpoint;

import sgf.gateway.model.metadata.inventory.LogicalFile;

import java.net.URI;

public interface EndpointStrategy {

    URI getEndpoint(LogicalFile logicalFile);

    boolean endpointExists(LogicalFile logicalFile);
}
