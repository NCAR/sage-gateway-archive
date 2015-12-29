package sgf.gateway.utils.propertyeditors;

import org.junit.Before;
import org.junit.Test;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.service.metadata.DatasetNotFoundException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DatasetPropertyEditorTest {

    private Dataset mockDataset;
    private DatasetRepository mockDatasetRepository;
    private DatasetPropertyEditor datasetPropertyEditor;

    @Before
    public void setup() {

        this.mockDataset = mock(Dataset.class);
        this.mockDatasetRepository = mock(DatasetRepository.class);
        this.datasetPropertyEditor = new DatasetPropertyEditor(mockDatasetRepository);
    }

    @Test
    public void testGetValue() {

        final String datasetShortName = "dataset_identifer";
        when(mockDatasetRepository.getByShortName(datasetShortName)).thenReturn(mockDataset);

        datasetPropertyEditor.setAsText(datasetShortName);

        Dataset dataset = (Dataset) datasetPropertyEditor.getValue();
        assertThat(dataset, equalTo(mockDataset));
    }


    @Test
    public void testGetAsText() {

        String datasetShortName = "dataset_identifer";
        when(mockDatasetRepository.getByShortName(datasetShortName)).thenReturn(mockDataset);
        when(mockDataset.getShortName()).thenReturn(datasetShortName);

        datasetPropertyEditor.setAsText(datasetShortName);
        String datasetName = datasetPropertyEditor.getAsText();

        assertThat(datasetName, is(datasetShortName));
    }


    @Test(expected = DatasetNotFoundException.class)
    public void testIdentiferNotFound() {

        when(mockDatasetRepository.getByShortName("dataset_identifer_not_found")).thenReturn(null);

        datasetPropertyEditor.setAsText("dataset_identifer_not_found");

    }

    @Test
    public void testNoText() {

        datasetPropertyEditor.setAsText("");

        Dataset dataset = (Dataset) datasetPropertyEditor.getValue();

        assertNullDataset(dataset);
    }

    private void assertNullDataset(Dataset dataset) {

        assertThat(dataset, equalTo(null));
    }

    @Test
    public void testNullText() {

        datasetPropertyEditor.setAsText(null);

        Dataset dataset = (Dataset) datasetPropertyEditor.getValue();

        assertNullDataset(dataset);
    }
}
