package sgf.gateway.hibernate.event;

import org.hibernate.event.spi.PostDeleteEvent;
import org.junit.Test;
import org.safehaus.uuid.UUID;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.DatasetVersion;
import sgf.gateway.search.service.DeletionService;

import static org.mockito.Mockito.*;

public class RemoveDatasetFromSearchPostDeleteMediatorTest {

    private UUID datasetUUID = UUID.valueOf("0e79a760-614c-11e4-9803-0800200c9a66");

    @Test
    public void verifyDeletionServiceIsCalled() {


        Dataset mockDataset = mock(Dataset.class);
        when(mockDataset.getIdentifier()).thenReturn(datasetUUID);

        PostDeleteEvent mockPostDeleteEvent = mock(PostDeleteEvent.class);
        when(mockPostDeleteEvent.getEntity()).thenReturn(mockDataset);
        when(mockPostDeleteEvent.getId()).thenReturn(datasetUUID);

        DeletionService mockDeletionService = mock(DeletionService.class);

        RemoveDatasetFromSearchPostDeleteMediator mediator = new RemoveDatasetFromSearchPostDeleteMediator(mockDeletionService);
        mediator.onPostDelete(mockPostDeleteEvent);

        verify(mockDeletionService).delete(datasetUUID);
    }

    @Test
    public void verifyDeletionServiceIsNotCalled() {

        DatasetVersion mockDatasetVersion = mock(DatasetVersion.class);
        PostDeleteEvent mockPostDeleteEvent = mock(PostDeleteEvent.class);
        when(mockPostDeleteEvent.getEntity()).thenReturn(mockDatasetVersion);

        DeletionService mockDeletionService = mock(DeletionService.class);

        RemoveDatasetFromSearchPostDeleteMediator mediator = new RemoveDatasetFromSearchPostDeleteMediator(mockDeletionService);
        mediator.onPostDelete(mockPostDeleteEvent);

        verifyZeroInteractions(mockDeletionService);
    }
}
