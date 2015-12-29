package sgf.gateway.search.service.indexer.qualifier;

import sgf.gateway.model.metadata.Dataset;

public class SearchIndexQualifierDatasetPublishedFiles implements SearchIndexQualifier {

    @Override
    public Boolean isQualified(Object object) {

        Boolean qualified = Boolean.FALSE;

        Dataset dataset = (Dataset) object;

        if (null != dataset.getCurrentDatasetVersion()) {
            if ((dataset.getCurrentDatasetVersion().isPublished()) && (dataset.getCurrentDatasetVersion().getLogicalFileCount() > 0)) {
                qualified = Boolean.TRUE;
            }
        }

        return qualified;
    }
}
