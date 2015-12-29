package sgf.gateway.utils.file.stream.impl;

import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.utils.file.stream.FileInputStreamStrategy;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LocalFileInputStreamStrategy implements FileInputStreamStrategy {

    private static final int MAX_FILE_BUFFER_SIZE = 8 * 1024;

    public InputStream getFileInputStream(LogicalFile logicalFile) {

        InputStream inputStream = getLocalFileInputStream(logicalFile);

        return inputStream;
    }

    private BufferedInputStream getLocalFileInputStream(LogicalFile logicalFile) {

        BufferedInputStream inputStream;
        int fileBufferSize = getFileBufferSize(logicalFile);

        try {

            inputStream = new BufferedInputStream(new FileInputStream(logicalFile.getFile()), fileBufferSize);

        } catch (IOException e) {

            throw new RuntimeException(e);
        }

        return inputStream;
    }

    private int getFileBufferSize(LogicalFile logicalFile) {

        long bufferSize = MAX_FILE_BUFFER_SIZE;

        if (logicalFile.getSize() < MAX_FILE_BUFFER_SIZE) {
            bufferSize = logicalFile.getSize();
        }

        return (int) bufferSize;
    }

}
