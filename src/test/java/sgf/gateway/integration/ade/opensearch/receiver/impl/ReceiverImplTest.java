package sgf.gateway.integration.ade.opensearch.receiver.impl;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.junit.Test;
import sgf.gateway.integration.ade.opensearch.Feed;
import sgf.gateway.integration.ade.opensearch.receiver.Reader;
import sgf.gateway.integration.ade.opensearch.receiver.Receiver;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReceiverImplTest {

    @Test
    public void receiverRestartsAfterNullFeed() {

        Reader reader = mock(Reader.class);
        Feed feed = mock(Feed.class);

        when(reader.continuePaging()).thenReturn(true, false, true);
        when(reader.read()).thenReturn(feed, feed);

        Receiver receiver = new ReceiverImpl(reader);
        Feed initFeed = receiver.receive();
        Feed nullFeed = receiver.receive();
        Feed anewFeed = receiver.receive();

        assertThat(initFeed, IsEqual.equalTo(feed));
        assertThat(nullFeed, IsNull.nullValue());
        assertThat(anewFeed, IsEqual.equalTo(feed));
    }
}
