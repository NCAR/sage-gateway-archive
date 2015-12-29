package sgf.gateway.service.metrics.impl.spring;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sgf.gateway.dao.metrics.MetricsDownloadDAO;
import sgf.gateway.model.metrics.FileDownload;
import sgf.gateway.model.metrics.factory.FileDownloadFactory;
import sgf.gateway.service.metrics.ExceptionStrategy;
import sgf.gateway.service.metrics.FileDownloadDetails;
import sgf.gateway.service.metrics.MetricsDownloadService;

public class MetricsDownloadServiceImpl implements MetricsDownloadService {

    private final MetricsDownloadDAO aMetricsDownloadDAO;

    private final FileDownloadFactory fileDownloadFactory;

    private final ExceptionStrategy exceptionStrategy;

    public MetricsDownloadServiceImpl(MetricsDownloadDAO downloadDAO, FileDownloadFactory fileDownloadFactory, ExceptionStrategy exceptionStrategy) {

        this.aMetricsDownloadDAO = downloadDAO;
        this.fileDownloadFactory = fileDownloadFactory;
        this.exceptionStrategy = exceptionStrategy;
    }

    // FIXME this class should take a file download details object and then create the FileDownload object from a factory instead of having the FileDownload object passed in as a parameter.
    public void storeFileDownload(FileDownload fileDownload) {

        try {

            tryStoreFileDownload(fileDownload);

        } catch (Exception e) {

            exceptionStrategy.handleException(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    protected void tryStoreFileDownload(FileDownload fileDownload) {

        MetricsDownloadServiceImpl.this.aMetricsDownloadDAO.storeFileDownload(fileDownload);
    }

    // This method is needed by Spring Integration for storing file downloads from our access logs.
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveFileDownload(FileDownloadDetails fileDownloadDetails) {

        FileDownload fileDownload = fileDownloadFactory.createFileDownload(fileDownloadDetails);

        aMetricsDownloadDAO.storeFileDownload(fileDownload);

    }
}
