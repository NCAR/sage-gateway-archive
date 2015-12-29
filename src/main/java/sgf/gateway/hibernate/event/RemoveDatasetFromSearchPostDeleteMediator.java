package sgf.gateway.hibernate.event;

import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.safehaus.uuid.UUID;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.search.service.DeletionService;

public class RemoveDatasetFromSearchPostDeleteMediator implements PostDeleteEventListener {

    private DeletionService deletionService;

    public RemoveDatasetFromSearchPostDeleteMediator(DeletionService deletionService) {

        this.deletionService = deletionService;
    }

    @Override
    public void onPostDelete(PostDeleteEvent postDeleteEvent) {

        if (postDeleteEvent.getEntity() instanceof Dataset) {

            this.deletionService.delete((UUID) postDeleteEvent.getId());
        }
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister entityPersister) {
        return false;
    }
}
