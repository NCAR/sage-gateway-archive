package sgf.gateway.model.metadata;

import sgf.gateway.model.metadata.descriptive.factory.DescriptiveMetadataFactory;

public interface MetadataProfile {

    /**
     * Gets this profile's name.
     *
     * @return the profile's name
     */
    public String getProfileName();

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setProfileName(String name);

    /**
     * Returns this profile's DescriptiveMetadataFactory.
     *
     * @return the profile's descriptive metadata factory.
     */
    public DescriptiveMetadataFactory getDescriptiveMetadataFactory();
}
