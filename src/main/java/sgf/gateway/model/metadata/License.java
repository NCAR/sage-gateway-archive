package sgf.gateway.model.metadata;

import org.safehaus.uuid.UUID;

public interface License {

    UUID getIdentifier();

    String getName();

    void setName(String name);

    String getLicenseText();

    void setLicenseText(String licenseText);
}
