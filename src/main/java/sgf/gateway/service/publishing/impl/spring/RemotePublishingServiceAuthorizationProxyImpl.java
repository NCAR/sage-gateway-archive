package sgf.gateway.service.publishing.impl.spring;

import org.springframework.security.access.AccessDeniedException;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.security.Operation;
import sgf.gateway.model.security.User;
import sgf.gateway.service.publishing.api.DatasetAlreadyExistsException;
import sgf.gateway.service.publishing.api.DatasetDoesntExistException;
import sgf.gateway.service.publishing.api.InvalidDatasetStateTransitionException;
import sgf.gateway.service.publishing.api.RemotePublishingService;
import sgf.gateway.service.security.AuthorizationService;
import sgf.gateway.service.security.RuntimeUserService;

public class RemotePublishingServiceAuthorizationProxyImpl implements RemotePublishingService {

    private final RemotePublishingService remotePublishingService;

    private final RuntimeUserService runtimeUserService;

    private final AuthorizationService authorizationService;

    private final DatasetRepository datasetRepository;

    public RemotePublishingServiceAuthorizationProxyImpl(final RemotePublishingService remotePublishingService, RuntimeUserService runtimeUserService, AuthorizationService authorizationService, DatasetRepository datasetRepository) {

        this.remotePublishingService = remotePublishingService;
        this.runtimeUserService = runtimeUserService;
        this.authorizationService = authorizationService;
        this.datasetRepository = datasetRepository;
    }

    public String createDataset(String shortName, String catalogURI, Integer recurseLevel, String initialState) throws DatasetDoesntExistException, DatasetAlreadyExistsException {

        this.authorize(shortName);

        String result = this.remotePublishingService.createDataset(shortName, catalogURI, recurseLevel, initialState);

        return result;
    }

    public void deleteDataset(String shortName, boolean permanent, String changeMessage) throws InvalidDatasetStateTransitionException, DatasetDoesntExistException {

        this.authorize(shortName);

        this.remotePublishingService.deleteDataset(shortName, permanent, changeMessage);
    }

    public String getPublishingResult(String operationHandle) {

        String result = this.remotePublishingService.getPublishingResult(operationHandle);

        return result;
    }

    public String getPublishingStatus(String operationHandle) {

        String result = this.remotePublishingService.getPublishingStatus(operationHandle);

        return result;
    }

    public void retractDataset(String shortName, String changeMessage) throws InvalidDatasetStateTransitionException, DatasetDoesntExistException {

        this.authorize(shortName);

        this.remotePublishingService.retractDataset(shortName, changeMessage);
    }

    protected void authorize(String shortName) {

        User user = this.runtimeUserService.getUser();

        Dataset dataset = this.getDataset(shortName);

        this.authorizeForWrite(user, dataset);
    }

    protected Dataset getDataset(String shortName) {

        Dataset dataset = datasetRepository.getByShortName(shortName);

        if (dataset == null) {

            throw new DatasetDoesntExistException(shortName);
        }

        return dataset;
    }

    protected void authorizeForWrite(User user, Dataset dataset) {

        boolean authorized = this.authorizationService.authorize(user, dataset, Operation.WRITE);

        if (!authorized) {

            throw new AccessDeniedException("Access is denied");
        }
    }
}
