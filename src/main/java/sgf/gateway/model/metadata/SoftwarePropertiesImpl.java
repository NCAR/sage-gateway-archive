package sgf.gateway.model.metadata;

import org.hibernate.envers.Audited;
import sgf.gateway.model.AbstractPersistableChild;

/**
 * Reference implementation of {@link SoftwareProperties}.
 */
@Audited
public class SoftwarePropertiesImpl extends AbstractPersistableChild implements SoftwareProperties {

    /**
     * The software version.
     */
    private String softwareVersion;

    /**
     * No arguments constructor for Hibernate.
     */
    protected SoftwarePropertiesImpl() {
        super();
    }

    /**
     * General constructor for creating new instances.
     *
     * @param dataset The parent dataset for the properties.
     */
    public SoftwarePropertiesImpl(Dataset dataset) {

        super(dataset);
    }

    /**
     * {@inheritDoc}
     */
    public void setSoftwareVersion(String softwareVersion) {

        this.softwareVersion = softwareVersion;
    }

    /**
     * {@inheritDoc}
     */
    public String getSoftwareVersion() {
        return softwareVersion;
    }

}
