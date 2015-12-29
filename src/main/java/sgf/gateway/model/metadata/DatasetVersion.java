package sgf.gateway.model.metadata;

import org.safehaus.uuid.UUID;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.metadata.inventory.Variable;
import sgf.gateway.model.security.User;

import java.net.URI;
import java.util.Collection;
import java.util.Date;

public interface DatasetVersion {

    UUID getIdentifier();

    String getVersionIdentifier();

    Date getDateCreated();

    User getPublisher();

    String getComment();

    String getLabel();

    URI getAuthoritativeSourceURI();

    void setAuthoritativeSourceURI(URI authoritativeSourceURI);

    Date getAuthoritativeSourceDateCreated();

    void setAuthoritativeSourceDateCreated(Date authoritativeSourceDateCreated);

    Date getAuthoritativeSourceDateModified();

    void setAuthoritativeSourceDateModified(Date authoritativeSourceDateModified);

    Collection<Variable> getVariables();

    void addVariable(Variable variable);

    void removeVariable(Variable variable);

    Variable findVariable(String name);

    long getLogicalFileSize();

    /**
     * Gets the logical file count.
     * <p/>
     * <b>Note: </b> This method should be called rather than getLogicalFiles().size() or related methods to determine the size. This value may be calculated
     * rather that fully loading the collection just to determine how many files are attached. The number of files may be very large and thus create potentially
     * thousands of unneeded objects.
     *
     * @return the logical file count
     */
    Integer getLogicalFileCount();

    /**
     * Checks for logical files.
     * <p/>
     * <b>Note: </b> This method should be called rather than getLogicalFiles().size() or isEmpty() methods to determine the size. This value may be calculated
     * rather that fully loading the collection just to determine how many files are attached. The number of files may be very large and thus create potentially
     * thousands of unneeded objects.
     *
     * @return the boolean
     */
    boolean hasLogicalFiles();

    /**
     * Gets the logical files.
     *
     * @return the logical files
     */
    Collection<LogicalFile> getLogicalFiles();

    void addLogicalFile(LogicalFile logicalFile);

    void removeLogicalFile(LogicalFile logicalFile);

    void removeAllLogicalFiles();

    LogicalFile getLogicalFileByFileName(String fileName);

    Dataset getDataset();

    PublishedState getPublishedState();

    boolean isPrepublished();

    boolean isPublished();

    void publish();

    boolean isRetracted();

    void retract();

    boolean isDeleted();

    void delete();
}
