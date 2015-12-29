package sgf.gateway.search.service;

import org.safehaus.uuid.UUID;

public interface DeletionService {
    void deleteDatasetByShortName(String shortName);

    void delete(UUID id);

    void deleteByDatacenter(String datacenter);
}
