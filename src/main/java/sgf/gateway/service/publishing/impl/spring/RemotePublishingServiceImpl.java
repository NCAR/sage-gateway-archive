package sgf.gateway.service.publishing.impl.spring;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import sgf.gateway.model.security.User;
import sgf.gateway.publishing.thredds.ThreddsDatasetDetails;
import sgf.gateway.publishing.thredds.ThreddsPublishingEvent;
import sgf.gateway.publishing.thredds.impl.ThreddsDatasetDetailsImpl;
import sgf.gateway.service.publishing.api.*;
import sgf.gateway.service.security.RuntimeUserService;
import sgf.gateway.utils.State;

import java.net.URI;

/**
 * RemotePublishingServiceImpl implementation.
 */
public class RemotePublishingServiceImpl implements RemotePublishingService, ApplicationEventPublisherAware {

    private RuntimeUserService runtimeUserService;

    private PublishingService publishingService;

    private ApplicationEventPublisher applicationEventPublisher;

    public RemotePublishingServiceImpl(PublishingService publishingService, RuntimeUserService runtimeUserService) {

        this.publishingService = publishingService;
        this.runtimeUserService = runtimeUserService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createDataset(final String shortName, final String authoritativeSourceURI, final Integer recurseLevel, final String initialState) throws DatasetDoesntExistException, DatasetAlreadyExistsException {

        User user = this.runtimeUserService.getUser();

        this.fireCreateDatasetEvent(user, shortName, authoritativeSourceURI);

        return shortName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPublishingStatus(String shortName) {

        return State.SUCCESSFUL.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPublishingResult(String shortName) {

        return "Your publishing request has been submitted and we will contact you by email to let you know the results shortly.";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void retractDataset(final String shortName, final String changeMessage) throws InvalidDatasetStateTransitionException, DatasetDoesntExistException {

        User user = this.runtimeUserService.getUser();

        this.publishingService.retractDataset(user, shortName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteDataset(String shortName, boolean permanent, String changeMessage) throws InvalidDatasetStateTransitionException, DatasetDoesntExistException {

        User user = this.runtimeUserService.getUser();

        this.publishingService.deleteDataset(user, shortName);
    }

    protected void fireCreateDatasetEvent(User user, String shortName, String authoritativeSourceURI) {

        ThreddsDatasetDetails details = new ThreddsDatasetDetailsImpl(URI.create(authoritativeSourceURI), shortName, user.getIdentifier());

        ThreddsPublishingEvent event = new ThreddsPublishingEvent(this, details);

        this.applicationEventPublisher.publishEvent(event);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {

        this.applicationEventPublisher = applicationEventPublisher;
    }
}
