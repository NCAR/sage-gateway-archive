package sgf.gateway.model.metrics;

import org.safehaus.uuid.UUID;
import sgf.gateway.model.AbstractPersistableEntity;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.security.User;
import sgf.gateway.service.metrics.FileDownloadDetails;

import java.io.Serializable;
import java.net.URI;
import java.util.Date;

public class FileDownload extends AbstractPersistableEntity {

    private UUID logicalFileIdentifier;
    private String logicalFileName;
    private Long logicalFileSize = 0L;
    private String logicalFileLineageIdentifier;
    private String logicalFileVersionIdentifier;

    private String fileAccessPointUri;

    private UUID userIdentifier;
    private String userUsername;
    private String userOpenId;
    private String userEmail;
    private String userFirstName;
    private String userLastName;

    private UUID userAgentIdentifier;
    private String userAgentName;

    private Date dateStarted;
    private Date dateCompleted;
    private Long duration;
    private boolean completed;
    private Long bytesSent;

    private String remoteAddress;

    private Long geocodeLatitude;
    private Long geocodeLongitude;
    private String geocodeCountry;
    private String geocodeCountryCode;
    private Date geocodeDateUpdated;
    private String geocodeCity;
    private String geocodeState;

    private String whoisOrganization;
    private Date whoisDateUpdated;

    /**
     * <p>
     * Creates a new instance of FileDownload and auto generates a new UUID.
     * </p>
     * <p>
     * Same as calling <code>new FileDownload(true)</code>.
     * </p>
     */
    protected FileDownload() {

    }

    public FileDownload(Serializable identifier, Serializable version, FileDownloadDetails fileDownloadDetails) {

        super(identifier, version);

        this.fileAccessPointUri = fileDownloadDetails.getFileURI();
        this.userOpenId = fileDownloadDetails.getOpenId();
        this.userAgentName = fileDownloadDetails.getUserAgent();

        this.dateStarted = fileDownloadDetails.getStartDate();
        this.dateCompleted = fileDownloadDetails.getEndDate();
        this.duration = fileDownloadDetails.getDuration();
        this.completed = fileDownloadDetails.getCompleted();
        this.remoteAddress = fileDownloadDetails.getRemoteAddress();
        this.bytesSent = fileDownloadDetails.getBytesSent();
    }

    public FileDownload(Serializable identifier, Serializable version, LogicalFile logicalFile, User user, UserAgent userAgent,
                        Date dateStarted, Date dateCompleted, Long duration, Boolean completed, String remoteAddress, Long bytesSent, URI requestURI) {

        super(identifier, version);

        this.logicalFileIdentifier = logicalFile.getIdentifier();
        this.logicalFileName = logicalFile.getName();
        this.logicalFileSize = logicalFile.getSize();
        this.logicalFileLineageIdentifier = logicalFile.getLineageIdentifier();
        this.logicalFileVersionIdentifier = logicalFile.getVersionIdentifier();

        this.fileAccessPointUri = requestURI.toString();

        this.userIdentifier = user.getIdentifier();
        this.userUsername = user.getUserName();
        this.userOpenId = user.getOpenid();
        this.userEmail = user.getEmail();
        this.userFirstName = user.getFirstName();
        this.userLastName = user.getLastName();

        if (userAgent != null) {
            this.userAgentIdentifier = userAgent.getIdentifier();
            this.userAgentName = userAgent.getName();
        }

        this.dateStarted = dateStarted;
        this.dateCompleted = dateCompleted;
        this.duration = duration;
        this.completed = completed;
        this.remoteAddress = remoteAddress;
        this.bytesSent = bytesSent;
    }

    public void setLogicalFileIdentifier(UUID identifier) {

        this.logicalFileIdentifier = identifier;
    }

    public UUID getLogicalFileIdentifier() {

        return this.logicalFileIdentifier;
    }

    public void setLogicalFileName(String fileName) {

        this.logicalFileName = fileName;
    }

    public String getLogicalFileName() {

        return this.logicalFileName;
    }

    public void setLogicalFileSize(Long fileSize) {

        this.logicalFileSize = fileSize;
    }

