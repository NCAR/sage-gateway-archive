package sgf.gateway.hibernate.event;

import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.persister.entity.EntityPersister;
import sgf.gateway.service.messaging.ExceptionHandlingService;
import sgf.gateway.service.messaging.UnhandledException;

public class RemoveDatasetFromDiskPostDeleteErrorHandlingWrapper implements PostDeleteEventListener {

    private ExceptionHandlingService service;
    private RemoveDatasetFromDiskPostDeleteMediator mediator;

    public RemoveDatasetFromDiskPostDeleteErrorHandlingWrapper(ExceptionHandlingService service,
                                                               RemoveDatasetFromDiskPostDeleteMediator mediator) {
        this.service = service;
        this.mediator = mediator;
    }

    @Override
    public void onPostDelete(PostDeleteEvent event) {

        try {
            mediator.onPostDelete(event);
        } catch (Exception e) {

            UnhandledException unhandledException = new UnhandledException("Unable to delete dataset from disk", e);
            unhandledException.put("dataset identifier", event.getId().toString());

            service.handledException(unhandledException);
        }
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister entityPersister) {
        return false;
    }
}
