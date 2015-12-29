package sgf.gateway.integration.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorLogService {

    private final Logger log;

    public ErrorLogService(String logName) {
        super();
        this.log = LoggerFactory.getLogger(logName);
    }

    public void error(String error) {
        this.log.error(error);
    }
}
