package sgf.gateway.integration.ade.opensearch.receiver;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.web.client.RestTemplate;
import sgf.gateway.integration.ade.opensearch.Feed;
import sgf.gateway.integration.ade.opensearch.Link;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class ReaderTest {

    private RestTemplate template;
    private Feed feed;
    private Link nextLink;

    @Before
    public void setup() {
        this.template = mock(RestTemplate.class);
        this.feed = mock(Feed.class);
        this.nextLink = mock(Link.class);
    }

    @Test
    public void continuePagingTrueInitially() {

        URI initialURI = URI.create("http://initial.one.org");
        Reader reader = new Reader(initialURI, this.template);

        assertThat(reader.continuePaging(), Is.is(true));
    }

    @Test
    public void continuePagingFalseIfNextLinkAbsentInFeed() {

        prepMocksFeedWithNoLinks();

        URI initialURI = URI.create("http://initial.one.org");
        Reader reader = new Reader(initialURI, this.template);

        reader.read();

        assertThat(reader.continuePaging(), Is.is(false));
    }

    @Test
    public void continuePagingTrueIfNextLinkInFeed() {

        prepMocksFeedWithNextLink();

        URI initialURI = URI.create("http://initial.one.org");
        Reader reader = new Reader(initialURI, this.template);

        reader.read();

        assertThat(reader.continuePaging(), Is.is(true));
    }

    @Test
    public void initiallyTemplateUsesInitialPageURI() {

        prepMocksFeedWithNoLinks();

        URI initialURI = URI.create("http://initial.one.org");
        Reader reader = new Reader(initialURI, this.template);

        reader.read();

        verify(this.template).getForObject(initialURI, Feed.class);
    }

    @Test
    public void afterFirstPageTemplateUsesNextPageURI() {

        prepMocksFeedWithNextLink();

        URI initialURI = URI.create("http://initial.one.org");
        Reader reader = new Reader(initialURI, this.template);

        reader.read();
        reader.read();

        ArgumentCaptor<URI> pageURI = ArgumentCaptor.forClass(URI.class);
        verify(this.template, times(2)).getForObject(pageURI.capture(), eq(Feed.class));
        assertThat(pageURI.getValue().toString(), Is.is("http://next.one.org"));
    }

    @Test
    public void afterResetTemplateUsesInitialPageURI() {

        prepMocksFeedWithNextLink();

        URI initialURI = URI.create("http://initial.one.org");
        Reader reader = new Reader(initialURI, this.template);

        reader.read();
        reader.reset();
        reader.read();

        ArgumentCaptor<URI> pageURI = ArgumentCaptor.forClass(URI.class);
        verify(this.template, times(2)).getForObject(pageURI.capture(), eq(Feed.class));
        assertThat(pageURI.getValue().toString(), Is.is("http://initial.one.org"));
    }

    private void prepMocksFeedWithNoLinks() {

        List<Link> links = new ArrayList<Link>();

        when(this.template.getForObject(any(URI.class), eq(Feed.class))).thenReturn(this.feed);
        when(this.feed.getLinks()).thenReturn(links);
    }

    private void prepMocksFeedWithNextLink() {

        List<Link> links = new ArrayList<Link>();
        links.add(this.nextLink);

        when(this.template.getForObject(any(URI.class), eq(Feed.class))).thenReturn(this.feed);
        when(this.feed.getLinks()).thenReturn(links);
        when(this.nextLink.getRel()).thenReturn("next");
        when(this.nextLink.getHref()).thenReturn("http://next.one.org");
    }
}
