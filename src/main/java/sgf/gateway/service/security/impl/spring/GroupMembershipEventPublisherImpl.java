package sgf.gateway.service.security.impl.spring;

import org.springframework.context.support.ApplicationObjectSupport;
import sgf.gateway.service.security.GroupMembershipEvent;
import sgf.gateway.service.security.GroupMembershipEventPublisher;

/**
 * Implementation of {@link GroupMembershipEventPublisher} specific to the Spring application context. This class will process only events that are instances of
 * {@link GroupMembershipEventImpl} and publish them to the Spring application contect.
 */
public class GroupMembershipEventPublisherImpl extends ApplicationObjectSupport implements GroupMembershipEventPublisher {

    public void publishEvent(GroupMembershipEvent event) {

        if (event instanceof GroupMembershipEventImpl) {
            this.getApplicationContext().publishEvent((GroupMembershipEventImpl) event);
        }

    }

}
