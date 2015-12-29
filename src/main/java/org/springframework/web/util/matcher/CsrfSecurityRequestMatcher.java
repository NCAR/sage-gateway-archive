package org.springframework.web.util.matcher;

import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public class CsrfSecurityRequestMatcher implements RequestMatcher {

    private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");
    private RequestMatcher requestMatcher;

    public CsrfSecurityRequestMatcher(RequestMatcher requestMatcher) {

        this.requestMatcher = requestMatcher;
    }

    @Override
    public boolean matches(HttpServletRequest httpServletRequest) {

        boolean matches = true;

        if (this.allowedMethods.matcher(httpServletRequest.getMethod()).matches()) {

            matches = false;

        } else {

            if (this.requestMatcher.matches(httpServletRequest)) {

                matches = false;
            }
        }

        return matches;
    }
}
