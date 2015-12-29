package sgf.gateway.dao.metadata;

import sgf.gateway.dao.Repository;
import sgf.gateway.model.metadata.activities.observing.PlatformType;

import java.io.Serializable;
import java.util.List;


public interface PlatformTypeRepository extends Repository<PlatformType, Serializable> {

    /**
     * Find PlatformType by name, ignoring case.
     */
    PlatformType findByName(String shortName);

    /**
     * Get all PlatformType, ordered by "shortName" property.
     */
    List<PlatformType> getAll();
}
