package sgf.gateway.integration.metrics.gridftp.transformer;

import sgf.gateway.integration.metrics.FileDownloadPayload;

import java.util.Map;

public interface FieldTransformer {
    FileDownloadPayload transform(FileDownloadPayload payload, Map<String, String> tokens);
}
