package sgf.gateway.publishing.thredds.transform.impl;

import org.junit.Before;
import org.junit.Test;
import sgf.gateway.model.metadata.Dataset;
import thredds.catalog.InvDataset;

import static org.mockito.Mockito.*;

public class DatasetTitleTransformerTest {

    DatasetTitleTransformer datasetTitleTransformer;
    InvDataset mockInvDataset = mock(InvDataset.class);
    Dataset mockDataset = mock(Dataset.class);

    @Before
    public void setUp() throws Exception {

        when(mockInvDataset.getName()).thenReturn("NARCCAP mm5i ncep Table 2");

        datasetTitleTransformer = new DatasetTitleTransformer();
    }


    @Test
    public void testTransform() {

        datasetTitleTransformer.transform(mockInvDataset, mockDataset);

        verify(this.mockDataset).setTitle("NARCCAP mm5i ncep Table 2");

    }

}
