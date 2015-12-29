package sgf.gateway.hibernate.event;

import org.hibernate.SessionFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.event.spi.PreCollectionUpdateEventListener;
import org.hibernate.internal.SessionFactoryImpl;

import java.util.List;

public class HibernateEventRegistrar {

    private SessionFactory sessionFactory;
    private List<PostDeleteEventListener> postCommitDeleteEventListeners;
    private List<PreCollectionUpdateEventListener> preCollectionUpdateEventListeners;
    private List<PostUpdateEventListener> postUpdateEventListeners;

    public HibernateEventRegistrar(SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;
    }

    public void init() {

        EventListenerRegistry registry = ((SessionFactoryImpl) this.sessionFactory).getServiceRegistry().getService(EventListenerRegistry.class);

        for (PostDeleteEventListener postDeleteEventListener : this.postCommitDeleteEventListeners) {

            registry.getEventListenerGroup(EventType.POST_COMMIT_DELETE).appendListener(postDeleteEventListener);
        }

        for (PreCollectionUpdateEventListener listener : this.preCollectionUpdateEventListeners) {

            registry.getEventListenerGroup(EventType.PRE_COLLECTION_UPDATE).appendListener(listener);
        }

        for (PostUpdateEventListener listener : this.postUpdateEventListeners) {

            registry.getEventListenerGroup(EventType.POST_COMMIT_UPDATE).appendListener(listener);
        }
    }

    public void setPostCommitDeleteListeners(List<PostDeleteEventListener> postCommitDeleteEventListeners) {

        this.postCommitDeleteEventListeners = postCommitDeleteEventListeners;
    }


    public void setPreCollectionUpdateEventListeners(List<PreCollectionUpdateEventListener> preCollectionUpdateEventListeners) {

        this.preCollectionUpdateEventListeners = preCollectionUpdateEventListeners;
    }

    public void setPostUpdateEventListeners(List<PostUpdateEventListener> postUpdateEventListeners) {

        this.postUpdateEventListeners = postUpdateEventListeners;
    }
}
