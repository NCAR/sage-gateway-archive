package sgf.gateway.publishing.thredds.transform.impl;

import org.springframework.util.StringUtils;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.activities.modeling.Ensemble;
import sgf.gateway.model.metadata.activities.modeling.builder.EnsembleBuilder;
import sgf.gateway.publishing.thredds.transform.ThreddsDatasetTransformer;
import thredds.catalog.InvDataset;

public class DatasetEnsembleTransformer implements ThreddsDatasetTransformer {

    public static final String THREDDS_PROPERTY_NAME = "ensemble";
    private static final String THREDDS_PROPERTY_NAME_DEPRECATED = "run_name";

    private EnsembleBuilder ensembleBuilder;

    public DatasetEnsembleTransformer(EnsembleBuilder ensembleBuilder) {

        this.ensembleBuilder = ensembleBuilder;
    }

    @Override
    public void transform(InvDataset invDataset, Dataset dataset) {

        Ensemble ensemble = getEnsemble(invDataset);

        if (null != ensemble) {

            dataset.associateActivity(ensemble);
        }
    }

    protected Ensemble getEnsemble(InvDataset invDataset) {

        Ensemble ensemble = null;

        String ensembleName = invDataset.findProperty(THREDDS_PROPERTY_NAME);

        if (!StringUtils.hasText(ensembleName)) {

            ensembleName = invDataset.findProperty(THREDDS_PROPERTY_NAME_DEPRECATED);
        }

        if (StringUtils.hasText(ensembleName)) {

            ensemble = ensembleBuilder.build(ensembleName);
        }

        return ensemble;
    }
}
