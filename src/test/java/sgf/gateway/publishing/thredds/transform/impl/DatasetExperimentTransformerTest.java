package sgf.gateway.publishing.thredds.transform.impl;

import org.junit.Before;
import org.junit.Test;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.activities.modeling.Experiment;
import sgf.gateway.model.metadata.activities.modeling.builder.ExperimentBuilder;
import thredds.catalog.InvDataset;

import static org.mockito.Mockito.*;

public class DatasetExperimentTransformerTest {

    DatasetExperimentTransformer datasetExperimentTransformer;
    InvDataset mockInvDataset = mock(InvDataset.class);
    Dataset mockDataset = mock(Dataset.class);
    ExperimentBuilder mockExperimentBuilder = mock(ExperimentBuilder.class);
    Experiment mockExperiment = mock(Experiment.class);

    public static final String THREDDS_PROPERTY_NAME = "experiment";

    @Before
    public void setUp() throws Exception {

        String experimentName = "Experiment 1";
        when(mockInvDataset.findProperty(THREDDS_PROPERTY_NAME)).thenReturn(experimentName);
        when(mockExperimentBuilder.build(experimentName)).thenReturn(mockExperiment);

        datasetExperimentTransformer = new DatasetExperimentTransformer(mockExperimentBuilder);
    }

    @Test
    public void testFindOrCreateExperiment() {

        // This returns an Experiment but we don't need to look at that here
        datasetExperimentTransformer.findOrCreateExperiment(mockInvDataset);

        verify(this.mockExperiment).setShortName("Experiment 1");
        //assertThat(experiment.getShortName(), is("Experiment 1"));
    }

    @Test
    public void testTransform() {

        datasetExperimentTransformer.transform(mockInvDataset, mockDataset);

        verify(this.mockDataset).associateActivity(mockExperiment);

    }


}
