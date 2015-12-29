package sgf.gateway.service.doi.impl;

import org.junit.Before;
import org.junit.Test;
import org.safehaus.uuid.UUID;
import org.springframework.context.ApplicationEventPublisher;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.service.doi.DataciteDoiRequest;
import sgf.gateway.service.doi.DoiMetadata;
import sgf.gateway.service.doi.RemoteDoiFacade;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class DoiServiceImplTest {

    private static final String MINTED_DOI_VALUE = "doi:10.5072/FK2WM1S8T";
    private static final UUID DATASET_ID = new UUID("dda98300-9f2f-11e3-a5e2-0800200c9a66");

    private StubDataciteRequest stubdataciteRequest = new StubDataciteRequest();
    private Dataset mockDataset = mock(Dataset.class);
    private RemoteDoiFacade mockRemoteDoiFacade = mock(RemoteDoiFacade.class);
    private DatasetRepository mockDatasetRepository = mock(DatasetRepository.class);
    private ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);

    @Before
    public void setup() {

        when(this.mockDatasetRepository.get(DATASET_ID)).thenReturn(mockDataset);

        when(mockRemoteDoiFacade.mintDoi(this.stubdataciteRequest)).thenReturn(MINTED_DOI_VALUE);
    }

    @Test
    public void testSettingDoiOnDataset() {

        DoiServiceImpl doiService = new DoiServiceImpl(mockRemoteDoiFacade, mockDatasetRepository);
        doiService.setApplicationEventPublisher(this.applicationEventPublisher);

        doiService.mintDoi(this.stubdataciteRequest);

        verify(mockDataset).setDOI(MINTED_DOI_VALUE);
    }

    @Test
    public void testVerifyUpdateCalledOnRemoteDoiFacade() {

        DoiServiceImpl doiService = new DoiServiceImpl(mockRemoteDoiFacade, mockDatasetRepository);

        doiService.updateDoi(this.stubdataciteRequest);

        verify(mockRemoteDoiFacade).updateDoi(stubdataciteRequest);
    }

    @Test
    public void testGetDataciteDoi() {

        DoiMetadata mockDoiMetadata = mock(DoiMetadata.class);

        when(mockRemoteDoiFacade.getDoiMetadata(MINTED_DOI_VALUE)).thenReturn(mockDoiMetadata);

        DoiServiceImpl doiService = new DoiServiceImpl(mockRemoteDoiFacade, null);

        DoiMetadata returnedDoiMetadata = doiService.getDoiMetadata(MINTED_DOI_VALUE);

        assertThat(mockDoiMetadata, is(equalTo(returnedDoiMetadata)));
    }

    public class StubDataciteRequest implements DataciteDoiRequest {

        @Override
        public UUID getDatasetIdentifier() {

            return DATASET_ID;
        }

        @Override
        public String getDoi() {

            return null;
        }

        @Override
        public String getCreator() {

            return null;
        }

        @Override
        public String getTitle() {

            return null;
        }

        @Override
        public String getPublisher() {

            return null;
        }

        @Override
        public String getPublicationYear() {

            return null;
        }

        @Override
        public String getResourceType() {

            return null;
        }

    }
}
