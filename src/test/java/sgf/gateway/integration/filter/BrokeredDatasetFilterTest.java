package sgf.gateway.integration.filter;

import org.junit.Before;
import org.junit.Test;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.service.metadata.DatasetDetails;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class BrokeredDatasetFilterTest {

    @Before
    public void setup() {
    }

    @Test
    public void passIfDatasetDoesNotExistTest() {

        DatasetRepository repository = mock(DatasetRepository.class);
        DatasetDetails details = mock(DatasetDetails.class);

        when(repository.findByAuthoritativeIdentifier("id")).thenReturn(null);
        when(details.getAuthoritativeIdentifier()).thenReturn("id");

        BrokeredDatasetFilter filter = new BrokeredDatasetFilter(repository);

        assertTrue(filter.filter(details));
    }

    @Test
    public void doNotPassIfDatasetIsLocal() {

        DatasetRepository repository = mock(DatasetRepository.class);
        Dataset dataset = mock(Dataset.class);
        DatasetDetails details = mock(DatasetDetails.class);

        when(repository.findByAuthoritativeIdentifier("id")).thenReturn(dataset);
        when(details.getAuthoritativeIdentifier()).thenReturn("id");
        when(dataset.isBrokered()).thenReturn(false);

        BrokeredDatasetFilter filter = new BrokeredDatasetFilter(repository);

        assertFalse(filter.filter(details));
    }

    @Test
    public void passIfDatasetIsBrokered() {

        DatasetRepository repository = mock(DatasetRepository.class);
        Dataset dataset = mock(Dataset.class);
        DatasetDetails details = mock(DatasetDetails.class);

        when(repository.findByAuthoritativeIdentifier("id")).thenReturn(dataset);
        when(details.getAuthoritativeIdentifier()).thenReturn("id");
        when(dataset.isBrokered()).thenReturn(true);

        BrokeredDatasetFilter filter = new BrokeredDatasetFilter(repository);

        assertTrue(filter.filter(details));
    }
}
