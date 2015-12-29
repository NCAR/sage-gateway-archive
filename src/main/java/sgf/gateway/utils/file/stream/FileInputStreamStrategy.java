package sgf.gateway.utils.file.stream;

import sgf.gateway.model.metadata.inventory.LogicalFile;

import java.io.InputStream;

public interface FileInputStreamStrategy {

    InputStream getFileInputStream(LogicalFile logicalFile);

}
