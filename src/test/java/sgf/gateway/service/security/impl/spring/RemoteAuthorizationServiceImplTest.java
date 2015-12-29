package sgf.gateway.service.security.impl.spring;

import org.junit.Before;
import org.junit.Test;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.AccountService;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RemoteAuthorizationServiceImplTest {

    private AccountService mockAccountService;
    private UserRepository mockUserRepository;

    @Before
    public void setupMocks() {

        this.mockAccountService = mock(AccountService.class);
        this.mockUserRepository = mock(UserRepository.class);
    }

    @Test
    public void testGetExistingUser() {

        RemoteAuthorizationServiceImpl remoteAuthorizationServiceImpl = new RemoteAuthorizationServiceImpl(null, null, this.mockUserRepository, null);

        User mockUser = mock(User.class);

        String openId = "";

        when(this.mockUserRepository.findUserByOpenid(openId)).thenReturn(mockUser);

        User user = remoteAuthorizationServiceImpl.getUser(openId);

        assertThat(mockUser, is(user));
    }

    @Test
    public void testGetRemoteUser() {

        RemoteAuthorizationServiceImpl remoteAuthorizationServiceImpl = new RemoteAuthorizationServiceImpl(null, this.mockAccountService, this.mockUserRepository, null);

        User mockUser = mock(User.class);

        String openId = "";

        when(this.mockUserRepository.findUserByOpenid(openId)).thenReturn(null);
        when(this.mockAccountService.registerRemoteUser(openId)).thenReturn(mockUser);

        User user = remoteAuthorizationServiceImpl.getUser(openId);

        assertThat(mockUser, is(user));
    }

}
