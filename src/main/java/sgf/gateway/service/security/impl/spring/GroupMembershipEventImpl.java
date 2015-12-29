package sgf.gateway.service.security.impl.spring;

import org.springframework.context.ApplicationEvent;
import sgf.gateway.model.security.Group;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.GroupMembershipEvent;

/**
 * Implementation of {@link GroupMembershipEvent} specific to the Spring application context (i.e. instances of this class will be detected by the Spring event
 * infrastructure).
 */
public class GroupMembershipEventImpl extends ApplicationEvent implements GroupMembershipEvent {

    private User user;
    private Group group;

    /**
     * Constructor
     *
     * @param source : the object that published the event
     * @param user   : the user whose membership in the group has changed
     * @param group  : the group the membership refers to
     */
    public GroupMembershipEventImpl(Object source, User user, Group group) {
        super(source);
        this.user = user;
        this.group = group;
    }

    public User getUser() {
        return user;
    }

    public Group getGroup() {
        return group;
    }

}
