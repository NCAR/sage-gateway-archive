package sgf.gateway.integration.ade.filter;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.integration.ade.opensearch.Entry;
import sgf.gateway.model.metadata.Dataset;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NSIDCDatasetNotBrokeredFilterTest {

    private DatasetRepository datasetRepository;
    private Entry entry;

    @Before
    public void setup() {
        datasetRepository = mock(DatasetRepository.class);
        entry = mock(Entry.class);
    }

    @Test
    public void nonMatchingDataCenterPasses() {

        when(entry.getDataCenter()).thenReturn("other");

        NSIDCDatasetNotBrokeredFilter filter = new NSIDCDatasetNotBrokeredFilter(datasetRepository, "nsidc");

        assertThat(filter.filter(entry), Is.is(true));
    }

    @Test
    public void matchingDataCenterNotFoundInRepositoryByAuthIdDoesPass() {

        when(entry.getDataCenter()).thenReturn("nsidc");
        when(entry.getDatasetId()).thenReturn("id");
        when(datasetRepository.findByAuthoritativeIdentifier("id")).thenReturn(null);

        NSIDCDatasetNotBrokeredFilter filter = new NSIDCDatasetNotBrokeredFilter(datasetRepository, "nsidc");

        assertThat(filter.filter(entry), Is.is(true));
    }

    @Test
    public void matchingDataCenterFoundInRepositoryByAuthIdDoesNotPass() {

        Dataset dataset = mock(Dataset.class);

        when(entry.getDataCenter()).thenReturn("nsidc");
        when(entry.getDatasetId()).thenReturn("id");
        when(datasetRepository.findByAuthoritativeIdentifier("id")).thenReturn(dataset);

        NSIDCDatasetNotBrokeredFilter filter = new NSIDCDatasetNotBrokeredFilter(datasetRepository, "nsidc");

        assertThat(filter.filter(entry), Is.is(false));
    }
}
