package sgf.gateway.service.doi;

import org.safehaus.uuid.UUID;

public interface DataciteDoiRequest {

    UUID getDatasetIdentifier();

    String getDoi();

    String getCreator();

    String getTitle();

    String getPublisher();

    String getPublicationYear();

    String getResourceType();
}
