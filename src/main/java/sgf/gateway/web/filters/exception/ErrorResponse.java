package sgf.gateway.web.filters.exception;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public interface ErrorResponse {

    void send(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception;

}
