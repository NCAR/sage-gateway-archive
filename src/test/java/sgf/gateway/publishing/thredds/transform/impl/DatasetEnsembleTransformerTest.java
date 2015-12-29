package sgf.gateway.publishing.thredds.transform.impl;

import org.junit.Before;
import org.junit.Test;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.activities.modeling.Ensemble;
import sgf.gateway.model.metadata.activities.modeling.builder.EnsembleBuilder;
import thredds.catalog.InvDataset;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class DatasetEnsembleTransformerTest {

    DatasetEnsembleTransformer datasetEnsembleTransformer;
    InvDataset mockInvDataset = mock(InvDataset.class);
    Dataset mockDataset = mock(Dataset.class);
    EnsembleBuilder mockEnsembleBuilder = mock(EnsembleBuilder.class);
    Ensemble mockEnsemble = mock(Ensemble.class);

    public static final String THREDDS_PROPERTY_NAME = "ensemble";
    private static final String THREDDS_PROPERTY_NAME_DEPRECATED = "run_name";

    String ensembleName = "Quartet";
    String runName = "Treble";

    @Before
    public void setUp() throws Exception {

        when(mockEnsembleBuilder.build(ensembleName)).thenReturn(mockEnsemble);
        when(mockInvDataset.findProperty(THREDDS_PROPERTY_NAME)).thenReturn(ensembleName);
        when(mockInvDataset.findProperty(THREDDS_PROPERTY_NAME_DEPRECATED)).thenReturn(runName);

        datasetEnsembleTransformer = new DatasetEnsembleTransformer(mockEnsembleBuilder);
    }

    @Test
    public void testGetEnsemble() {

        mockEnsemble = datasetEnsembleTransformer.getEnsemble(mockInvDataset);

        verify(this.mockEnsembleBuilder).build(ensembleName);

        //assertThat(mockEnsemble.getName(), is(ensembleName));
        assertNotNull(mockEnsemble);
    }

    @Test
    public void testTransform() {

        datasetEnsembleTransformer.transform(mockInvDataset, mockDataset);

        verify(this.mockDataset).associateActivity(mockEnsemble);

    }


}
