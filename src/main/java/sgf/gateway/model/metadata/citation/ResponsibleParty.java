package sgf.gateway.model.metadata.citation;

import org.safehaus.uuid.UUID;

import java.util.Date;

public interface ResponsibleParty {

    UUID getIdentifier();

    Date getDateCreated();

    Date getDateUpdated();

    String getIndividualName();

    void setIndividualName(String individualName);

    String getOrganizationName();

    void setOrganizationName(String organizationName);

    String getPositionName();

    void setPositionName(String positionName);

    String getEmail();

    void setEmail(String email);

    void setRole(ResponsiblePartyRole role);

    ResponsiblePartyRole getRole();
}
