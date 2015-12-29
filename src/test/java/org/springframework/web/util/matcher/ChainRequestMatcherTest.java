package org.springframework.web.util.matcher;

import org.junit.Test;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ChainRequestMatcherTest {

    @Test
    public void matchOnFirstMatcher() {

        ChainRequestMatcher matcher = createChainRequestMatcher(true, false, false);

        assertThat(matcher.matches(null), is(true));
    }

    @Test
    public void matchOnSecondMatcher() {

        ChainRequestMatcher matcher = createChainRequestMatcher(false, true, false);

        assertThat(matcher.matches(null), is(true));
    }

    @Test
    public void matchOnThirdMatcher() {

        ChainRequestMatcher matcher = createChainRequestMatcher(false, false, true);

        assertThat(matcher.matches(null), is(true));
    }

    @Test
    public void noMatchesFound() {

        ChainRequestMatcher matcher = createChainRequestMatcher(false, false, false);

        assertThat(matcher.matches(null), is(false));
    }

    public ChainRequestMatcher createChainRequestMatcher(boolean... matches) {

        List<RequestMatcher> requestMatchers = createReqestMatcherList(matches);

        return new ChainRequestMatcher(requestMatchers);
    }

    public List<RequestMatcher> createReqestMatcherList(boolean... matches) {

        List<RequestMatcher> requestMatchers = new ArrayList<>();

        for (boolean match : matches) {
            requestMatchers.add(createFakeRequestMatcher(match));
        }

        return requestMatchers;
    }

    public RequestMatcher createFakeRequestMatcher(boolean match) {

        return new FakeRequestMatcher(match);
    }

    public class FakeRequestMatcher implements RequestMatcher {

        private boolean match;

        public FakeRequestMatcher(boolean match) {
            this.match = match;
        }

        @Override
        public boolean matches(HttpServletRequest request) {
            return this.match;
        }
    }

    ;
}
