package sgf.gateway.search.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.event.DatasetStoredEvent;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.search.service.SearchService;

public class HierarchyAwareDatasetStoredListener extends DatasetStoredListener implements ApplicationListener<DatasetStoredEvent> {

    public HierarchyAwareDatasetStoredListener(SearchService searchService, DatasetRepository datasetRepository) {
        super(searchService, datasetRepository);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void onApplicationEvent(final DatasetStoredEvent event) {
        this.recursivelyIndex(this.getDataset(event));
    }

    private void recursivelyIndex(Dataset dataset) {
        this.index(dataset);
        for (Dataset childDataset : dataset.getDirectlyNestedDatasets()) {
            this.recursivelyIndex(childDataset);
        }
    }
}
