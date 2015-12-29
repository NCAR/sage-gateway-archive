package sgf.gateway.model.metadata.factory.impl;

import org.junit.Before;
import org.junit.Test;
import org.safehaus.uuid.UUID;
import sgf.gateway.dao.NewInstanceIdentifierStrategy;
import sgf.gateway.dao.impl.hibernate.VersionedUUIDIdentifier;
import sgf.gateway.dao.metadata.DataCenterRepository;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.DatasetImpl;
import sgf.gateway.model.metadata.descriptive.factory.DescriptiveMetadataFactory;
import sgf.gateway.model.metadata.factory.DatasetVersionFactory;
import sgf.gateway.service.metadata.MetadataProfileStrategy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DatasetFactoryImplTest {

    private NewInstanceIdentifierStrategy identifierStrategy = mock(NewInstanceIdentifierStrategy.class);
    private VersionedUUIDIdentifier identifier = mock(VersionedUUIDIdentifier.class);
    private DescriptiveMetadataFactory descriptiveMetadataFactory = mock(DescriptiveMetadataFactory.class);
    private DatasetVersionFactory datasetVersionFactory = mock(DatasetVersionFactory.class);
    private MetadataProfileStrategy metadataProfileStrategy = mock(MetadataProfileStrategy.class);
    private DataCenterRepository dataCenterRepository = mock(DataCenterRepository.class);
    private Dataset parent = mock(Dataset.class);

    @Before
    public void setup() {
        when(identifierStrategy.generateNewIdentifier(DatasetImpl.class)).thenReturn(identifier);
        when(identifier.getIdentifierValue()).thenReturn(UUID.valueOf("5f3934c0-7a4a-11e3-981f-0800200c9a66"));
        when(identifier.getVersion()).thenReturn(1);
    }

    @Test
    public void projectIsBrokered() {

        DatasetFactoryImpl factory = new DatasetFactoryImpl(identifierStrategy, descriptiveMetadataFactory, datasetVersionFactory, metadataProfileStrategy, dataCenterRepository);

        Dataset dataset = factory.createCadisProject("short-name", "title", parent, true, "authID");

        assertThat(dataset.isBrokered(), is(true));
    }

    @Test
    public void projectIsNotBrokered() {

        DatasetFactoryImpl factory = new DatasetFactoryImpl(identifierStrategy, descriptiveMetadataFactory, datasetVersionFactory, metadataProfileStrategy, dataCenterRepository);

        Dataset dataset = factory.createCadisProject("short-name", "title", parent, false, "authID");

        assertThat(dataset.isBrokered(), is(false));
    }
}
