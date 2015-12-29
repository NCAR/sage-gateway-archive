package sgf.gateway.publishing.thredds.transform.impl;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.publishing.thredds.transform.ThreddsDatasetTransformer;
import thredds.catalog.InvDataset;

import java.util.List;

public class ThreddsDatasetTopicTransformerAggregator extends DatasetTransformerAggregator implements ThreddsDatasetTransformer {

    public ThreddsDatasetTopicTransformerAggregator(List<ThreddsDatasetTransformer> transformers) {

        super(transformers);
    }

    @Override
    public void transform(InvDataset invDataset, Dataset dataset) {

        clearAllTopics(dataset);

        super.transform(invDataset, dataset);
    }

    private void clearAllTopics(Dataset dataset) {
        dataset.removeAllTopics();
    }

}
