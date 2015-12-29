package sgf.gateway.hibernate.event;

import org.hibernate.event.spi.PostDeleteEvent;
import org.junit.Test;
import org.safehaus.uuid.UUID;
import sgf.gateway.service.messaging.ExceptionHandlingService;
import sgf.gateway.service.messaging.UnhandledException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class RemoveDatasetFromDiskPostDeleteErrorHandlingWrapperTest {

    @Test
    public void exceptionHandledTest() {

        PostDeleteEvent event = mock(PostDeleteEvent.class);
        ExceptionHandlingService service = mock(ExceptionHandlingService.class);
        RemoveDatasetFromDiskPostDeleteMediator mediator = mock(RemoveDatasetFromDiskPostDeleteMediator.class);

        when(event.getId()).thenReturn(new UUID());
        doThrow(new RuntimeException()).when(mediator).onPostDelete(any(PostDeleteEvent.class));

        RemoveDatasetFromDiskPostDeleteErrorHandlingWrapper wrapper = new RemoveDatasetFromDiskPostDeleteErrorHandlingWrapper(service, mediator);

        wrapper.onPostDelete(event);

        verify(service).handledException(any(UnhandledException.class));
    }
}
