package sgf.gateway.hibernate.event;

import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.persister.entity.EntityPersister;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.service.publishing.dataset.file.LocalFileDirectoryStrategy;
import sgf.gateway.utils.file.NonEmptyDirectoryDeleter;

import java.io.File;

public class RemoveDatasetFromDiskPostDeleteMediator implements PostDeleteEventListener {

    private LocalFileDirectoryStrategy strategy;
    private NonEmptyDirectoryDeleter deleter;

    public RemoveDatasetFromDiskPostDeleteMediator(LocalFileDirectoryStrategy strategy, NonEmptyDirectoryDeleter deleter) {
        this.strategy = strategy;
        this.deleter = deleter;
    }

    @Override
    public void onPostDelete(PostDeleteEvent postDeleteEvent) {
        if (postDeleteEvent.getEntity() instanceof Dataset) {
            mediateRemovalFromDisk((Dataset) postDeleteEvent.getEntity());
        }
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister entityPersister) {
        return false;
    }

    private void mediateRemovalFromDisk(Dataset dataset) {
        if (!dataset.isBrokered()) {
            removeFromDisk(dataset);
        }
    }

    private void removeFromDisk(Dataset dataset) {
        File directory = strategy.getDirectory(dataset);
        deleter.delete(directory);
    }
}
