package sgf.gateway.service.security;

import sgf.gateway.model.security.Group;
import sgf.gateway.model.security.User;

/**
 * Interface defining behavior for a group membership event.
 */
public interface GroupMembershipEvent {

    /**
     * Returns the user for which this event was generated.
     *
     * @return
     */
    User getUser();

    /**
     * Returns the group for which this event was generated.
     *
     * @return
     */
    Group getGroup();

}
