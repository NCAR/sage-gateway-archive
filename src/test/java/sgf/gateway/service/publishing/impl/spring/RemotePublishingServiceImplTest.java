package sgf.gateway.service.publishing.impl.spring;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.safehaus.uuid.UUID;
import org.springframework.context.ApplicationEventPublisher;
import sgf.gateway.model.security.User;
import sgf.gateway.publishing.thredds.ThreddsDatasetDetails;
import sgf.gateway.publishing.thredds.ThreddsPublishingEvent;
import sgf.gateway.publishing.thredds.impl.ThreddsDatasetDetailsImpl;
import sgf.gateway.service.publishing.api.PublishingService;
import sgf.gateway.service.security.RuntimeUserService;

import java.net.URI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.*;

public class RemotePublishingServiceImplTest {

    @Test
    public void testCreateDataset() {

        ApplicationEventPublisher mockApplicationEventPublisher = mock(ApplicationEventPublisher.class);

        User mockUser = mock(User.class);
        when(mockUser.getIdentifier()).thenReturn(UUID.valueOf("e6133e60-e3d0-4f95-8157-329069209692"));

        RuntimeUserService mockRuntimeUserService = mock(RuntimeUserService.class);
        when(mockRuntimeUserService.getUser()).thenReturn(mockUser);

        RemotePublishingServiceImpl remotePublishingServiceImpl = new RemotePublishingServiceImpl(null, mockRuntimeUserService);
        remotePublishingServiceImpl.setApplicationEventPublisher(mockApplicationEventPublisher);

        remotePublishingServiceImpl.createDataset("parentDatasetShortName", "http://localhost/thredds/catalog.xml", 0, "state ignored");

        ThreddsDatasetDetails details = new ThreddsDatasetDetailsImpl(URI.create("http://localhost/thredds/catalog.xml"), "parentDatasetShortName", UUID.valueOf("e6133e60-e3d0-4f95-8157-329069209692"));

        ThreddsPublishingEvent event = new ThreddsPublishingEvent(this, details);

        ArgumentCaptor<ThreddsPublishingEvent> argument = ArgumentCaptor.forClass(ThreddsPublishingEvent.class);

        verify(mockApplicationEventPublisher).publishEvent(argument.capture());

        ThreddsDatasetDetails resultingDetails = event.getThreddsDatasetDetails();

        assertThat(resultingDetails.getParentShortName(), equalTo("parentDatasetShortName"));
        assertThat(resultingDetails.getAuthoritativeSourceURI(), equalTo(URI.create("http://localhost/thredds/catalog.xml")));
        assertThat(resultingDetails.getUserID(), equalTo(UUID.valueOf("e6133e60-e3d0-4f95-8157-329069209692")));
    }

    @Test
    public void testDeleteDataset() {

        User mockUser = mock(User.class);

        RuntimeUserService mockRuntimeUserService = mock(RuntimeUserService.class);
        when(mockRuntimeUserService.getUser()).thenReturn(mockUser);

        PublishingService mockPublishingService = mock(PublishingService.class);

        RemotePublishingServiceImpl remotePublishingServiceImpl = new RemotePublishingServiceImpl(mockPublishingService, mockRuntimeUserService);

        remotePublishingServiceImpl.deleteDataset("datasetShortName", true, "message ignored");

        verify(mockPublishingService).deleteDataset(mockUser, "datasetShortName");
    }

    @Test
    public void testRetractDataset() {

        User mockUser = mock(User.class);

        RuntimeUserService mockRuntimeUserService = mock(RuntimeUserService.class);
        when(mockRuntimeUserService.getUser()).thenReturn(mockUser);

        PublishingService mockPublishingService = mock(PublishingService.class);

        RemotePublishingServiceImpl remotePublishingServiceImpl = new RemotePublishingServiceImpl(mockPublishingService, mockRuntimeUserService);

        remotePublishingServiceImpl.retractDataset("datasetShortName", "message ignored");

        verify(mockPublishingService).retractDataset(mockUser, "datasetShortName");
    }

    @Test
    public void testPublishingStatus() {

        RemotePublishingServiceImpl remotePublishingServiceImpl = new RemotePublishingServiceImpl(null, null);

        String status = remotePublishingServiceImpl.getPublishingStatus("parentDatasetShortName");

        assertThat(status, equalTo("SUCCESSFUL"));
    }

    @Test
    public void testPublishingResult() {

        RemotePublishingServiceImpl remotePublishingServiceImpl = new RemotePublishingServiceImpl(null, null);

        String result = remotePublishingServiceImpl.getPublishingResult("parentDatasetShortName");

        assertThat(result, equalTo("Your publishing request has been submitted and we will contact you by email to let you know the results shortly."));
    }
}
