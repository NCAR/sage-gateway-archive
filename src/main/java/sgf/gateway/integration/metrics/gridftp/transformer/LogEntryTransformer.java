package sgf.gateway.integration.metrics.gridftp.transformer;

import sgf.gateway.service.metrics.FileDownloadDetails;

public interface LogEntryTransformer {
    FileDownloadDetails transform(String gridFTPMetrics);
}
