package sgf.gateway.model.metadata.dataaccess;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.safehaus.uuid.UUID;
import sgf.gateway.model.metadata.inventory.LogicalFile;

import java.io.Serializable;
import java.net.URI;

@Audited
public class FileAccessPointImpl implements FileAccessPoint {

    private Serializable identifier;

    @NotAudited
    private Integer version;

    private LogicalFile logicalFile;

    private URI endpoint;

    /**
     * No parameter constructor for hibernate.
     */
    protected FileAccessPointImpl() {

    }

    public FileAccessPointImpl(Serializable identifier, Integer version, LogicalFile logicalFile, URI endpoint) {

        this.identifier = identifier;
        this.version = version;
        this.logicalFile = logicalFile;
        this.endpoint = endpoint;

        // Attach to the logical file so the bi-directional relationship is maintained.
        logicalFile.addFileAccessPoint(this);
    }

    public URI getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(URI endpoint) {
        this.endpoint = endpoint;
    }

    public LogicalFile getLogicalFile() {

        return this.logicalFile;
    }

    /**
     * Gets the identifier.
     *
     * @return the identifier
     */
    public UUID getIdentifier() {

        return (UUID) this.identifier;
    }
}
