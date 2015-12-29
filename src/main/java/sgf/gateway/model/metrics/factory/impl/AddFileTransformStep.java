package sgf.gateway.model.metrics.factory.impl;

import sgf.gateway.dao.metadata.LogicalFileRepository;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.metrics.FileDownload;

import java.net.URI;

public class AddFileTransformStep implements TransformStep {

    private final LogicalFileRepository logicalFileRepository;

    public AddFileTransformStep(LogicalFileRepository logicalFileRepository) {

        this.logicalFileRepository = logicalFileRepository;
    }

    @Override
    public FileDownload transform(FileDownload fileDownload) {

        String stringUri = fileDownload.getFileAccessPointUri();

        URI uri = URI.create(stringUri);

        LogicalFile logicalFile = this.logicalFileRepository.findLogicalFileByAccessPointURL(uri);

        fileDownload.setLogicalFileIdentifier(logicalFile.getIdentifier());
        fileDownload.setLogicalFileName(logicalFile.getName());
        fileDownload.setLogicalFileSize(logicalFile.getSize());
        fileDownload.setLogicalFileLineageId(logicalFile.getLineageIdentifier());
        fileDownload.setLogicalFileVersionId(logicalFile.getVersionIdentifier());

        return fileDownload;
    }

}
