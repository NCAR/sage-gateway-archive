package sgf.gateway.model.metadata.activities.observing;

import org.safehaus.uuid.UUID;

public interface PlatformType {

    UUID getIdentifier();

    /**
     * Gets the short name.
     *
     * @return the short name
     */
    String getShortName();

    /**
     * Sets the short name.
     *
     * @param shortName the new short name
     */
    void setShortName(String shortName);

    /**
     * Gets the long name.
     *
     * @return the long name
     */
    String getLongName();

    /**
     * Sets the long name.
     *
     * @param longName the new long name
     */
    void setLongName(String longName);
}