    public Long getLogicalFileSize() {

        return this.logicalFileSize;
    }

    public void setLogicalFileLineageId(String lineageIdentifier) {

        this.logicalFileLineageIdentifier = lineageIdentifier;
    }

    public String getLogicalFileLineageId() {

        return this.logicalFileLineageIdentifier;
    }

    public void setLogicalFileVersionId(String identifier) {

        this.logicalFileVersionIdentifier = identifier;
    }

    public String getLogicalFileVersionId() {

        return this.logicalFileVersionIdentifier;
    }

    public void setFileAccessPointUri(String uri) {

        this.fileAccessPointUri = uri;
    }

    public String getFileAccessPointUri() {

        return this.fileAccessPointUri;
    }

    public void setUserIdentifier(UUID identifier) {

        this.userIdentifier = identifier;
    }

    public UUID getUserIdentifier() {

        return this.userIdentifier;
    }

    public void setUserUsername(String username) {

        this.userUsername = username;
    }

    public String getUserUsername() {

        return this.userUsername;
    }

    public String getUserOpenId() {

        return this.userOpenId;
    }

    public void setUserEmail(String email) {

        this.userEmail = email;
    }

    public String getUserEmail() {

        return this.userEmail;
    }

    public void setUserFirstName(String firstName) {

        this.userFirstName = firstName;
    }

    public String getUserFirstName() {

        return this.userFirstName;
    }

    public void setUserLastName(String lastName) {

        this.userLastName = lastName;
    }

    public String getUserLastName() {

        return this.userLastName;
    }

    public void setUserAgentIdentifier(UUID identifier) {

        this.userAgentIdentifier = identifier;
    }

    public UUID getUserAgentIdentifier() {

        return this.userAgentIdentifier;
    }

    public String getUserAgentName() {

        return this.userAgentName;
    }

    public Date getDateStarted() {

        return dateStarted;
    }

    public Date getDateCompleted() {

        return this.dateCompleted;
    }

    public Long getDuration() {

        return this.duration;
    }

    public Boolean getCompleted() {

        return this.completed;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public String getRemoteAddress() {

        return this.remoteAddress;
    }

    public Long getBytesSent() {
        return bytesSent;
    }

    public void setBytesSent(Long bytesSent) {
        this.bytesSent = bytesSent;
    }

    public Long getGeocodeLatitude() {
        return geocodeLatitude;
    }

    public void setGeocodeLatitude(Long geocodeLatitude) {
        this.geocodeLatitude = geocodeLatitude;
    }

    public Long getGeocodeLongitude() {
        return geocodeLongitude;
    }

    public void setGeocodeLongitude(Long geocodeLongitude) {
        this.geocodeLongitude = geocodeLongitude;
    }

    public String getGeocodeCountry() {
        return geocodeCountry;
    }

    public void setGeocodeCountry(String geocodeCountry) {
        this.geocodeCountry = geocodeCountry;
    }

    public String getGeocodeCountryCode() {
        return geocodeCountryCode;
    }

    public void setGeocodeCountryCode(String geocodeCountryCode) {
        this.geocodeCountryCode = geocodeCountryCode;
    }

    public Date getGeocodeDateUpdated() {
        return geocodeDateUpdated;
    }

    public void setGeocodeDateUpdated(Date geocodeDateUpdated) {
        this.geocodeDateUpdated = geocodeDateUpdated;
    }

    public String getGeocode_city() {
        return geocodeCity;
    }

    public void setGeocode_city(String geocode_city) {
        this.geocodeCity = geocode_city;
    }

    public String getGeocode_state() {
        return geocodeState;
    }

    public void setGeocode_state(String geocode_state) {
        this.geocodeState = geocode_state;
    }

    public String getWhois_organization() {
        return whoisOrganization;
    }

    public void setWhois_organization(String whois_organization) {
        this.whoisOrganization = whois_organization;
    }

    public Date getWhois_date_updated() {
        return whoisDateUpdated;
    }

    public void setWhois_date_updated(Date whois_date_updated) {
        this.whoisDateUpdated = whois_date_updated;
    }
}
