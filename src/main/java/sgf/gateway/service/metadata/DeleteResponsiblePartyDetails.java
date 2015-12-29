package sgf.gateway.service.metadata;

import org.safehaus.uuid.UUID;

public interface DeleteResponsiblePartyDetails {

    UUID getDatasetIdentifier();

    UUID getResponsiblePartyIdentifier();
}
