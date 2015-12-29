package sgf.gateway.service.security.impl.spring;

import org.junit.Test;
import sgf.gateway.dao.security.GroupRepository;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.security.User;
import sgf.gateway.model.security.factory.UserFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


public class AccountServiceImplTest {

    @Test
    public void testRegisterRemoteUser() {

        UserFactory mockUserFactory = mock(UserFactory.class);

        User mockUser = mock(User.class);

        when(mockUserFactory.createRemoteUser("")).thenReturn(mockUser);

        UserRepository mockUserRepository = mock(UserRepository.class);

        GroupRepository mockGroupRepository = mock(GroupRepository.class);

        AccountServiceImpl accountService = new AccountServiceImpl(mockUserFactory, null, null);
        accountService.setUserDao(mockUserRepository);
        accountService.setGroupDao(mockGroupRepository);

        User user = accountService.registerRemoteUser("");

        verify(mockUserRepository, times(1)).add(any(User.class));

        assertThat(mockUser, equalTo(user));
    }
}
