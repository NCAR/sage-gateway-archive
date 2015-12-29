package sgf.gateway.search.service.indexer.qualifier;

import sgf.gateway.model.metadata.Dataset;

public class SearchIndexQualifierDatasetSoftPubFiles extends SearchIndexQualifierDatasetPublishedFiles {

    @Override
    public Boolean isQualified(Object object) {

        Boolean qualified;

        Dataset dataset = (Dataset) object;

        if (dataset.isSoftwareDataset()) {
            qualified = Boolean.TRUE;
        } else {
            qualified = super.isQualified(dataset);
        }

        return qualified;
    }
}
