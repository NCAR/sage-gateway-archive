package sgf.gateway.web.views.dataset.file;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.AbstractView;
import sgf.gateway.event.DownloadEvent;
import sgf.gateway.event.publisher.DownloadEventPublisher;
import sgf.gateway.model.Gateway;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.security.User;
import sgf.gateway.service.messaging.ExceptionHandlingService;
import sgf.gateway.service.messaging.UnhandledException;
import sgf.gateway.service.security.RuntimeUserService;
import sgf.gateway.utils.time.DateStrategy;
import sgf.gateway.web.HttpHeaderConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipArchiveView extends AbstractView {

    private final RuntimeUserService runtimeUserService;
    private final DownloadEventPublisher downloadEventPublisher;
    private final DateStrategy dateStrategy;
    private final ExceptionHandlingService exceptionHandlingService;
    private final Gateway gateway;

    public ZipArchiveView(RuntimeUserService runtimeUserService, DownloadEventPublisher downloadEventPublisher, DateStrategy dateStrategy, ExceptionHandlingService exceptionHandlingService, Gateway gateway) {

        this.runtimeUserService = runtimeUserService;
        this.downloadEventPublisher = downloadEventPublisher;
        this.dateStrategy = dateStrategy;
        this.exceptionHandlingService = exceptionHandlingService;
        this.gateway = gateway;
    }

    @Override
    public void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Dataset dataset = (Dataset) model.get("dataset");

        Collection<LogicalFile> logicalFiles = (Collection<LogicalFile>) model.get("logicalFiles");

        response.addHeader("Content-Disposition", "attachment; filename=" + dataset.getShortName() + ".zip");

        Date dateStarted = dateStrategy.getDate();

        boolean completed = zipOutputStream(response, model);

        Date dateCompleted = dateStrategy.getDate();

        this.fireFileDownloadEvents(request, logicalFiles, completed, dateStarted, dateCompleted);
    }

    public boolean zipOutputStream(HttpServletResponse response, Map<String, Object> model) {

        boolean completed = false;

        ZipOutputStream zipStream = null;

        try {

            zipStream = new ZipOutputStream(response.getOutputStream());

            Dataset dataset = (Dataset) model.get("dataset");
            this.getIsoMetadata(zipStream, dataset);

            Collection<LogicalFile> logicalFiles = (Collection<LogicalFile>) model.get("logicalFiles");
            this.tryZipOutputStream(zipStream, logicalFiles);

            completed = true;

        } catch (Exception e) {

            UnhandledException unhandledException = new UnhandledException(e);
            this.exceptionHandlingService.handledException(unhandledException);

        } finally {

            this.closeStream(zipStream);
        }

        return completed;
    }

    public void getIsoMetadata(ZipOutputStream zipStream, Dataset dataset) {

        try {
            RestTemplate restTemplate = new RestTemplate();

            RequestCallback requestCallback = new RequestCallback() {
                public void doWithRequest(ClientHttpRequest request) {

                }
            };

            ResponseExtractor responseExtractor = new InputStreamResponseExtractor(zipStream, dataset);

            restTemplate.execute(this.gateway.getBaseSecureURL() + "dataset/" + dataset.getShortName() + ".iso19139", HttpMethod.GET, requestCallback, responseExtractor);

        } catch (Exception e) {

            UnhandledException unhandledException = new UnhandledException(e);
            ZipArchiveView.this.exceptionHandlingService.handledException(unhandledException);
        }
    }

    public void tryZipOutputStream(ZipOutputStream zipStream, Collection<LogicalFile> files) throws IOException {

        for (LogicalFile file : files) {

            ZipEntry zipEntry = new ZipEntry(file.getName());

            zipStream.putNextEntry(zipEntry);

            FileInputStream source = null;

            try {

                source = new FileInputStream(file.getFile());

                IOUtils.copy(source, zipStream);

            } finally {

                this.closeStream(source);
            }

            zipStream.closeEntry();
        }
    }

    private void closeStream(Closeable closeable) {

        if (closeable != null) {

            try {

                closeable.close();

            } catch (IOException e) {

                UnhandledException unhandledException = new UnhandledException(e);
                this.exceptionHandlingService.handledException(unhandledException);
            }
        }
    }

    public void fireFileDownloadEvents(HttpServletRequest request, Collection<LogicalFile> logicalFiles, boolean downloadCompleted, Date dateStarted, Date dateCompleted) {

        try {

            for (LogicalFile logicalFile : logicalFiles) {

                URI diskLocationURI = this.createDiskLocationURI(logicalFile);

                User user = this.runtimeUserService.getUser();

                Long fileSize = logicalFile.getSize();

                Long bytesSent = 0L;

                if (downloadCompleted) {

                    bytesSent = fileSize;
                }

                String remoteAddress = request.getRemoteAddr();

                String userAgentName = request.getHeader(HttpHeaderConstants.USER_AGENT);

                try {

                    this.downloadEventPublisher.publishDownloadEvent(new DownloadEvent(this, URI.create(request.getRequestURI()), user, dateStarted, dateCompleted, downloadCompleted, remoteAddress, fileSize, userAgentName, bytesSent, logicalFile));

                } catch (Exception e) {

                    UnhandledException unhandledException = new UnhandledException(e);
                    unhandledException.put("logicalFile.getIdentifier()", logicalFile.getIdentifier().toString());
                    unhandledException.put("logicalFile.getDiskLocation()", logicalFile.getDiskLocation());
                    unhandledException.put("diskLocationURI", diskLocationURI.toString());
                    this.exceptionHandlingService.handledException(unhandledException);
                }
            }

        } catch (Exception e) {

            UnhandledException unhandledException = new UnhandledException(e);
            this.exceptionHandlingService.handledException(unhandledException);
        }
    }

    public URI createDiskLocationURI(final LogicalFile logicalFile) throws URISyntaxException {

        String diskLocation = logicalFile.getDiskLocation();

        URI diskLocationURI = new URI("file", null, diskLocation, null);

        return diskLocationURI;
    }

    private class InputStreamResponseExtractor<Object> implements ResponseExtractor {

        private ZipOutputStream zipStream;
        private Dataset dataset;

        public InputStreamResponseExtractor(ZipOutputStream zipStream, Dataset dataset) {

            this.zipStream = zipStream;
            this.dataset = dataset;
        }

        public Object extractData(ClientHttpResponse response) {

            try {

                ZipEntry zipEntry = new ZipEntry(dataset.getShortName() + ".xml");

                zipStream.putNextEntry(zipEntry);

                IOUtils.copy(response.getBody(), zipStream);

                zipStream.closeEntry();

            } catch (Exception e) {

                UnhandledException unhandledException = new UnhandledException(e);
                ZipArchiveView.this.exceptionHandlingService.handledException(unhandledException);
            }

            return null;
        }
    }

    ;
}
