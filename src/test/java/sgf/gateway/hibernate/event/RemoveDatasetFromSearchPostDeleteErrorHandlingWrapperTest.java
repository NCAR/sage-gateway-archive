package sgf.gateway.hibernate.event;

import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.junit.Test;
import org.safehaus.uuid.UUID;
import sgf.gateway.service.messaging.ExceptionHandlingService;
import sgf.gateway.service.messaging.UnhandledException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class RemoveDatasetFromSearchPostDeleteErrorHandlingWrapperTest {

    @Test
    public void exceptionServiceCalledOnError() {

        PostDeleteEventListener mockPostDeleteEventListener = mock(PostDeleteEventListener.class);
        doThrow(new RuntimeException("Error Occurred")).when(mockPostDeleteEventListener).onPostDelete(any(PostDeleteEvent.class));

        ExceptionHandlingService mockExceptionHandlingService = mock(ExceptionHandlingService.class);

        RemoveDatasetFromSearchPostDeleteErrorHandlingWrapper wrapper = new RemoveDatasetFromSearchPostDeleteErrorHandlingWrapper(mockExceptionHandlingService, mockPostDeleteEventListener);

        wrapper.onPostDelete(new PostDeleteEvent(null, UUID.valueOf("0e79a760-614c-11e4-9803-0800200c9a66"), null, null, null));

        verify(mockExceptionHandlingService).handledException(any(UnhandledException.class));
    }
}
