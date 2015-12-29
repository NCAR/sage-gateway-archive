package sgf.gateway.search.extract.dataset;

import org.hamcrest.core.Is;
import org.junit.Test;
import sgf.gateway.model.metadata.Dataset;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class DatasetDOIExtractorTest {

    @Test
    public void testExtractor() {
        Dataset dataset = mock(Dataset.class);
        DatasetDOIExtractor extractor = new DatasetDOIExtractor();

        when(dataset.getDOI()).thenReturn("doi:10.5072/FK2PZ5NR5");

        Object doi = extractor.extract(dataset);

        assertThat(doi.toString(), Is.is("doi:10.5072/FK2PZ5NR5"));
    }
}
