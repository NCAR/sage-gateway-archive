package sgf.gateway.integration.metrics;

import sgf.gateway.service.metrics.FileDownloadDetails;

import java.io.Serializable;
import java.util.Date;

public class FileDownloadPayload implements FileDownloadDetails, Serializable {

    private static final long serialVersionUID = -8565761203935660487L;

    private final String logEntry;

    private Boolean completed;
    private Date startDate;
    private Date endDate;
    private String openId;
    private String userAgent;
    private String fileURI;
    private String remoteAddress;
    private Long bytesSent;
    private String status;

    public FileDownloadPayload(String logEntry) {
        this.logEntry = logEntry;
    }

    public String getLogEntry() {
        return this.logEntry;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    @Override
    public Boolean getCompleted() {
        return this.completed;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public Date getStartDate() {
        return this.startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public Date getEndDate() {
        return this.endDate;
    }

    @Override
    public Long getDuration() {
        Long duration = this.endDate.getTime() - this.startDate.getTime();
        return duration;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    public String getOpenId() {
        return this.openId;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    @Override
    public String getUserAgent() {
        return this.userAgent;
    }

    public void setFileURI(String fileURI) {
        this.fileURI = fileURI;
    }

    @Override
    public String getFileURI() {
        return this.fileURI;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    @Override
    public String getRemoteAddress() {
        return this.remoteAddress;
    }

    public void setBytesSent(Long bytesSent) {
        this.bytesSent = bytesSent;
    }

    @Override
    public Long getBytesSent() {
        return this.bytesSent;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    @Override
    public String toString() {

        StringBuffer sb = new StringBuffer();

        sb.append("Metrics record is as follows.\n");

        sb.append("logEntry: " + this.logEntry + "\n");
        sb.append("openId: " + this.openId + "\n");
        sb.append("fileURI: " + this.fileURI + "\n");
        sb.append("remoteAddress: " + this.remoteAddress + "\n");
        sb.append("completed: " + this.completed + "\n");
        sb.append("startDate: " + this.startDate + "\n");
        sb.append("endDate: " + this.endDate + "\n");
        sb.append("userAgent: " + this.userAgent + "\n");
        sb.append("bytesSent: " + this.bytesSent + "\n");

        return sb.toString();
    }
}