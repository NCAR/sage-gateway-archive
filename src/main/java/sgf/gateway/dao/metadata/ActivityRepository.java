package sgf.gateway.dao.metadata;

import org.safehaus.uuid.UUID;
import sgf.gateway.model.metadata.activities.Activity;

public interface ActivityRepository {

    /**
     * Retrieve the activity with the given identity.
     *
     * @param activityIdentity The identity of the activity to retrieve.
     * @return The activity with the given identity or null.
     */
    Activity get(UUID activityIdentity);
}
