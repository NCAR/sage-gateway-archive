package sgf.gateway.hibernate.event;

import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.persister.entity.EntityPersister;
import sgf.gateway.service.messaging.ExceptionHandlingService;
import sgf.gateway.service.messaging.UnhandledException;

public class RemoveDatasetFromSearchPostDeleteErrorHandlingWrapper implements PostDeleteEventListener {

    private ExceptionHandlingService exceptionHandlingService;
    private PostDeleteEventListener postDeleteEventListener;

    public RemoveDatasetFromSearchPostDeleteErrorHandlingWrapper(ExceptionHandlingService exceptionHandlingService, PostDeleteEventListener postDeleteEventListener) {

        this.exceptionHandlingService = exceptionHandlingService;
        this.postDeleteEventListener = postDeleteEventListener;
    }

    @Override
    public void onPostDelete(PostDeleteEvent event) {

        try {

            this.postDeleteEventListener.onPostDelete(event);

        } catch (Exception e) {

            UnhandledException unhandledException = new UnhandledException("Unable to Delete Dataset from Solr Index", e);
            unhandledException.put("dataset identifier", event.getId().toString());

            this.exceptionHandlingService.handledException(unhandledException);
        }
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister entityPersister) {
        return false;
    }
}
