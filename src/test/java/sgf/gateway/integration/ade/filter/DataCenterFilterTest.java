package sgf.gateway.integration.ade.filter;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import sgf.gateway.integration.ade.opensearch.Entry;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class DataCenterFilterTest {

    private DataCenterFilter filter;
    private Set<String> excludedDataCenters;
    private Entry entry;

    @Before
    public void setup() {
        excludedDataCenters = new HashSet<String>();
        filter = new DataCenterFilter(excludedDataCenters);
        entry = mock(Entry.class);
    }

    @Test
    public void nullDataCenterDoesNotPass() {

        when(entry.getDataCenter()).thenReturn(null);

        assertThat(filter.filter(entry), Is.is(false));
    }

    @Test
    public void emptyDataCenterDoesNotPass() {

        when(entry.getDataCenter()).thenReturn("");

        assertThat(filter.filter(entry), Is.is(false));

    }

    @Test
    public void whitespaceDataCenterDoesNotPass() {

        when(entry.getDataCenter()).thenReturn("   ");

        assertThat(filter.filter(entry), Is.is(false));

    }

    @Test
    public void excludedDataCenterDoesNotPass() {

        excludedDataCenters.add("bad1");

        when(entry.getDataCenter()).thenReturn("bad1");

        assertThat(filter.filter(entry), Is.is(false));
    }

    @Test
    public void notExcludedDataCenterDoesPass() {

        excludedDataCenters.add("bad1");

        when(entry.getDataCenter()).thenReturn("good1");

        assertThat(filter.filter(entry), Is.is(true));
    }
}
