package sgf.gateway.model.file.endpoint;

import sgf.gateway.model.metadata.dataaccess.FileAccessPoint;
import sgf.gateway.model.metadata.inventory.LogicalFile;

import java.net.URI;
import java.util.Set;

public abstract class AbstractEndpointStrategy implements EndpointStrategy {

    @Override
    public abstract URI getEndpoint(LogicalFile logicalFile);

    @Override
    public abstract boolean endpointExists(LogicalFile logicalFile);

    protected boolean fileAccessPointExists(LogicalFile logicalFile, String scheme) {

        boolean exists = false;

        FileAccessPoint endpoint = getFileAccessPoint(logicalFile, scheme);

        if (endpoint != null) {

            exists = true;
        }

        return exists;
    }

    protected FileAccessPoint getFileAccessPoint(LogicalFile logicalFile, String scheme) {

        FileAccessPoint match = null;

        Set<FileAccessPoint> fileAccessPoints = logicalFile.getFileAccessPoints();

        for (FileAccessPoint fileAccessPoint : fileAccessPoints) {

            if (fileAccessPoint.getEndpoint().getScheme().equalsIgnoreCase(scheme)) {
                match = fileAccessPoint;
                break;
            }
        }

        return match;
    }
}
