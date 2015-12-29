package sgf.gateway.web.filters.exception;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SimpleServletErrorResponse implements ErrorResponse {

    private String exceptionMessage;

    public SimpleServletErrorResponse(String exceptionMessage) {
        super();
        this.exceptionMessage = exceptionMessage;
    }

    public void send(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {

        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        // If the response has been committed, nothing more we can do...
        if (!httpResponse.isCommitted()) {
            httpResponse.reset();
            httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, this.exceptionMessage);
        }
    }
}
