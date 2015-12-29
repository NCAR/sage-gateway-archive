package sgf.gateway.service.metadata;

import org.safehaus.uuid.UUID;
import sgf.gateway.model.metadata.citation.ResponsiblePartyRole;

public interface ResponsiblePartyDetails {

    UUID getDatasetIdentifier();

    UUID getResponsiblePartyIdentifier();

    String getIndividualName();

    String getEmail();

    ResponsiblePartyRole getRole();
}
