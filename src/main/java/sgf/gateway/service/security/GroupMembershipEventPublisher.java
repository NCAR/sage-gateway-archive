package sgf.gateway.service.security;

/**
 * Interface defining functionality for generating group membership events.
 */
public interface GroupMembershipEventPublisher {

    void publishEvent(GroupMembershipEvent event);

}
