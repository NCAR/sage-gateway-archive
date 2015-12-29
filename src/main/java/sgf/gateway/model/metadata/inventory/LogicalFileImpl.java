package sgf.gateway.model.metadata.inventory;

import org.apache.commons.collections.collection.UnmodifiableCollection;
import org.apache.commons.collections.set.UnmodifiableSet;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.safehaus.uuid.UUID;
import org.springframework.util.StringUtils;
import sgf.gateway.model.metadata.DataFormat;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.DatasetVersion;
import sgf.gateway.model.metadata.dataaccess.FileAccessPoint;

import java.io.File;
import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Audited
public class LogicalFileImpl implements LogicalFile {

    private UUID identifier;

    /**
     * The version, provides optimistic locking support.
     * <p/>
     * Null should be the unsaved version, Does not risk collisions with scripts and database default values. Essentially built in constant that can be guarded
     * easily in the DB.
     */
    @NotAudited
    public Integer version;

    @NotAudited
    protected Date dateCreated;

    @NotAudited
    protected Date dateUpdated;

    private String name;

    private String description;

    @NotAudited
    private Set<DatasetVersion> datasetVersionsReference;

    private String md5Checksum;
    private Date md5ChecksumDateUpdated;

    private Long size = 0L;

    private Set<FileAccessPoint> fileAccessPoints = new HashSet<FileAccessPoint>();

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Set<Variable> variablesReference = new HashSet<Variable>();

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private DataFormat dataFormat;

    /**
     * The lineage identifier.
     */
    private String lineageIdentifier;

    private String versionIdentifier;

    /**
     * The cmor tracking identifier.
     */
    private String cmorTrackingIdentifier;

    private String label;

    private String diskLocation;

    /**
     * Default constructor.
     * <p/>
     * For use by Hibernate only.
     */
    protected LogicalFileImpl() {

    }

    public LogicalFileImpl(UUID identifier, Integer version, String primaryIdentifier, String lineageIdentifier,
                           String versionIdentifier, DatasetVersion datasetVersion, String name, Long size, DataFormat dataFormat,
                           String label, String cmorTrackingIdentifier) {

        this.identifier = identifier;
        this.version = version;

        this.lineageIdentifier = lineageIdentifier;
        this.versionIdentifier = versionIdentifier;

        this.datasetVersionsReference = new HashSet<DatasetVersion>();

        // FIXME: not bi-directional and does not enforce DatasetVersion state logic. -ejn

        this.datasetVersionsReference.add(datasetVersion);

        this.name = name;
        this.size = size;
        this.dataFormat = dataFormat;
        this.label = label;
        this.cmorTrackingIdentifier = cmorTrackingIdentifier;
    }

    public void associateTo(DatasetVersion datasetVersion) {
        // FIXME should make sure that datasets are consistent.
        // FIXME: note does not enforce state logic, eg only pre-published datasets
        // can be associated to, also missing bi-directional adds. - ejn
        this.getDatasetVersionsReference().add(datasetVersion);
    }

    @Override
    public boolean isDeletable() {

        boolean result = false;

        File file = this.getFile();
        if (null != file) {

            result = true;
        }

        return result;
    }

    @Override
    public File getFile() {

        File file = null;

        if (StringUtils.hasText(this.diskLocation)) {
            file = new File(this.diskLocation);
        }

        return file;
    }

    @Override
    public String getDiskLocation() {

        return this.diskLocation;
    }

    public void addChecksum(String algorithmName, String value) throws IllegalArgumentException {

        if (algorithmName.equals("MD5")) {
            setMd5Checksum(value);
        } else {
            throw new IllegalArgumentException(algorithmName);
        }
    }

    @Override
    public String getChecksum(String algorithmName) throws IllegalArgumentException {

        String value;
        if (algorithmName.equals("MD5")) {
            value = getMd5Checksum();
        } else {
            throw new IllegalArgumentException(algorithmName);
        }
        return value;
    }

    public void removeAllChecksums() {
        this.setMd5Checksum(null);
    }

    public String getMd5Checksum() {
        return md5Checksum;
    }

    public void setMd5Checksum(String md5Checksum) throws ChecksumMismatchException {

        if (StringUtils.isEmpty(this.md5Checksum)) {

            this.md5Checksum = md5Checksum;
        } else if (!this.md5Checksum.equals(md5Checksum)) {

            throw new ChecksumMismatchException(this.md5Checksum);
        }
    }

    public Date getMd5ChecksumDate() {
        return md5ChecksumDateUpdated;
    }

