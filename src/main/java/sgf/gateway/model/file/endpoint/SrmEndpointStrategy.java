package sgf.gateway.model.file.endpoint;

import sgf.gateway.model.metadata.dataaccess.FileAccessPoint;
import sgf.gateway.model.metadata.inventory.LogicalFile;

import java.net.URI;

public class SrmEndpointStrategy extends AbstractEndpointStrategy {

    private static final String SCHEME = "srm";

    @Override
    public URI getEndpoint(LogicalFile logicalFile) {

        URI endpoint = null;

        if (this.fileAccessPointExists(logicalFile, SCHEME)) {

            FileAccessPoint fileAccessPoint = this.getFileAccessPoint(logicalFile, SCHEME);

            endpoint = fileAccessPoint.getEndpoint();
        }

        return endpoint;
    }

    @Override
    public boolean endpointExists(LogicalFile logicalFile) {

        boolean exists = this.fileAccessPointExists(logicalFile, SCHEME);

        return exists;
    }
}
