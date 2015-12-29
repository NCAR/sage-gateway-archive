package sgf.gateway.event;

import org.springframework.context.ApplicationEvent;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.security.User;

import java.net.URI;
import java.util.Date;

public class DownloadEvent extends ApplicationEvent {

    private final URI requestURI;

    private final LogicalFile logicalFile;

    private final User user;

    private final Date startDate;

    private final Date endDate;

    private final Long duration;

    private final Boolean completed;

    private final String remoteAddress;

    private final Long fileSize;

    private final String userAgent;

    private final Long bytesSent;

    public DownloadEvent(Object source, URI requestURI, User user, Date startDate,
                         Date endDate, Boolean completed, String remoteAddress, Long fileSize,
                         String userAgent, Long bytesSent, LogicalFile logicalFile) {

        super(source);
        this.requestURI = requestURI;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = endDate.getTime() - startDate.getTime();
        this.completed = completed;
        this.remoteAddress = remoteAddress;
        this.fileSize = fileSize;
        this.userAgent = userAgent;
        this.bytesSent = bytesSent;
        this.logicalFile = logicalFile;
    }

    public URI getRequestURI() {
        return requestURI;
    }

    public LogicalFile getLogicalFile() {
        return this.logicalFile;
    }

    public User getUser() {

        return this.user;
    }

    public Date getStartDate() {

        return this.startDate;
    }

    public Date getEndDate() {

        return this.endDate;
    }

    public Long getDuration() {

        return this.duration;
    }

    public Boolean getCompleted() {

        return this.completed;
    }

    public String getRemoteAddress() {

        return this.remoteAddress;
    }

    public Long getFileSize() {
        return this.fileSize;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public Long getBytesSent() {
        return bytesSent;
    }

}
