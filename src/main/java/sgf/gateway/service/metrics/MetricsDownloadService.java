package sgf.gateway.service.metrics;

import sgf.gateway.model.metrics.FileDownload;

/**
 * The Interface MetricsDownloadService.
 */
public interface MetricsDownloadService {

    void storeFileDownload(FileDownload fileDownload);

    // This method is needed by Spring Integration for storing file downloads from our access logs.
    void saveFileDownload(FileDownloadDetails fileDownloadDetails);
}
