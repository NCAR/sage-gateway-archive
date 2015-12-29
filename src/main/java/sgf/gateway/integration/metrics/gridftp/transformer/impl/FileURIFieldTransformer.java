package sgf.gateway.integration.metrics.gridftp.transformer.impl;

import sgf.gateway.integration.metrics.FileDownloadPayload;
import sgf.gateway.integration.metrics.gridftp.transformer.FieldTransformer;

import java.util.Map;

public class FileURIFieldTransformer implements FieldTransformer {

    private final FileURIStrategy fileURIStrategy;

    public FileURIFieldTransformer(FileURIStrategy fileURIStrategy) {
        this.fileURIStrategy = fileURIStrategy;
    }

    @Override
    public FileDownloadPayload transform(FileDownloadPayload payload, Map<String, String> tokens) {

        String fileURI = fileURIStrategy.getFileURI(tokens);
        payload.setFileURI(fileURI);

        return payload;
    }
}
