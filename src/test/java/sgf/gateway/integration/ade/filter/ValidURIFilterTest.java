package sgf.gateway.integration.ade.filter;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import sgf.gateway.integration.ade.opensearch.Entry;
import sgf.gateway.integration.ade.opensearch.Link;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ValidURIFilterTest {

    ValidURIFilter filter;
    private Entry mockEntry;
    private List<Link> listOfLinks = new ArrayList<Link>();
    Link mockLink;

    @Before
    public void setup() {
        mockEntry = mock(Entry.class);
        mockLink = mock(Link.class);
        filter = new ValidURIFilter();
        listOfLinks.add(mockLink);

        when(mockEntry.getLinks()).thenReturn(listOfLinks);
    }

    @Test
    public void entryWithNoLinksDoesNotPass() {

        listOfLinks.clear();

        assertThat(filter.filter(mockEntry), Is.is(false));
    }

    @Test
    public void entryWithNullHrefDoesNotPass() {

        when(mockLink.getHref()).thenReturn(null);

        assertThat(filter.filter(mockEntry), Is.is(false));
    }

    @Test
    public void entryWithEmptyHrefDoesNotPass() {

        when(mockLink.getHref()).thenReturn(" ");

        assertThat(filter.filter(mockEntry), Is.is(false));
    }

    @Test
    public void entryWithBadHrefDoesNotPass() {

        when(mockLink.getHref()).thenReturn("random text");

        assertThat(filter.filter(mockEntry), Is.is(false));
    }

    @Test
    public void entryWithBadHrefDoesNotPass2() {

        when(mockLink.getHref()).thenReturn("http://sedac.ciesin.columbia.edu/data/set/ndh-landslide-mortality-risks- distribution");

        assertThat(filter.filter(mockEntry), Is.is(false));
    }

    @Test
    public void entryWithValidHrefDoesPass() {

        when(mockLink.getHref()).thenReturn("https://localhost:8443/gateway/dataset/2011_niskin_bottlle_data_chlorophyll_nutrients.html");

        assertThat(filter.filter(mockEntry), Is.is(true));
    }

    @Test
    public void entryWithValidHrefDoesPass2() {

        when(mockLink.getHref()).thenReturn("http://foo.bar.com/");

        assertThat(filter.filter(mockEntry), Is.is(true));
    }

    @Test
    public void entryWithOneBadInListHrefDoesNotPass() {

        Link mockLink2 = mock(Link.class);

        listOfLinks.add(mockLink2);

        when(mockLink.getHref()).thenReturn("https://foo.bar.com/");
        when(mockLink2.getHref()).thenReturn("http://bad com/");

        assertThat(filter.filter(mockEntry), Is.is(false));
    }

    @Test
    public void entryWithValidListHrefDoesPass() {

        Link mockLink2 = mock(Link.class);

        listOfLinks.add(mockLink2);

        when(mockLink.getHref()).thenReturn("http://foo.bar.com/");
        when(mockLink2.getHref()).thenReturn("http://sedac.ciesin.columbia.edu/data");

        assertThat(filter.filter(mockEntry), Is.is(true));
    }
}
