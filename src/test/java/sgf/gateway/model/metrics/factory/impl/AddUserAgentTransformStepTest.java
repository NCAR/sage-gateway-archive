package sgf.gateway.model.metrics.factory.impl;

import org.junit.Before;
import org.junit.Test;
import org.safehaus.uuid.UUID;
import sgf.gateway.model.metrics.FileDownload;
import sgf.gateway.model.metrics.UserAgent;
import sgf.gateway.service.metrics.UserAgentService;

import static org.mockito.Mockito.*;

public class AddUserAgentTransformStepTest {

    private static final String USER_AGENT_NAME = "userAgentName";
    private static final UUID USER_AGENT_UUID = UUID.valueOf("f5ae6870-4559-11e2-bcfd-0800200c9a66");

    private FileDownload mockFileDownload;
    private UserAgent mockUserAgent;
    private UserAgentService mockUserAgentService;

    @Before
    public void setup() {

        mockFileDownload = mock(FileDownload.class);
        when(mockFileDownload.getUserAgentName()).thenReturn(USER_AGENT_NAME);

        mockUserAgent = mock(UserAgent.class);

        mockUserAgentService = mock(UserAgentService.class);
        when(mockUserAgentService.getUserAgent(USER_AGENT_NAME)).thenReturn(mockUserAgent);
    }

    @Test
    public void getUserAgentFromServiceTest() {

        AddUserAgentTransformStep addUserAgentTransformStep = new AddUserAgentTransformStep(mockUserAgentService);
        addUserAgentTransformStep.transform(mockFileDownload);

        verify(mockUserAgentService).getUserAgent(USER_AGENT_NAME);
    }

    @Test
    public void fileDownloadProperlySetTest() {

        when(mockUserAgent.getIdentifier()).thenReturn(USER_AGENT_UUID);


        AddUserAgentTransformStep addUserAgentTransformStep = new AddUserAgentTransformStep(mockUserAgentService);
        addUserAgentTransformStep.transform(mockFileDownload);

        verify(mockFileDownload).setUserAgentIdentifier(USER_AGENT_UUID);
    }
}
