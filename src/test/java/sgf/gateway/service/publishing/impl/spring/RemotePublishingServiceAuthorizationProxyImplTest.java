package sgf.gateway.service.publishing.impl.spring;

import org.junit.Test;
import org.springframework.security.access.AccessDeniedException;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.security.Operation;
import sgf.gateway.model.security.User;
import sgf.gateway.service.publishing.api.RemotePublishingService;
import sgf.gateway.service.security.AuthorizationService;
import sgf.gateway.service.security.RuntimeUserService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.*;

public class RemotePublishingServiceAuthorizationProxyImplTest {

    @Test
    public void testCreateDataset() {

        String shortName = "parentDatasetShortName";
        String catalogURI = "http://localhost/thredds/catalog.xml";
        Integer recurseLevel = 0;
        String initialState = "PUBLISHED";

        RemotePublishingService mockRemotePublishingService = mock(RemotePublishingService.class);
        when(mockRemotePublishingService.createDataset(shortName, catalogURI, recurseLevel, initialState)).thenReturn("9b1e49e4-b444-408d-80d6-f362dde03074");

        RemotePublishingServiceAuthorizationProxyImpl remotePublishingService = new RemotePublishingServiceAuthorizationProxyImpl(mockRemotePublishingService, null, null, null) {

            protected void authorize(String shortName) {

            }
        };

        String result = remotePublishingService.createDataset(shortName, catalogURI, recurseLevel, initialState);

        assertThat(result, equalTo("9b1e49e4-b444-408d-80d6-f362dde03074"));
    }

    @Test
    public void testDeleteDataset() {

        String shortName = "parentDatasetShortName";
        boolean permanent = true;
        String changeMessage = "Message";

        RemotePublishingService mockRemotePublishingService = mock(RemotePublishingService.class);

        RemotePublishingServiceAuthorizationProxyImpl remotePublishingService = new RemotePublishingServiceAuthorizationProxyImpl(mockRemotePublishingService, null, null, null) {

            protected void authorize(String shortName) {

            }
        };

        remotePublishingService.deleteDataset(shortName, permanent, changeMessage);

        verify(mockRemotePublishingService).deleteDataset(shortName, permanent, changeMessage);
    }

    @Test
    public void testPublishingResult() {

        RemotePublishingService mockRemotePublishingService = mock(RemotePublishingService.class);
        when(mockRemotePublishingService.getPublishingResult("ac2d5bd9-aad0-404f-97d1-eb021622d645")).thenReturn("You will be sent an email when the publishing task is completed.");

        RemotePublishingServiceAuthorizationProxyImpl remotePublishingService = new RemotePublishingServiceAuthorizationProxyImpl(mockRemotePublishingService, null, null, null);

        String result = remotePublishingService.getPublishingResult("ac2d5bd9-aad0-404f-97d1-eb021622d645");

        assertThat(result, equalTo("You will be sent an email when the publishing task is completed."));
    }

    @Test
    public void testPublishingStatus() {

        RemotePublishingService mockRemotePublishingService = mock(RemotePublishingService.class);
        when(mockRemotePublishingService.getPublishingStatus("dfef15e9-6402-4a13-b221-d64d1bd53fe8")).thenReturn("SUCCESSFUL");

        RemotePublishingServiceAuthorizationProxyImpl remotePublishingService = new RemotePublishingServiceAuthorizationProxyImpl(mockRemotePublishingService, null, null, null);

        String result = remotePublishingService.getPublishingStatus("dfef15e9-6402-4a13-b221-d64d1bd53fe8");

        assertThat(result, equalTo("SUCCESSFUL"));
    }

    @Test
    public void testRetractDataset() {

        String shortName = "parentDatasetShortName";
        String changeMessage = "Message";

        RemotePublishingService mockRemotePublishingService = mock(RemotePublishingService.class);

        RemotePublishingServiceAuthorizationProxyImpl remotePublishingService = new RemotePublishingServiceAuthorizationProxyImpl(mockRemotePublishingService, null, null, null) {

            protected void authorize(String shortName) {

            }
        };

        remotePublishingService.retractDataset(shortName, changeMessage);

        verify(mockRemotePublishingService).retractDataset(shortName, changeMessage);
    }

    @Test
    public void testAuthorize() {

        User mockUser = mock(User.class);

        RuntimeUserService mockRuntimeUserService = mock(RuntimeUserService.class);
        when(mockRuntimeUserService.getUser()).thenReturn(mockUser);

        final Dataset mockDataset = mock(Dataset.class);

        AuthorizationService mockAuthorizationService = mock(AuthorizationService.class);
        when(mockAuthorizationService.authorize(mockUser, mockDataset, Operation.WRITE)).thenReturn(true);

        RemotePublishingServiceAuthorizationProxyImpl remotePublishingService = new RemotePublishingServiceAuthorizationProxyImpl(null, mockRuntimeUserService, mockAuthorizationService, null) {

            protected Dataset getDataset(String shortName) {

                return mockDataset;
            }
        };

        remotePublishingService.authorize("parentDatasetShortName");

        verify(mockAuthorizationService).authorize(mockUser, mockDataset, Operation.WRITE);
    }

    @Test(expected = AccessDeniedException.class)
    public void testNotAuthorize() {

        User mockUser = mock(User.class);

        RuntimeUserService mockRuntimeUserService = mock(RuntimeUserService.class);
        when(mockRuntimeUserService.getUser()).thenReturn(mockUser);

        final Dataset mockDataset = mock(Dataset.class);

        AuthorizationService mockAuthorizationService = mock(AuthorizationService.class);
        when(mockAuthorizationService.authorize(mockUser, mockDataset, Operation.WRITE)).thenReturn(false);

        RemotePublishingServiceAuthorizationProxyImpl remotePublishingService = new RemotePublishingServiceAuthorizationProxyImpl(null, mockRuntimeUserService, mockAuthorizationService, null) {

            protected Dataset getDataset(String shortName) {

                return mockDataset;
            }
        };

        remotePublishingService.authorize("parentDatasetShortName");
    }

    @Test
    public void testGetDataset() {

        Dataset mockDataset = mock(Dataset.class);

        DatasetRepository mockDatasetRepository = mock(DatasetRepository.class);
        when(mockDatasetRepository.getByShortName("parentDatasetShortName")).thenReturn(mockDataset);

        RemotePublishingServiceAuthorizationProxyImpl remotePublishingService = new RemotePublishingServiceAuthorizationProxyImpl(null, null, null, mockDatasetRepository);

        Dataset dataset = remotePublishingService.getDataset("parentDatasetShortName");

        assertThat(dataset, equalTo(mockDataset));
    }
}
