package sgf.gateway.service.metadata.impl.spring;

import org.safehaus.uuid.UUID;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sgf.gateway.dao.metadata.DataCenterRepository;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.dao.security.GroupRepository;
import sgf.gateway.event.DatasetStoredEvent;
import sgf.gateway.model.metadata.DataCenter;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.descriptive.DescriptiveMetadata;
import sgf.gateway.model.metadata.factory.DatasetFactory;
import sgf.gateway.model.security.Group;
import sgf.gateway.model.security.Operation;
import sgf.gateway.service.metadata.CadisProjectService;
import sgf.gateway.service.metadata.ProjectRequest;
import sgf.gateway.utils.FileNameAndURIRenameStrategy;

public class CadisProjectServiceImpl implements CadisProjectService, ApplicationEventPublisherAware {

    private final DatasetRepository datasetRepository;
    private final DatasetFactory datasetFactory;
    private final GroupRepository groupRepository;
    private final FileNameAndURIRenameStrategy identifierRenameStrategy;
    private final DataCenterRepository dataCenterRepository;

    private ApplicationEventPublisher applicationEventPublisher;

    public CadisProjectServiceImpl(DatasetRepository datasetRepository, DatasetFactory datasetFactory,
                                   GroupRepository groupRepository, FileNameAndURIRenameStrategy identifierRenameStrategy, DataCenterRepository dataCenterRepository) {

        this.datasetRepository = datasetRepository;
        this.datasetFactory = datasetFactory;
        this.groupRepository = groupRepository;
        this.identifierRenameStrategy = identifierRenameStrategy;
        this.dataCenterRepository = dataCenterRepository;
    }

    @Override
    public Dataset save(final ProjectRequest details) {

        Dataset result = trySave(details);

        fireDatasetStoredEvent(result.getShortName());

        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    protected Dataset trySave(final ProjectRequest details) {

        String shortName = details.getShortName();

        if (shortName != null) {

            shortName = this.identifierRenameStrategy.rename(shortName);
        } else {

            shortName = this.identifierRenameStrategy.rename(details.getTitle());
        }

        Dataset dataset = this.datasetFactory.createCadisProject(shortName, details.getTitle(), null, details.isBrokered(), details.getAuthoritativeIdentifier());

        dataset.setDescription(details.getDescription());

        if (details.getDataCenterName() != null) {
            DataCenter dataCenter = this.getOverrideDataCenter(details);
            dataset.setDataCenter(dataCenter);
        }

        DescriptiveMetadata metadata = dataset.getDescriptiveMetadata();
        metadata.setGeographicBoundingBox(details.getWesternLongitude(), details.getEasternLongitude(),
                details.getSouthernLatitude(), details.getNorthernLatitude());
        metadata.setTimePeriod(details.getStartDate(), details.getEndDate());

        dataset.setProjectGroup(details.getProjectGroup());

        Group rootGroup = this.groupRepository.findGroupByName(Group.GROUP_ROOT);
        Group guestGroup = this.groupRepository.findGroupByName(Group.GROUP_GUEST);

        dataset.addPermission(rootGroup, Operation.WRITE);
        dataset.addPermission(guestGroup, Operation.READ);

        dataset.getCurrentDatasetVersion().publish();

        this.datasetRepository.add(dataset);

        return dataset;
    }

    @Override
    public Dataset update(final UUID identifier, final ProjectRequest request) {

        Dataset result = tryUpdate(identifier, request);

        fireDatasetStoredEvent(result.getShortName());

        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    protected Dataset tryUpdate(final UUID identifier, final ProjectRequest request) {

        Dataset dataset = this.datasetRepository.get(identifier);

        //String shortName = this.identifierRenameStrategy.rename(request.getTitle());
        String shortName = request.getShortName();
        dataset.setShortName(shortName);

        dataset.setTitle(request.getTitle());
        dataset.setDescription(request.getDescription());
        dataset.setProjectGroup(request.getProjectGroup());

        DescriptiveMetadata metadata = dataset.getDescriptiveMetadata();
        metadata.setGeographicBoundingBox(Double.valueOf(request.getWesternLongitude()), Double.valueOf(request.getEasternLongitude()), Double.valueOf(request.getSouthernLatitude()), Double.valueOf(request.getNorthernLatitude()));
        metadata.setTimePeriod(request.getStartDate(), request.getEndDate());

        return dataset;
    }

    @Override
    public Dataset update(final ProjectRequest details) {

        Dataset result = tryUpdate(details);

        fireDatasetStoredEvent(result.getShortName());

        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    protected Dataset tryUpdate(final ProjectRequest details) {

        String shortName = details.getShortName();

        if (shortName != null) {

            shortName = this.identifierRenameStrategy.rename(shortName);

        } else {

            shortName = this.identifierRenameStrategy.rename(details.getShortName());
        }

        Dataset dataset = this.datasetRepository.getProjectByShortName(shortName);

        dataset.setTitle(details.getTitle());
        dataset.setDescription(details.getDescription());

        DescriptiveMetadata metadata = dataset.getDescriptiveMetadata();
        metadata.setGeographicBoundingBox(Double.valueOf(details.getWesternLongitude()), Double.valueOf(details.getEasternLongitude()), Double.valueOf(details.getSouthernLatitude()), Double.valueOf(details.getNorthernLatitude()));
        metadata.setTimePeriod(details.getStartDate(), details.getEndDate());

        return dataset;
    }

    // TODO:  What to use if shortName is null?  Exception?
    @Override
    public Dataset saveOrUpdate(final ProjectRequest details) {

        Dataset dataset;

        String shortName = details.getShortName();

        if (shortName != null) {
            shortName = this.identifierRenameStrategy.rename(shortName);
        } else {
            shortName = this.identifierRenameStrategy.rename(details.getTitle());
        }

        if (projectExists(shortName)) {
            dataset = update(details);
        } else {
            dataset = save(details);
        }

        return dataset;
    }

    private Boolean projectExists(String shortName) {

        Boolean exists = false;
        Dataset dataset = findProject(shortName);

        if (dataset != null) {
            exists = true;
        }

        return exists;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private Dataset findProject(final String shortName) {

        Dataset dataset = this.datasetRepository.getProjectByShortName(shortName);

        return dataset;
    }

    private DataCenter getOverrideDataCenter(final ProjectRequest details) {

        String dataCenterName = details.getDataCenterName();
        DataCenter dataCenter = this.dataCenterRepository.getByName(dataCenterName);

        return dataCenter;
    }

    private void fireDatasetStoredEvent(String shortName) {
        this.applicationEventPublisher.publishEvent(new DatasetStoredEvent(this, shortName));
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
