package sgf.gateway.model.metrics.factory;

import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.metrics.FileDownload;
import sgf.gateway.model.metrics.UserAgent;
import sgf.gateway.model.security.User;
import sgf.gateway.service.metrics.FileDownloadDetails;

import java.net.URI;
import java.util.Date;

public interface FileDownloadFactory {

    FileDownload createFileDownload(User user, LogicalFile logicalFile, UserAgent userAgent, Date dateStarted,
                                    Date dateCompleted, Long duration, Boolean success, String remoteAddress, Long bytesSent, URI requestURI);

    FileDownload createFileDownload(FileDownloadDetails fileDownloadDetails);
}
