package sgf.gateway.service.security;

/**
 * Interface defining functionality for reacting to {@link GroupMembershipEvent}s.
 */
public interface GroupMembershipEventListener {

    void processEvent(GroupMembershipEvent event);

}
