package sgf.gateway.model.metadata.dataaccess.factory;

import sgf.gateway.model.metadata.dataaccess.FileAccessPoint;
import sgf.gateway.model.metadata.inventory.LogicalFile;

import java.net.URI;

public interface FileAccessPointFactory {

    FileAccessPoint createFileAccessPoint(LogicalFile logicalFile, URI endpoint);

}
