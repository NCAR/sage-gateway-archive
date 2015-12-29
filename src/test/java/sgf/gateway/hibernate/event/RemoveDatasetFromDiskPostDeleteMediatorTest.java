package sgf.gateway.hibernate.event;

import org.hibernate.event.spi.PostDeleteEvent;
import org.junit.Before;
import org.junit.Test;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.activities.project.Award;
import sgf.gateway.service.publishing.dataset.file.LocalFileDirectoryStrategy;
import sgf.gateway.utils.file.NonEmptyDirectoryDeleter;

import java.io.File;

import static org.mockito.Mockito.*;

public class RemoveDatasetFromDiskPostDeleteMediatorTest {

    private LocalFileDirectoryStrategy strategy;
    private NonEmptyDirectoryDeleter deleter;
    private PostDeleteEvent event;
    private RemoveDatasetFromDiskPostDeleteMediator mediator;


    @Before
    public void setup() {
        strategy = mock(LocalFileDirectoryStrategy.class);
        deleter = mock(NonEmptyDirectoryDeleter.class);
        event = mock(PostDeleteEvent.class);
        mediator = new RemoveDatasetFromDiskPostDeleteMediator(strategy, deleter);
    }

    @Test
    public void silenceIfNonDataset() {

        Award nonDataset = mock(Award.class);

        when(event.getEntity()).thenReturn(nonDataset);

        mediator.onPostDelete(event);

        verifyZeroInteractions(nonDataset);
        verifyZeroInteractions(deleter);
    }

    @Test
    public void silenceIfDatasetBrokered() {

        Dataset dataset = mock(Dataset.class);

        when(event.getEntity()).thenReturn(dataset);
        when(dataset.isBrokered()).thenReturn(true);

        mediator.onPostDelete(event);

        verifyZeroInteractions(deleter);
    }

    @Test
    public void deleteIfDatasetNotBrokered() {

        Dataset dataset = mock(Dataset.class);
        File directoryOnDisk = mock(File.class);

        when(event.getEntity()).thenReturn(dataset);
        when(dataset.isBrokered()).thenReturn(false);
        when(strategy.getDirectory(dataset)).thenReturn(directoryOnDisk);
        when(directoryOnDisk.exists()).thenReturn(true);

        mediator.onPostDelete(event);

        verify(deleter).delete(directoryOnDisk);
    }
}
