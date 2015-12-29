package sgf.gateway.integration.filter;

import org.junit.Before;
import org.junit.Test;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.DataCenter;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.service.metadata.DatasetDetails;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class DatasetOfDatacenterFilterTest {

    @Before
    public void setup() {
    }

    @Test
    public void isOfDatacenterTest() {

        DatasetRepository repository = mock(DatasetRepository.class);
        Dataset dataset = mock(Dataset.class);
        DataCenter datacenter = mock(DataCenter.class);
        DatasetDetails details = mock(DatasetDetails.class);

        when(repository.findByAuthoritativeIdentifier("id")).thenReturn(dataset);
        when(dataset.getDataCenter()).thenReturn(datacenter);
        when(datacenter.getShortName()).thenReturn("NSIDC");
        when(details.getAuthoritativeIdentifier()).thenReturn("id");
        when(details.getDataCenterName()).thenReturn("NSIDC");

        DatasetOfDatacenterFilter filter = new DatasetOfDatacenterFilter(repository);

        assertTrue(filter.filter(details));
    }

    @Test
    public void isNotOfDatacenterTest() {

        DatasetRepository repository = mock(DatasetRepository.class);
        Dataset dataset = mock(Dataset.class);
        DataCenter datacenter = mock(DataCenter.class);
        DatasetDetails details = mock(DatasetDetails.class);

        when(repository.findByAuthoritativeIdentifier("id")).thenReturn(dataset);
        when(dataset.getDataCenter()).thenReturn(datacenter);
        when(datacenter.getShortName()).thenReturn("ACADIS");
        when(details.getAuthoritativeIdentifier()).thenReturn("id");
        when(details.getDataCenterName()).thenReturn("NSIDC");

        DatasetOfDatacenterFilter filter = new DatasetOfDatacenterFilter(repository);

        assertFalse(filter.filter(details));
    }

    @Test
    public void isDatasetDoesNotExistTest() {

        DatasetRepository repository = mock(DatasetRepository.class);
        DatasetDetails details = mock(DatasetDetails.class);

        when(repository.findByAuthoritativeIdentifier("id")).thenReturn(null);
        when(details.getAuthoritativeIdentifier()).thenReturn("id");

        DatasetOfDatacenterFilter filter = new DatasetOfDatacenterFilter(repository);

        assertTrue(filter.filter(details));
    }
}
