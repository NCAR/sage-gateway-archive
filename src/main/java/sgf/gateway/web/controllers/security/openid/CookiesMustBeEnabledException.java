package sgf.gateway.web.controllers.security.openid;

import org.springframework.security.core.AuthenticationException;


public class CookiesMustBeEnabledException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    public CookiesMustBeEnabledException(String msg) {
        super(msg);
    }

}
