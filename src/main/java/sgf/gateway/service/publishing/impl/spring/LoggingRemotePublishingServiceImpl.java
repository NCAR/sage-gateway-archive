package sgf.gateway.service.publishing.impl.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sgf.gateway.service.publishing.api.DatasetAlreadyExistsException;
import sgf.gateway.service.publishing.api.DatasetDoesntExistException;
import sgf.gateway.service.publishing.api.InvalidDatasetStateTransitionException;
import sgf.gateway.service.publishing.api.RemotePublishingService;

public class LoggingRemotePublishingServiceImpl implements RemotePublishingService {

    private static final Log LOG = LogFactory.getLog(LoggingRemotePublishingServiceImpl.class);

    private RemotePublishingService remotePublishingService;

    public LoggingRemotePublishingServiceImpl(RemotePublishingService remotePublishingService) {

        this.remotePublishingService = remotePublishingService;
    }

    @Override
    public String createDataset(String persistentIdentifier, String catalogURI, Integer recurseLevel, String initialState) throws DatasetDoesntExistException, DatasetAlreadyExistsException {

        LOG.trace("createDataset{persistentIdentifier: " + persistentIdentifier + ", catalogURI: " + catalogURI + ", recurseLevel: " + recurseLevel + ", initialState: " + initialState + "}");
        return this.remotePublishingService.createDataset(persistentIdentifier, catalogURI, recurseLevel, initialState);
    }

    @Override
    public void retractDataset(String persistentIdentifier, String changeMessage) throws InvalidDatasetStateTransitionException, DatasetDoesntExistException {

        LOG.trace("retractDataset{persistentIdentifier: " + persistentIdentifier + ", changeMessage: " + changeMessage + "}");
        this.remotePublishingService.retractDataset(persistentIdentifier, changeMessage);
    }

    @Override
    public void deleteDataset(String persistentIdentifier, boolean permanent, String changeMessage) throws InvalidDatasetStateTransitionException, DatasetDoesntExistException {

        LOG.trace("deleteDataset{persistentIdentifier: " + persistentIdentifier + ", changeMessage: " + changeMessage + "}");
        this.remotePublishingService.deleteDataset(persistentIdentifier, permanent, changeMessage);
    }

    @Override
    public String getPublishingStatus(String operationHandle) {

        LOG.trace("getPublishingStatus{operationHandle: " + operationHandle + "}");
        return this.remotePublishingService.getPublishingStatus(operationHandle);
    }

    @Override
    public String getPublishingResult(String operationHandle) {

        LOG.trace("getPublishingResult{operationHandle: " + operationHandle + "}");
        return this.remotePublishingService.getPublishingResult(operationHandle);
    }
}
