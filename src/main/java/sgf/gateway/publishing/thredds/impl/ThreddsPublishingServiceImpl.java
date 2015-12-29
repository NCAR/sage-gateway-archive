package sgf.gateway.publishing.thredds.impl;

import org.safehaus.uuid.UUID;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.event.DatasetStoredEvent;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.DatasetVersion;
import sgf.gateway.model.metadata.descriptive.DescriptiveMetadata;
import sgf.gateway.model.metadata.factory.DatasetFactory;
import sgf.gateway.model.metadata.factory.DatasetVersionFactory;
import sgf.gateway.model.security.User;
import sgf.gateway.publishing.thredds.ThreddsDataServer;
import sgf.gateway.publishing.thredds.ThreddsDatasetDetails;
import sgf.gateway.publishing.thredds.ThreddsPublishingException;
import sgf.gateway.publishing.thredds.ThreddsPublishingService;
import sgf.gateway.publishing.thredds.transform.ThreddsDatasetTransformer;
import sgf.gateway.publishing.thredds.transform.ThreddsDatasetVersionTransformer;
import sgf.gateway.publishing.thredds.transform.ThreddsDescriptiveMetadataTransformer;
import sgf.gateway.utils.FileNameAndURIRenameStrategy;
import sgf.gateway.utils.time.DateStrategy;
import thredds.catalog.InvDataset;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreddsPublishingServiceImpl implements ThreddsPublishingService, ApplicationEventPublisherAware {

    private final UserRepository userRepository;
    private final DatasetRepository datasetRepository;
    private final DatasetFactory datasetFactory;
    private final DatasetVersionFactory datasetVersionFactory;
    private final ThreddsDatasetTransformer threddsDatasetTransformer;
    private final ThreddsDescriptiveMetadataTransformer threddsDescriptiveMetadataTransformer;
    private final ThreddsDatasetVersionTransformer threddsDatasetVersionTransformer;
    private final ThreddsDataServer threddsDataServer;
    private final DateStrategy dateStrategy;
    private final FileNameAndURIRenameStrategy identifierRenameStrategy;

    private ApplicationEventPublisher applicationEventPublisher;

    public ThreddsPublishingServiceImpl(UserRepository userRepository, DatasetRepository datasetRepository, DatasetFactory datasetFactory,
                                        DatasetVersionFactory datasetVersionFactory, ThreddsDatasetTransformer threddsDatasetTransformer,
                                        ThreddsDescriptiveMetadataTransformer threddsDescriptiveMetadataTransformer, ThreddsDatasetVersionTransformer threddsDatasetVersionTransformer,
                                        ThreddsDataServer threddsDataServer, DateStrategy dateStrategy, FileNameAndURIRenameStrategy identifierRenameStrategy) {

        this.userRepository = userRepository;
        this.datasetRepository = datasetRepository;
        this.datasetFactory = datasetFactory;
        this.datasetVersionFactory = datasetVersionFactory;
        this.threddsDatasetTransformer = threddsDatasetTransformer;
        this.threddsDescriptiveMetadataTransformer = threddsDescriptiveMetadataTransformer;
        this.threddsDatasetVersionTransformer = threddsDatasetVersionTransformer;
        this.threddsDataServer = threddsDataServer;
        this.dateStrategy = dateStrategy;
        this.identifierRenameStrategy = identifierRenameStrategy;
    }

    @Override
    public Dataset publishThreddsCatalog(ThreddsDatasetDetails details) {

        Dataset dataset = tryPublishThreddsCatalog(details);

        DatasetStoredEvent event = new DatasetStoredEvent(this, dataset.getShortName());

        this.applicationEventPublisher.publishEvent(event);

        return dataset;
    }

    protected Dataset tryPublishThreddsCatalog(ThreddsDatasetDetails details) {

        Dataset dataset;

        try {

            InvDataset invDataset = this.getInvDataset(details.getAuthoritativeSourceURI());

            dataset = this.transactPublishThreddsCatalog(invDataset, details);

        } catch (Exception e) {
            throw new ThreddsPublishingException(e);
        }

        return dataset;
    }

    protected InvDataset getInvDataset(URI authoritativeSourceURI) {

        InvDataset invDataset = this.threddsDataServer.get(authoritativeSourceURI);

        return invDataset;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    protected Dataset transactPublishThreddsCatalog(InvDataset invDataset, ThreddsDatasetDetails details) {

        Dataset dataset = this.getDataset(invDataset, details);

        this.threddsDatasetTransformer.transform(invDataset, dataset);

        DescriptiveMetadata descriptiveMetadata = dataset.getDescriptiveMetadata();

        this.threddsDescriptiveMetadataTransformer.transform(invDataset, descriptiveMetadata);

        DatasetVersion datasetVersion = this.getDatasetVersion(invDataset, dataset, details);

        this.threddsDatasetVersionTransformer.transform(invDataset, datasetVersion);

        return dataset;
    }

    protected Dataset getDataset(InvDataset invDataset, ThreddsDatasetDetails details) {

        String authoritativeIdentifier = this.getAuthoritativeIdentifier(invDataset);

        Dataset dataset = this.datasetRepository.findByAuthoritativeIdentifier(authoritativeIdentifier);

        if (dataset == null) {

            dataset = this.createDataset(invDataset, details);
        }

        return dataset;
    }

    protected Dataset createDataset(InvDataset invDataset, ThreddsDatasetDetails details) {

        String shortName = this.getShortName(invDataset);

        String authoritativeIdentifier = this.getAuthoritativeIdentifier(invDataset);

        Dataset datasetParent = this.datasetRepository.getByShortName(details.getParentShortName());

        User user = this.getUser(details.getUserID());

        String datasetVersionIdentifier = this.getDatasetVersionIdentifier(invDataset);

        Date dateCreated = this.getCreationDate(invDataset);

        Dataset dataset = this.datasetFactory.createDataset(invDataset.getName(), shortName, datasetParent, user, datasetVersionIdentifier, "remove version label", "remove creation comment", dateCreated, details.getAuthoritativeSourceURI(), true, authoritativeIdentifier);

        this.datasetRepository.add(dataset);

        return dataset;
    }

    protected String getAuthoritativeIdentifier(InvDataset invDataset) {

        String authoritativeIdentifier = invDataset.getID();

        if (invDataset.getProperties() != null) {

            String datasetIdentifier = invDataset.findProperty("dataset_id");

            if (datasetIdentifier != null) {

                authoritativeIdentifier = datasetIdentifier;
            }
        }

        return authoritativeIdentifier;
    }

    protected String getShortName(InvDataset invDataset) {

        String authoritativeIdentifier = this.getAuthoritativeIdentifier(invDataset);

        String shortName = this.identifierRenameStrategy.rename(authoritativeIdentifier.trim());

        return shortName;
    }

    protected Dataset getParentDataset(String shortName) {

        Dataset parentDataset = this.datasetRepository.getByShortName(shortName);

        return parentDataset;
    }

    protected User getUser(final UUID identifier) {

        User user = this.userRepository.getUser(identifier);

        return user;
    }

    private String getDatasetVersionIdentifier(InvDataset invDataset) {

        String datasetVersionIdentifier = invDataset.findProperty("dataset_version");

        return datasetVersionIdentifier;
    }

    protected Date getCreationDate(final InvDataset invDataset) {

        Date creationDate;

        String threddsCreationTime = invDataset.findProperty("creation_time");

        if (null != threddsCreationTime) {

            try {

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                creationDate = dateFormat.parse(threddsCreationTime);

            } catch (ParseException e) {

                throw new RuntimeException(e);
            }

        } else {

            creationDate = this.dateStrategy.getDate();
        }

        return creationDate;
    }

    protected DatasetVersion getDatasetVersion(InvDataset invDataset, Dataset dataset, ThreddsDatasetDetails details) {

        String datasetVersionIdentifier = this.getDatasetVersionIdentifier(invDataset);

        DatasetVersion datasetVersion = dataset.getDatasetVersion(datasetVersionIdentifier);

        if (datasetVersion == null) {

            datasetVersion = this.createDatasetVersion(invDataset, dataset, details);

            dataset.addDatasetVersion(datasetVersion);
        }

        return datasetVersion;
    }

    protected DatasetVersion createDatasetVersion(InvDataset invDataset, Dataset dataset, ThreddsDatasetDetails details) {

        String datasetVersionIdentifier = this.getDatasetVersionIdentifier(invDataset);

        User user = this.getUser(details.getUserID());

        DatasetVersion datasetVersion = this.datasetVersionFactory.create(datasetVersionIdentifier, dataset, user, "remove version label", "remove creation comment", details.getAuthoritativeSourceURI());

        return datasetVersion;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
