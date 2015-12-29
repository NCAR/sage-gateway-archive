package sgf.gateway.model.file.endpoint;

import sgf.gateway.model.Gateway;
import sgf.gateway.model.metadata.dataaccess.FileAccessPoint;
import sgf.gateway.model.metadata.inventory.LogicalFile;

import java.net.URI;

public class HttpEndpointStrategy extends AbstractEndpointStrategy {

    protected static String SCHEME = "http";

    private final Gateway gateway;

    public HttpEndpointStrategy(Gateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public URI getEndpoint(LogicalFile logicalFile) {

        URI endpoint = null;

        if (fileAccessPointExists(logicalFile, SCHEME)) {

            FileAccessPoint fileAccessPoint = getFileAccessPoint(logicalFile, SCHEME);

            endpoint = fileAccessPoint.getEndpoint();

        } else if (logicalFile.getDiskLocation() != null) {

            endpoint = buildHttpUri(logicalFile);
        }

        return endpoint;
    }

    @Override
    public boolean endpointExists(LogicalFile logicalFile) {

        boolean exists = false;

        if (fileAccessPointExists(logicalFile, SCHEME) || logicalFile.getDiskLocation() != null) {
            exists = true;
        }

        return exists;
    }

    protected URI buildHttpUri(LogicalFile logicalFile) {

        String uriString = gateway.getBaseSecureURL() + "download/fileDownload.htm?logicalFileId=" + logicalFile.getIdentifier();

        URI endpoint = URI.create(uriString);

        endpoint = endpoint.normalize();

        return endpoint;
    }
}
