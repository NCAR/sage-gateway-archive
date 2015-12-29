package sgf.gateway.integration.metrics.gridftp.transformer.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sgf.gateway.dao.metadata.LogicalFileRepository;
import sgf.gateway.integration.metrics.FileDownloadPayload;
import sgf.gateway.integration.metrics.gridftp.transformer.FieldTransformer;
import sgf.gateway.model.metadata.inventory.LogicalFile;

import java.net.URI;
import java.util.Map;

public class CompletedFieldTransformer implements FieldTransformer {

    private final String statusCodeKey;
    private final String bytesSentKey;
    private final String successfulStatusCode;
    private final FileURIStrategy fileURIStrategy;
    private final LogicalFileRepository logicalFileRepository;


    public CompletedFieldTransformer(String statusCodeKey, String bytesSentKey, String successfulStatusCode,
                                     FileURIStrategy fileURIStrategy, LogicalFileRepository logicalFileRepository) {
        this.statusCodeKey = statusCodeKey;
        this.bytesSentKey = bytesSentKey;
        this.successfulStatusCode = successfulStatusCode;
        this.fileURIStrategy = fileURIStrategy;
        this.logicalFileRepository = logicalFileRepository;
    }

    @Override
    public FileDownloadPayload transform(FileDownloadPayload payload, Map<String, String> tokens) {

        if (successfulStatusCode(tokens) && bytesSentEqualsFileBytes(tokens)) {
            payload.setCompleted(true);
        } else {
            payload.setCompleted(false);
        }

        return payload;
    }

    private Boolean successfulStatusCode(Map<String, String> tokens) {

        Boolean successful = false;

        String statusCode = tokens.get(statusCodeKey);

        if (statusCode.equals(successfulStatusCode)) {
            successful = true;
        }

        return successful;
    }

    private Boolean bytesSentEqualsFileBytes(Map<String, String> tokens) {

        Boolean equals = false;

        Long bytesSent = getBytesSent(tokens);
        Long fileBytes = getFileBytes(tokens);

        if (bytesSent.equals(fileBytes)) {
            equals = true;
        }

        return equals;
    }

    private Long getBytesSent(Map<String, String> tokens) {

        String bytesSentToken = tokens.get(bytesSentKey);
        Long bytesSent = new Long(bytesSentToken);

        return bytesSent;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private Long getFileBytes(Map<String, String> tokens) {

        LogicalFile logicalFile = getLogicalFile(tokens);
        Long fileBytes = logicalFile.getSize();

        return fileBytes;
    }

    private LogicalFile getLogicalFile(Map<String, String> tokens) {

        String fileURIAsString = fileURIStrategy.getFileURI(tokens);
        URI fileURI = URI.create(fileURIAsString);

        LogicalFile logicalFile = logicalFileRepository.findLogicalFileByAccessPointURL(fileURI);

        return logicalFile;
    }
}
