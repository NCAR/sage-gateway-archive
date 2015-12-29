package sgf.gateway.integration.metrics.tds.transformer;

import sgf.gateway.service.metrics.FileDownloadDetails;

public interface LogEntryTransformer {
    FileDownloadDetails transform(String tdsMetrics);
}
