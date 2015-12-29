package sgf.gateway.model.metrics.factory.impl;

import org.springframework.util.Assert;
import sgf.gateway.dao.NewInstanceIdentifierStrategy;
import sgf.gateway.dao.impl.hibernate.VersionedUUIDIdentifier;
import sgf.gateway.model.Identifier;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.metrics.FileDownload;
import sgf.gateway.model.metrics.UserAgent;
import sgf.gateway.model.metrics.factory.FileDownloadFactory;
import sgf.gateway.model.security.User;
import sgf.gateway.service.metrics.FileDownloadDetails;

import java.net.URI;
import java.util.Date;
import java.util.List;

public class FileDownloadFactoryImpl implements FileDownloadFactory {

    private final NewInstanceIdentifierStrategy newInstanceIdentifierStrategy;

    private final List<TransformStep> transformSteps;

    public FileDownloadFactoryImpl(NewInstanceIdentifierStrategy newInstanceIdentifierStrategy, List<TransformStep> transformSteps) {

        this.newInstanceIdentifierStrategy = newInstanceIdentifierStrategy;
        this.transformSteps = transformSteps;
    }

    public FileDownload createFileDownload(User user, LogicalFile logicalFile, UserAgent userAgent, Date dateStarted,
                                           Date dateCompleted, Long duration, Boolean completed, String remoteAddress, Long bytesSent, URI requestURI) {

        Assert.notNull(user, "user must not be null.");
        Assert.notNull(dateStarted, "dateStarted must not be null.");

        Identifier newIdentifier = this.newInstanceIdentifierStrategy.generateNewIdentifier(FileDownload.class);

        VersionedUUIDIdentifier vuId = (VersionedUUIDIdentifier) newIdentifier;

        FileDownload fileDownload = new FileDownload(vuId.getIdentifierValue(), vuId.getVersion(), logicalFile, user, userAgent, dateStarted,
                dateCompleted, duration, completed, remoteAddress, bytesSent, requestURI);

        return fileDownload;
    }

    public FileDownload createFileDownload(FileDownloadDetails fileDownloadDetails) {

        Identifier newIdentifier = this.newInstanceIdentifierStrategy.generateNewIdentifier(FileDownload.class);

        VersionedUUIDIdentifier vuId = (VersionedUUIDIdentifier) newIdentifier;

        FileDownload fileDownload = new FileDownload(vuId.getIdentifierValue(), vuId.getVersion(), fileDownloadDetails);

        for (TransformStep step : this.transformSteps) {

            fileDownload = step.transform(fileDownload);
        }

        return fileDownload;
    }
}
