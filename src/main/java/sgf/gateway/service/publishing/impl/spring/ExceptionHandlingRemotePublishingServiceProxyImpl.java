package sgf.gateway.service.publishing.impl.spring;

import sgf.gateway.service.messaging.ExceptionHandlingService;
import sgf.gateway.service.messaging.UnhandledException;
import sgf.gateway.service.publishing.api.DatasetAlreadyExistsException;
import sgf.gateway.service.publishing.api.DatasetDoesntExistException;
import sgf.gateway.service.publishing.api.InvalidDatasetStateTransitionException;
import sgf.gateway.service.publishing.api.RemotePublishingService;

public class ExceptionHandlingRemotePublishingServiceProxyImpl implements RemotePublishingService {

    private ExceptionHandlingService exceptionhandlingService;
    private RemotePublishingService remotePublishingService;

    public ExceptionHandlingRemotePublishingServiceProxyImpl(ExceptionHandlingService exceptionHandlingService, RemotePublishingService remotePublishingService) {

        this.exceptionhandlingService = exceptionHandlingService;
        this.remotePublishingService = remotePublishingService;
    }

    @Override
    public String createDataset(String persistentIdentifier, String catalogURI, Integer recurseLevel, String initialState) throws DatasetDoesntExistException, DatasetAlreadyExistsException {

        String result;

        try {

            result = this.remotePublishingService.createDataset(persistentIdentifier, catalogURI, recurseLevel, initialState);

        } catch (RuntimeException e) {

            UnhandledException unhandledException = new UnhandledException(e);
            unhandledException.put("persistentIdentifier", persistentIdentifier);
            unhandledException.put("catalogURI", catalogURI);
            unhandledException.put("initialState", initialState);
            this.exceptionhandlingService.handledException(unhandledException);

            throw e;
        }

        return result;
    }

    @Override
    public void retractDataset(String persistentIdentifier, String changeMessage) throws InvalidDatasetStateTransitionException, DatasetDoesntExistException {

        try {

            this.remotePublishingService.retractDataset(persistentIdentifier, changeMessage);

        } catch (RuntimeException e) {

            UnhandledException unhandledException = new UnhandledException(e);
            unhandledException.put("persistentIdentifier", persistentIdentifier);
            unhandledException.put("changeMessage", changeMessage);
            this.exceptionhandlingService.handledException(unhandledException);

            throw e;
        }
    }

    @Override
    public void deleteDataset(String persistentIdentifier, boolean permanent, String changeMessage) throws InvalidDatasetStateTransitionException, DatasetDoesntExistException {

        try {

            this.remotePublishingService.deleteDataset(persistentIdentifier, permanent, changeMessage);

        } catch (RuntimeException e) {

            UnhandledException unhandledException = new UnhandledException(e);
            unhandledException.put("persistentIdentifier", persistentIdentifier);
            unhandledException.put("changeMessage", changeMessage);
            this.exceptionhandlingService.handledException(unhandledException);

            throw e;
        }
    }

    @Override
    public String getPublishingStatus(String operationHandle) {

        String result;

        try {

            result = this.remotePublishingService.getPublishingStatus(operationHandle);

        } catch (RuntimeException e) {

            UnhandledException unhandledException = new UnhandledException(e);
            this.exceptionhandlingService.handledException(unhandledException);

            throw e;
        }

        return result;
    }

    @Override
    public String getPublishingResult(String operationHandle) {

        String result;

        try {

            result = this.remotePublishingService.getPublishingStatus(operationHandle);

        } catch (RuntimeException e) {

            UnhandledException unhandledException = new UnhandledException(e);
            this.exceptionhandlingService.handledException(unhandledException);

            throw e;
        }

        return result;
    }
}
