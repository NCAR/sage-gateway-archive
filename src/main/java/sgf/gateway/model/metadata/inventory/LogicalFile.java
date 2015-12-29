package sgf.gateway.model.metadata.inventory;

import org.safehaus.uuid.UUID;
import sgf.gateway.model.metadata.DataFormat;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.DatasetVersion;
import sgf.gateway.model.metadata.dataaccess.FileAccessPoint;

import java.io.File;
import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

public interface LogicalFile {

    UUID getIdentifier();

    String getName();

    String getDescription();

    void setDescription(String description);

    Date getDateUpdated();

    /**
     * Determines if this resource requires authorization for read access
     *
     * @return true if this resource requires authorization for read and "is not free"
     */
    boolean isReadRestricted();

    boolean isDeletable();

    File getFile();

    String getDiskLocation();

    void setDiskLocation(String filePath);

    void associateTo(DatasetVersion datasetVersion);

    void addChecksum(String algorithmName, String value) throws IllegalArgumentException;

    String getChecksum(String algorithmName) throws IllegalArgumentException;

    void setMd5Checksum(String value) throws ChecksumMismatchException;

    String getMd5Checksum();

    Date getMd5ChecksumDate();

    void setMd5ChecksumDate(Date md5ChecksumDate);

    void removeAllChecksums();

    DataFormat getDataFormat();

    void setDataFormat(DataFormat dataFormat);

    Dataset getDataset();

    Set<FileAccessPoint> getFileAccessPoints();

    void addFileAccessPoint(FileAccessPoint fileAccessPoint);

    void removeFileAccessPoint(FileAccessPoint fileAccessPoint);

    FileAccessPoint getFileAccessPointByURI(URI uri);

    Long getSize();

    void setSize(Long newSize);

    Collection<Variable> getVariables();

    Variable findVariable(String name);

    void addVariable(Variable variable);

    void removeVariable(Variable variable);

    String getLineageIdentifier();

    String getVersionIdentifier();

    String getCMORTrackingIdentifier();

    String getLabel();
}
