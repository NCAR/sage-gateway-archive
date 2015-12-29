package sgf.gateway.publishing.thredds.transform.impl;

import org.springframework.util.StringUtils;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.activities.modeling.Experiment;
import sgf.gateway.model.metadata.activities.modeling.builder.ExperimentBuilder;
import sgf.gateway.publishing.thredds.transform.ThreddsDatasetTransformer;
import thredds.catalog.InvDataset;

public class DatasetExperimentTransformer implements ThreddsDatasetTransformer {

    public static final String THREDDS_PROPERTY_NAME = "experiment";

    private ExperimentBuilder experimentBuilder;

    public DatasetExperimentTransformer(ExperimentBuilder experimentBuilder) {

        this.experimentBuilder = experimentBuilder;
    }

    @Override
    public void transform(InvDataset invDataset, Dataset dataset) {

        Experiment experiment = findOrCreateExperiment(invDataset);

        if (experiment != null) {
            dataset.associateActivity(experiment);
        }
    }

    protected Experiment findOrCreateExperiment(InvDataset invDataset) {

        Experiment experiment = null;

        String experimentName = invDataset.findProperty(THREDDS_PROPERTY_NAME);

        if (StringUtils.hasText(experimentName)) {

            experiment = experimentBuilder.build(experimentName);

            experiment.setShortName(experimentName);
        }

        return experiment;
    }
}
