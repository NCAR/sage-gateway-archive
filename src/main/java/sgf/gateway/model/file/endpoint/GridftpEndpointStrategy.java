package sgf.gateway.model.file.endpoint;

import sgf.gateway.model.metadata.dataaccess.FileAccessPoint;
import sgf.gateway.model.metadata.inventory.LogicalFile;

import java.net.URI;

public class GridftpEndpointStrategy extends AbstractEndpointStrategy {

    protected static String SCHEME = "gsiftp";

    @Override
    public URI getEndpoint(LogicalFile logicalFile) {

        URI endpoint = null;

        if (fileAccessPointExists(logicalFile, SCHEME)) {

            FileAccessPoint fileAccessPoint = getFileAccessPoint(logicalFile, SCHEME);

            endpoint = buildGridftpUri(fileAccessPoint);
        }

        return endpoint;
    }

    @Override
    public boolean endpointExists(LogicalFile logicalFile) {

        boolean exists = fileAccessPointExists(logicalFile, SCHEME);

        return exists;
    }

    protected URI buildGridftpUri(FileAccessPoint fileAccessPoint) {

        String endpoint = fileAccessPoint.getEndpoint().toString();

        String fileSize = fileAccessPoint.getLogicalFile().getSize().toString();

        URI gridftpEndpoint = URI.create(endpoint + "?size=" + fileSize);

        gridftpEndpoint = gridftpEndpoint.normalize();

        return gridftpEndpoint;
    }
}
