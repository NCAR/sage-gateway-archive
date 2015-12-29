package org.springframework.web.util.matcher;

import org.springframework.security.web.util.matcher.RequestMatcher;
import sgf.gateway.service.security.RuntimeUserService;

import javax.servlet.http.HttpServletRequest;

public class LogoutRequestMatcher implements RequestMatcher {

    private RuntimeUserService runtimeUserService;
    private RequestMatcher requestMatcher;

    public LogoutRequestMatcher(RuntimeUserService runtimeUserService, RequestMatcher requestMatcher) {

        this.runtimeUserService = runtimeUserService;
        this.requestMatcher = requestMatcher;
    }


    @Override
    public boolean matches(HttpServletRequest request) {

        boolean matches = false;

        if (this.requestMatcher.matches(request)) {

            if (this.runtimeUserService.getUser() == null) {

                matches = true;
            }
        }

        return matches;
    }
}
