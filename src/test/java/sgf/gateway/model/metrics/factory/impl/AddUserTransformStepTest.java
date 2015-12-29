package sgf.gateway.model.metrics.factory.impl;

import org.junit.Before;
import org.junit.Test;
import org.safehaus.uuid.UUID;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.metrics.FileDownload;
import sgf.gateway.model.security.User;

import static org.mockito.Mockito.*;

public class AddUserTransformStepTest {

    private static final String TEST_OPENID = "http://test.com/openid/testUser";
    private static final UUID TEST_UUID = UUID.valueOf("44014a70-44b9-11e2-bcfd-0800200c9a66");
    private static final String USERNAME = "username";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String EMAIL = "email";

    private FileDownload mockFileDownload;
    private User mockUser;
    private UserRepository mockUserRepository;

    @Before
    public void setup() {

        mockFileDownload = mock(FileDownload.class);
        when(mockFileDownload.getUserOpenId()).thenReturn(TEST_OPENID);

        mockUser = mock(User.class);
        mockUserRepository = mock(UserRepository.class);
    }

    @Test
    public void getUserFromRepositoryTest() {

        when(mockUserRepository.findUserByOpenid(TEST_OPENID)).thenReturn(mockUser);


        AddUserTransformStep addUserTransformStep = new AddUserTransformStep(mockUserRepository);
        addUserTransformStep.transform(mockFileDownload);

        verify(mockUserRepository).findUserByOpenid(TEST_OPENID);
    }

    @Test
    public void fileDownloadProperlySetTest() {

        when(mockUser.getIdentifier()).thenReturn(TEST_UUID);
        when(mockUser.getUserName()).thenReturn(USERNAME);
        when(mockUser.getFirstName()).thenReturn(FIRST_NAME);
        when(mockUser.getLastName()).thenReturn(LAST_NAME);
        when(mockUser.getEmail()).thenReturn(EMAIL);


        when(mockUserRepository.findUserByOpenid(TEST_OPENID)).thenReturn(mockUser);


        AddUserTransformStep addUserTransformStep = new AddUserTransformStep(mockUserRepository);
        addUserTransformStep.transform(mockFileDownload);

        verify(mockFileDownload).setUserIdentifier(TEST_UUID);
        verify(mockFileDownload).setUserUsername(USERNAME);
        verify(mockFileDownload).setUserFirstName(FIRST_NAME);
        verify(mockFileDownload).setUserLastName(LAST_NAME);
        verify(mockFileDownload).setUserEmail(EMAIL);
    }
}
