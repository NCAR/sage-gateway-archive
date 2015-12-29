package sgf.gateway.model.metadata.descriptive;

import java.util.Date;

public interface CADISMetadataProfile {

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName();

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name);

    /**
     * Gets the metadata version.
     *
     * @return the metadata version
     */
    public String getMetadataVersion();

    /**
     * Sets the metadata version.
     *
     * @param metadataVersion the new metadata version
     */
    public void setMetadataVersion(String metadataVersion);

    /**
     * Gets the creation date.
     *
     * @return the creation date
     */
    public Date getCreationDate();

    /**
     * Sets the creation date.
     *
     * @param creationDate the new creation date
     */
    public void setCreationDate(Date creationDate);

    /**
     * Gets the revision date.
     *
     * @return the revision date
     */
    public Date getRevisionDate();

    /**
     * Sets the revision date.
     *
     * @param revisionDate the new revision date
     */
    public void setRevisionDate(Date revisionDate);
}
