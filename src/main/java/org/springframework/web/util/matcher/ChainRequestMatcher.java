package org.springframework.web.util.matcher;

import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ChainRequestMatcher implements RequestMatcher {

    private List<RequestMatcher> requestMatcherList;

    public ChainRequestMatcher(List<RequestMatcher> requestMatcherList) {

        this.requestMatcherList = requestMatcherList;
    }

    @Override
    public boolean matches(HttpServletRequest request) {

        boolean match = false;

        for (RequestMatcher requestMatcher : this.requestMatcherList) {

            if (requestMatcher.matches(request)) {

                match = true;
                break;
            }
        }

        return match;
    }
}
