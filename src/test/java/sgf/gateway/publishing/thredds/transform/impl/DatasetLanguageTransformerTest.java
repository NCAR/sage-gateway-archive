package sgf.gateway.publishing.thredds.transform.impl;

import org.junit.Before;
import org.junit.Test;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.descriptive.DescriptiveMetadata;
import thredds.catalog.InvDataset;
import thredds.catalog.InvDocumentation;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class DatasetLanguageTransformerTest {

    DatasetLanguageTransformer datasetLanguageTransformer;
    InvDataset mockInvDataset = mock(InvDataset.class);
    Dataset mockDataset = mock(Dataset.class);
    DescriptiveMetadata mockDescriptiveMetadata = mock(DescriptiveMetadata.class);
    InvDocumentation mockInvDocumentation = mock(InvDocumentation.class);

    public static final String DOCUMENTATION_TYPE = "language";

    @Before
    public void setUp() throws Exception {

        List<InvDocumentation> doclist = new ArrayList<InvDocumentation>();
        doclist.add(mockInvDocumentation);

        when(mockInvDataset.getDocumentation()).thenReturn(doclist);
        when(mockInvDataset.getDocumentation(DOCUMENTATION_TYPE)).thenReturn("docType");

        datasetLanguageTransformer = new DatasetLanguageTransformer();
    }


    @Test
    public void testTransform() {

        datasetLanguageTransformer.transform(mockInvDataset, mockDescriptiveMetadata);

        verify(this.mockDescriptiveMetadata).setLanguage("docType");

    }

}
