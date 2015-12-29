package sgf.gateway.search.extract.dataset;

import org.junit.Before;
import org.junit.Test;
import sgf.gateway.model.metadata.ContainerType;
import sgf.gateway.model.metadata.Dataset;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DatasetTypeFieldExtractorTest {

    private DatasetTypeFieldExtractor extractor;
    private Dataset mockDataset;

    @Before
    public void setup() {

        this.extractor = new DatasetTypeFieldExtractor("Dataset", "Project", "Software");
        this.mockDataset = mock(Dataset.class);
    }

    @Test
    public void isDataset() {

        when(this.mockDataset.getContainerType()).thenReturn(ContainerType.DATASET);
        when(this.mockDataset.isSoftwareDataset()).thenReturn(false);

        assertThat(this.extractor.extract(mockDataset).toString(), is(equalTo("Dataset")));
    }

    @Test
    public void isSoftwareDataset() {

        when(this.mockDataset.getContainerType()).thenReturn(ContainerType.DATASET);
        when(this.mockDataset.isSoftwareDataset()).thenReturn(true);

        assertThat(this.extractor.extract(mockDataset).toString(), is(equalTo("Software")));
    }

    @Test
    public void isProject() {

        when(this.mockDataset.getContainerType()).thenReturn(ContainerType.PROJECT);

        assertThat(this.extractor.extract(this.mockDataset).toString(), is(equalTo("Project")));
    }
}
