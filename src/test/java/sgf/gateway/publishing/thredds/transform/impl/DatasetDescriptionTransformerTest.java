package sgf.gateway.publishing.thredds.transform.impl;

import org.junit.Before;
import org.junit.Test;
import sgf.gateway.model.metadata.Dataset;
import thredds.catalog.InvDataset;
import thredds.catalog.InvDocumentation;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class DatasetDescriptionTransformerTest {

    DatasetDescriptionTransformer datasetDescriptionTransformer;
    InvDataset mockInvDataset = mock(InvDataset.class);
    Dataset mockDataset = mock(Dataset.class);
    InvDocumentation mockInvDocumentation = mock(InvDocumentation.class);

    private static final String DESCRIPTION_KEY = "summary";

    @Before
    public void setUp() throws Exception {

        when(mockInvDataset.getDocumentation(DESCRIPTION_KEY)).thenReturn("dataset documentation");

        List<InvDocumentation> invDocList = new ArrayList<InvDocumentation>();
        invDocList.add(mockInvDocumentation);

        when(mockInvDataset.getDocumentation()).thenReturn(invDocList);

        datasetDescriptionTransformer = new DatasetDescriptionTransformer();
    }

    @Test
    public void testGetDescription() {

        String description = datasetDescriptionTransformer.getDescription(mockInvDataset);

        assertThat(description, is("dataset documentation"));
    }

    @Test
    public void testTransform() {

        datasetDescriptionTransformer.transform(mockInvDataset, mockDataset);

        verify(this.mockDataset).setDescription("dataset documentation");
    }


}
