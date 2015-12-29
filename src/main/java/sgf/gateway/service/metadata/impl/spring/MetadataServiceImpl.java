package sgf.gateway.service.metadata.impl.spring;

import org.apache.commons.lang.StringUtils;
import org.safehaus.uuid.UUID;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.dao.metadata.MetadataRepository;
import sgf.gateway.event.DatasetStoredEvent;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.security.Operation;
import sgf.gateway.model.security.User;
import sgf.gateway.service.metadata.MetadataService;
import sgf.gateway.service.metadata.ObjectNotFoundException;

import java.util.*;

public class MetadataServiceImpl implements MetadataService, ApplicationEventPublisherAware {

    private final MetadataRepository metadataRepository;
    private final DatasetRepository datasetRepository;

    private ApplicationEventPublisher applicationEventPublisher;

    public MetadataServiceImpl(MetadataRepository metadataRepository, DatasetRepository datasetRepository) {

        super();
        this.metadataRepository = metadataRepository;
        this.datasetRepository = datasetRepository;
    }

    @Override
    public Dataset storeDataset(Dataset dataset) {

        tryUpdate(dataset);

        this.applicationEventPublisher.publishEvent(new DatasetStoredEvent(this, dataset.getShortName()));

        return dataset;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    protected void tryUpdate(Dataset dataset) {

        MetadataServiceImpl.this.metadataRepository.storeDataset(dataset);
    }

    @Override
    public void deleteDataset(Dataset dataset) {
        tryDelete(dataset);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    protected void tryDelete(Dataset dataset) {

        if (dataset.getParent() != null) {
            dataset.getParent().removeChildDataset(dataset);
        }

        datasetRepository.remove(dataset);
    }

    @Override
    public List<LogicalFile> findLogicalFileById(Collection<UUID> desiredFileIds) throws ObjectNotFoundException {

        List<LogicalFile> foundFiles = this.metadataRepository.findLogicalFileById(desiredFileIds);

        // Verify the database is consistent between logical file UUIDs and logical file locations.
        if (foundFiles.size() < desiredFileIds.size()) {

            // An Object Not Found Exception has occurred
            List<UUID> missingFileIds = getMissingFileIds(desiredFileIds, foundFiles);

            String missingIdList = StringUtils.join(missingFileIds, ',');
            missingIdList = "{" + missingIdList.replaceAll(",", ", ") + "}";
            throw new ObjectNotFoundException(missingIdList, "Could not find Logical File Ids : " + missingIdList);
        }

        Assert.isTrue(desiredFileIds.size() == foundFiles.size(), "MetadataServiceImpl.findLogicalFileById result size ("
                + foundFiles.size() + ") not equal to input list size (" + desiredFileIds.size() + ")");

        return foundFiles;
    }

    private List<UUID> getMissingFileIds(Collection<UUID> desiredFileIds, Collection<LogicalFile> foundFiles) {

        // Create "desired" and "found" sets for IDs.   Use set difference to find missing IDs.
        HashSet<UUID> desiredIds = new HashSet<>(desiredFileIds);
        HashSet<UUID> foundIds = new HashSet<>();
        for (LogicalFile file : foundFiles) {
            foundIds.add(file.getIdentifier());
        }

        // Find missing desired file IDs; convert to ArrayList
        desiredIds.removeAll(foundIds);

        return new ArrayList<>(desiredIds);
    }

    @Override
    public Set<Dataset> findDatasetsByOperation(User user, Operation operation) {

        Set<Dataset> datasets = new HashSet<>();

        // datasets authorized through direct user permission
        datasets.addAll(metadataRepository.findDatasetsByUserAndOperation(user, operation));

        // datasets authorized through user membership in a group
        datasets.addAll(metadataRepository.findDatasetsByGroupUserAndOperation(user, operation));

        return datasets;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {

        this.applicationEventPublisher = applicationEventPublisher;
    }
}
