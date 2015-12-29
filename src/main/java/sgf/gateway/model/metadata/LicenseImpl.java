package sgf.gateway.model.metadata;

import sgf.gateway.model.AbstractPersistableEntity;

import java.io.Serializable;

public class LicenseImpl extends AbstractPersistableEntity implements License {

    /**
     * The name.
     */
    private String name;

    /**
     * The license text.
     */
    private String licenseText;

    /**
     * Instantiates a new basic license.
     */
    protected LicenseImpl() {

    }

    /**
     * Instantiates a new license impl.
     *
     * @param identifier  the identifier
     * @param version     the version
     * @param name        the name
     * @param licenseText the license text
     */
    public LicenseImpl(final Serializable identifier, final Serializable version, final String name, final String licenseText) {

        super(identifier, version);
        this.name = name;
        this.licenseText = licenseText;
    }

    /**
     * {@inheritDoc}
     */
    public String getLicenseText() {

        return this.licenseText;
    }

    /**
     * {@inheritDoc}
     */
    public void setLicenseText(final String licenseText) {

        this.licenseText = licenseText;
    }

    /**
     * {@inheritDoc}
     */
    public String getName() {

        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    public void setName(final String name) {

        this.name = name;
    }
}
