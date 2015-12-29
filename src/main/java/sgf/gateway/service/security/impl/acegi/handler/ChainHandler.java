package sgf.gateway.service.security.impl.acegi.handler;

import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ChainHandler implements OpenidAuthenticationExceptionHandler {

    private final List<OpenidAuthenticationExceptionHandler> handlerList;

    public ChainHandler(List<OpenidAuthenticationExceptionHandler> handlerList) {

        this.handlerList = handlerList;
    }

    @Override
    public boolean handleException(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {

        boolean handled = false;

        for (OpenidAuthenticationExceptionHandler handler : this.handlerList) {

            handled = handler.handleException(request, response, exception);

            if (handled) {

                break;
            }
        }

        return handled;
    }
}