    public void setMd5ChecksumDate(Date md5ChecksumDate) {
        this.md5ChecksumDateUpdated = md5ChecksumDate;
    }

    public DataFormat getDataFormat() {

        return this.dataFormat;
    }

    public void setDataFormat(DataFormat dataFormat) {

        this.dataFormat = dataFormat;
    }

    protected Set<DatasetVersion> getDatasetVersionsReference() {
        return this.datasetVersionsReference;
    }

    public Dataset getDataset() {

        return this.getDatasetVersionsReference().iterator().next().getDataset();
    }

    public Set<FileAccessPoint> getFileAccessPoints() {

        return UnmodifiableSet.decorate(this.fileAccessPoints);
    }

    public void addFileAccessPoint(FileAccessPoint fileAccessPoint) {

        this.fileAccessPoints.add(fileAccessPoint);
    }

    public void removeFileAccessPoint(FileAccessPoint fileAccessPoint) {

        this.fileAccessPoints.remove(fileAccessPoint);
    }

    protected String extractFilePath(URI accessURI) {

        return accessURI.getPath();
    }

    public FileAccessPoint getFileAccessPointByURI(URI uri) {

        FileAccessPoint fileAccessPoint = null;

        Set<FileAccessPoint> fileAccessPoints = this.getFileAccessPoints();

        for (FileAccessPoint fap : fileAccessPoints) {

            URI accessURI = fap.getEndpoint();

            if (accessURI.equals(uri)) {

                fileAccessPoint = fap;
                break;
            }
        }

        return fileAccessPoint;
    }

    public boolean isReadRestricted() {

        boolean result = this.getDataset().isReadRestricted();

        return result;
    }

    public Long getSize() {

        return size;
    }

    @Override
    public void setSize(Long newSize) {

        this.size = newSize;
    }

    /**
     * Getter for the Variables member variable.
     * <p/>
     * Note: Should be called rather than direct member access for hibernate proxy compatibility.
     *
     * @return The member variable reference.
     */
    protected Set<Variable> getVariablesReference() {

        return this.variablesReference;
    }

    public Collection<Variable> getVariables() {

        return UnmodifiableCollection.decorate(this.getVariablesReference());
    }

    public Variable findVariable(String name) {

        Variable variable = this.findLocalVariable(name);

        return variable;
    }

    protected Variable findLocalVariable(String name) {

        Variable result = null;

        for (Variable variable : this.getVariablesReference()) {

            if (variable.getName().equalsIgnoreCase(name)) {

                result = variable;

                break;
            }
        }

        return result;
    }

    public void addVariable(Variable variable) {

        this.getVariablesReference().add(variable);
    }

    public void removeVariable(Variable variable) {

        this.getVariablesReference().remove(variable);
    }

    public String getLineageIdentifier() {

        return this.lineageIdentifier;
    }

    public String getVersionIdentifier() {

        return this.versionIdentifier;
    }

    public String getCMORTrackingIdentifier() {

        return this.cmorTrackingIdentifier;
    }

    public String getLabel() {

        return this.label;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {

        return this.description;
    }

    public void setDescription(final String description) {

        this.description = description;
    }

    public UUID getIdentifier() {

        return this.identifier;
    }

    public Date getDateCreated() {

        return dateCreated;
    }

    public Date getDateUpdated() {

        return dateUpdated;
    }

    @Override
    public void setDiskLocation(String diskLocation) {
        this.diskLocation = diskLocation;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof LogicalFileImpl)) {
            return false;
        }

        if (this == object) {
            return true;
        }

        LogicalFileImpl other = (LogicalFileImpl) object;

        return new EqualsBuilder()
                .append(this.getName(), other.getName())
                .append(this.getDescription(), other.getDescription())
                .append(this.getDataFormat(), other.getDataFormat())
                .append(this.getCMORTrackingIdentifier(), other.getCMORTrackingIdentifier())
                .append(this.getDiskLocation(), other.getDiskLocation())
                .append(this.getLabel(), other.getLabel())
                .append(this.getLineageIdentifier(), other.getLineageIdentifier())
                .append(this.getSize(), other.getSize())
                .append(this.getVersionIdentifier(), other.getVersionIdentifier())
                .isEquals();
    }

    @Override
    public int hashCode() {

        return new HashCodeBuilder(17, 31)
                .append(this.getName())
                .append(this.getDescription())
                .append(this.getDataFormat())
                .append(this.getCMORTrackingIdentifier())
                .append(this.getDiskLocation())
                .append(this.getLabel())
                .append(this.getLineageIdentifier())
                .append(this.getSize())
                .append(this.getVersionIdentifier())
                .toHashCode();
    }
}
