package sgf.gateway.model.metadata.dataaccess.factory.impl;

import org.safehaus.uuid.UUID;
import sgf.gateway.model.metadata.dataaccess.FileAccessPoint;
import sgf.gateway.model.metadata.dataaccess.FileAccessPointImpl;
import sgf.gateway.model.metadata.dataaccess.factory.FileAccessPointFactory;
import sgf.gateway.model.metadata.inventory.LogicalFile;

import java.net.URI;

public class FileAccessPointFactoryImpl implements FileAccessPointFactory {

    public FileAccessPoint createFileAccessPoint(LogicalFile logicalFile, URI endpoint) {

        UUID newIdentifier = org.safehaus.uuid.UUIDGenerator.getInstance().generateRandomBasedUUID();
        Integer version = null;

        FileAccessPointImpl fileAccessPoint;

        fileAccessPoint = new FileAccessPointImpl(newIdentifier, version, logicalFile, endpoint);

        return fileAccessPoint;
    }
}
