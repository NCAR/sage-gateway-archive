package sgf.gateway.service.metrics;

import java.util.Date;

public interface FileDownloadDetails {

    Boolean getCompleted();

    Date getStartDate();

    Date getEndDate();

    Long getDuration();

    String getOpenId();

    String getUserAgent();

    String getFileURI();

    String getRemoteAddress();

    Long getBytesSent();
}
