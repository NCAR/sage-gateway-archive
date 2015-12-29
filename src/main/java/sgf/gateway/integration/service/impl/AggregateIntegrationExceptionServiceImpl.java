package sgf.gateway.integration.service.impl;

import org.springframework.integration.MessagingException;
import sgf.gateway.integration.service.IntegrationExceptionService;

import java.util.List;

public class AggregateIntegrationExceptionServiceImpl implements IntegrationExceptionService {

    private final List<IntegrationExceptionService> exceptionServices;

    public AggregateIntegrationExceptionServiceImpl(List<IntegrationExceptionService> exceptionServices) {
        super();
        this.exceptionServices = exceptionServices;
    }

    @Override
    public void handleException(MessagingException exception) {

        for (IntegrationExceptionService service : exceptionServices) {
            service.handleException(exception);
        }
    }
}
