package org.springframework.web.util.matcher;

import org.junit.Test;
import org.springframework.security.web.util.matcher.RequestMatcher;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.RuntimeUserService;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LogoutRequestMatcherTest {

    @Test
    public void requestMappingDoesNotMatch() {

        LogoutRequestMatcher logoutRequestMatcher = new LogoutRequestMatcher(null, new FakeRequestMatcher(false));

        assertThat(logoutRequestMatcher.matches(null), is(false));
    }

    @Test
    public void whenUserNullMatches() {

        RuntimeUserService fakeRuntimeUserService = mock(RuntimeUserService.class);
        when(fakeRuntimeUserService.getUser()).thenReturn(null);

        LogoutRequestMatcher logoutRequestMatcher = new LogoutRequestMatcher(fakeRuntimeUserService, new FakeRequestMatcher(true));

        assertThat(logoutRequestMatcher.matches(null), is(true));
    }

    @Test
    public void whenUserNotNullDoNotMatch() {

        RuntimeUserService fakeRuntimeUserService = mock(RuntimeUserService.class);
        when(fakeRuntimeUserService.getUser()).thenReturn(new User());

        LogoutRequestMatcher logoutRequestMatcher = new LogoutRequestMatcher(fakeRuntimeUserService, new FakeRequestMatcher(true));

        assertThat(logoutRequestMatcher.matches(null), is(false));
    }

    public class FakeRequestMatcher implements RequestMatcher {

        private boolean matches;

        public FakeRequestMatcher(boolean matches) {

            this.matches = matches;
        }

        @Override
        public boolean matches(HttpServletRequest request) {
            return this.matches;
        }
    }
}
