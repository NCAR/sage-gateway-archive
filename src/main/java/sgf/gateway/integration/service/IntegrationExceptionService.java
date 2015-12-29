package sgf.gateway.integration.service;

import org.springframework.integration.MessagingException;

public interface IntegrationExceptionService {

    void handleException(MessagingException exception);

}
