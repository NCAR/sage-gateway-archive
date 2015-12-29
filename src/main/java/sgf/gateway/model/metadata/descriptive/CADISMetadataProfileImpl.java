package sgf.gateway.model.metadata.descriptive;

import java.util.Date;

public class CADISMetadataProfileImpl implements CADISMetadataProfile {

    /**
     * The name.
     */
    private String name;

    /**
     * The metadata version.
     */
    private String metadataVersion;

    /**
     * The creation date.
     */
    private Date creationDate;

    /**
     * The revision date.
     */
    private Date revisionDate;

    /**
     * Instantiates a new metadata profile.
     */
    protected CADISMetadataProfileImpl() {
    }

    /**
     * Instantiates a new metadata profile.
     */
    public CADISMetadataProfileImpl(String name) {
        this.name = name;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {

        return this.name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {

        this.name = name;
    }

    /**
     * Gets the metadata version.
     *
     * @return the metadata version
     */
    public String getMetadataVersion() {

        return this.metadataVersion;
    }

    /**
     * Sets the metadata version.
     *
     * @param metadataVersion the new metadata version
     */
    public void setMetadataVersion(String metadataVersion) {

        this.metadataVersion = metadataVersion;
    }

    /**
     * Gets the creation date.
     *
     * @return the creation date
     */
    public Date getCreationDate() {

        return this.creationDate;
    }

    /**
     * Sets the creation date.
     *
     * @param creationDate the new creation date
     */
    public void setCreationDate(Date creationDate) {

        this.creationDate = creationDate;
    }

    /**
     * Gets the revision date.
     *
     * @return the revision date
     */
    public Date getRevisionDate() {

        return this.revisionDate;
    }

    /**
     * Sets the revision date.
     *
     * @param revisionDate the new revision date
     */
    public void setRevisionDate(Date revisionDate) {

        this.revisionDate = revisionDate;
    }
}
