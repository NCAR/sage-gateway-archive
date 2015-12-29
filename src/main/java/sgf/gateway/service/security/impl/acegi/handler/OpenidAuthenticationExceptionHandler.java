package sgf.gateway.service.security.impl.acegi.handler;

import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface OpenidAuthenticationExceptionHandler {

    boolean handleException(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception);
}
