package sgf.gateway.service.publishing.impl.spring;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.event.DatasetStoredEvent;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.PublishedState;
import sgf.gateway.model.security.User;
import sgf.gateway.service.publishing.api.DatasetDoesntExistException;
import sgf.gateway.service.publishing.api.InvalidDatasetStateTransitionException;
import sgf.gateway.service.publishing.api.PublishingException;
import sgf.gateway.service.publishing.api.PublishingService;

/**
 * The Class PublishingServiceImpl.
 */
public class PublishingServiceImpl implements PublishingService, ApplicationEventPublisherAware {

    private DatasetRepository datasetRepository;

    private ApplicationEventPublisher applicationEventPublisher;

    public PublishingServiceImpl(DatasetRepository datasetRepository) {

        this.datasetRepository = datasetRepository;
    }

    /**
     * {@inheritDoc}
     */
    public void deleteDataset(User initiator, String identifier) {

        this.changeDatasetState(initiator, identifier, PublishedState.DELETED);
    }

    /**
     * {@inheritDoc}
     */
    public void retractDataset(User initiator, String identifier) {

        this.changeDatasetState(initiator, identifier, PublishedState.RETRACTED);

        this.applicationEventPublisher.publishEvent(new DatasetStoredEvent(this, identifier));
    }

    /**
     * Generic internal method to transition the Dataset to the requested state.
     */
    private void changeDatasetState(User initiator, String identifier, PublishedState newState) {

        try {
            tryChangeDatasetState(identifier, newState);
        } catch (Exception e) {
            throw new PublishingException(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    protected void tryChangeDatasetState(String identifier, PublishedState newState) {

        Dataset target = datasetRepository.getByShortName(identifier);

        if (null == target) {

            throw new DatasetDoesntExistException();
        }

        // transition dataset to requested state
        if (newState == PublishedState.RETRACTED) {
            target.getCurrentDatasetVersion().retract();
            datasetRepository.add(target);

        } else if (newState == PublishedState.DELETED) {

            if (!target.isTopLevelDataset()) {

                Dataset parentDataset = target.getParent();
                parentDataset.removeChildDataset(target);
                datasetRepository.add(parentDataset);
            }

            target.getCurrentDatasetVersion().delete();
            datasetRepository.remove(target);

        } else {
            throw new InvalidDatasetStateTransitionException();
        }
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
