package sgf.gateway.model.metadata.factory.impl;

import org.springframework.util.Assert;
import sgf.gateway.dao.NewInstanceIdentifierStrategy;
import sgf.gateway.dao.impl.hibernate.VersionedUUIDIdentifier;
import sgf.gateway.dao.metadata.DataCenterRepository;
import sgf.gateway.model.Identifier;
import sgf.gateway.model.metadata.*;
import sgf.gateway.model.metadata.descriptive.DescriptiveMetadata;
import sgf.gateway.model.metadata.descriptive.DescriptiveMetadataImpl;
import sgf.gateway.model.metadata.descriptive.factory.DescriptiveMetadataFactory;
import sgf.gateway.model.metadata.factory.DatasetFactory;
import sgf.gateway.model.metadata.factory.DatasetVersionFactory;
import sgf.gateway.model.security.User;
import sgf.gateway.service.metadata.MetadataProfileStrategy;

import java.net.URI;
import java.util.Date;

public class DatasetFactoryImpl implements DatasetFactory {

    private final NewInstanceIdentifierStrategy newInstanceIdentifierStrategy;

    private final DescriptiveMetadataFactory descriptiveMetadataFactory;

    private final DatasetVersionFactory datasetVersionFactory;

    private final MetadataProfileStrategy metadataProfileStrategy;

    private final DataCenterRepository dataCenterRepository;

    public DatasetFactoryImpl(NewInstanceIdentifierStrategy newInstanceIdentifierStrategy, DescriptiveMetadataFactory descriptiveMetadataFactory,
                              DatasetVersionFactory datasetVersionFactory, MetadataProfileStrategy metadataProfileStrategy, DataCenterRepository dataCenterRepository) {

        super();
        this.newInstanceIdentifierStrategy = newInstanceIdentifierStrategy;
        this.descriptiveMetadataFactory = descriptiveMetadataFactory;
        this.datasetVersionFactory = datasetVersionFactory;
        this.metadataProfileStrategy = metadataProfileStrategy;
        this.dataCenterRepository = dataCenterRepository;
    }

    public Dataset createDataset(String title, String shortName, Dataset parent, User creator, String versionIdentifier, String versionLabel, String creationComment,
                                 Date creationDate, URI authoritativeSourceURI, Boolean brokered, String authoritativeIdentifier) {

        // For why we do asserts in Factories and not business objects...
        // See: http://misko.hevery.com/2009/02/09/to-assert-or-not-to-assert/
        Assert.hasText(title, "Title is required for all Dataset instances.");
        Assert.hasText(shortName, "ShortName is required for all Resource instances.");

        Identifier newIdentifier = this.newInstanceIdentifierStrategy.generateNewIdentifier(DatasetImpl.class);

        VersionedUUIDIdentifier vuId = (VersionedUUIDIdentifier) newIdentifier;

        DataCenter defaultDataCenter = getDefaultDataCenter();

        DatasetImpl newDataset;

        if (parent != null) {

            newDataset = new DatasetImpl(vuId.getIdentifierValue(), vuId.getVersion(), title, shortName, authoritativeIdentifier);

            parent.addChildDataset(newDataset);

        } else {

            MetadataProfile metadataProfile = this.metadataProfileStrategy.getMetadataProfileReference();

            newDataset = new DatasetImpl(vuId.getIdentifierValue(), vuId.getVersion(), title, shortName, metadataProfile, authoritativeIdentifier);
        }

        newDataset.setBrokered(brokered);

        newDataset.setContainerType(ContainerType.DATASET);

        DatasetVersion datasetVersion = this.datasetVersionFactory.create(versionIdentifier, newDataset, creator, versionLabel, creationComment, authoritativeSourceURI);

        newDataset.addDatasetVersion(datasetVersion);

        DescriptiveMetadata descriptiveMetadata = createDescriptiveMetadata(newDataset);

        newDataset.initializeDescriptiveMetadata(descriptiveMetadata);

        newDataset.setDataCenter(defaultDataCenter);

        return newDataset;
    }

    public Dataset createCadisDataset(String shortName, String title, Dataset parent, Boolean brokered) {

        String authoritativeIdentifier = null;
        Dataset dataset = this.createCadisDataset(shortName, title, parent, brokered, authoritativeIdentifier);

        return dataset;
    }

    public Dataset createCadisDataset(String shortName, String title, Dataset parent, Boolean brokered, String authoritativeIdentifier) {

        Dataset dataset = this.createCadisDataset(shortName, title, parent, brokered, authoritativeIdentifier, ContainerType.DATASET);

        return dataset;
    }

    public Dataset createCadisProject(String shortName, String title, Dataset parent, Boolean brokered, String authoritativeIdentifier) {

        Dataset dataset = this.createCadisDataset(shortName, title, parent, brokered, authoritativeIdentifier, ContainerType.PROJECT);

        return dataset;
    }

    // TODO:  Should projectGroup get set here?
    private Dataset createCadisDataset(String shortName, String title, Dataset parent, Boolean brokered, String authoritativeIdentifier, ContainerType containerType) {

        // Accessing the db via DataCenterRepository must happen before parent.addChildDataset below due to flush caused by query
        DataCenter defaultDataCenter = getDefaultDataCenter();

        // FIXME check shortName and name are not null and not blank by using Assert.hasText()?

        Identifier newIdentifier = this.newInstanceIdentifierStrategy.generateNewIdentifier(DatasetImpl.class);

        VersionedUUIDIdentifier vuId = (VersionedUUIDIdentifier) newIdentifier;

        DatasetImpl newDataset = new DatasetImpl(vuId.getIdentifierValue(), vuId.getVersion(), title, shortName, authoritativeIdentifier);

        if (null != parent) {

            parent.addChildDataset(newDataset);
        }

        newDataset.setBrokered(brokered);

        newDataset.setContainerType(containerType);

        DescriptiveMetadata descriptiveMetadata = new DescriptiveMetadataImpl(newDataset);

        newDataset.initializeDescriptiveMetadata(descriptiveMetadata);

        DatasetVersion datasetVersion = this.datasetVersionFactory.create("version identifier", newDataset, null, "first version", "Initial creation", null);

        newDataset.addDatasetVersion(datasetVersion);

        newDataset.setDataCenter(defaultDataCenter);

        return newDataset;
    }

    private DataCenter getDefaultDataCenter() {

        DataCenter defaultDataCenter = this.dataCenterRepository.getDefaultDataCenter();

        return defaultDataCenter;
    }

    private DescriptiveMetadata createDescriptiveMetadata(Dataset dataset) {

        DescriptiveMetadata descriptiveMetadata;

        if ((dataset.getMetadataProfile() != null) && (dataset.getMetadataProfile().getDescriptiveMetadataFactory() != null)) {

            descriptiveMetadata = dataset.getMetadataProfile().getDescriptiveMetadataFactory().create(dataset);

        } else {

            descriptiveMetadata = this.descriptiveMetadataFactory.create(dataset);
        }

        return descriptiveMetadata;
    }
}
