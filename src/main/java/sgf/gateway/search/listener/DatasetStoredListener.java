package sgf.gateway.search.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.event.DatasetStoredEvent;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.search.service.SearchService;

public class DatasetStoredListener implements ApplicationListener<DatasetStoredEvent> {

    private final SearchService searchService;
    private final DatasetRepository datasetRepository;

    public DatasetStoredListener(SearchService searchService, DatasetRepository datasetRepository) {
        super();
        this.searchService = searchService;
        this.datasetRepository = datasetRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void onApplicationEvent(final DatasetStoredEvent event) {
        this.index(this.getDataset(event));
    }

    protected void index(Dataset dataset) {
        this.searchService.index(dataset);
    }

    protected Dataset getDataset(DatasetStoredEvent event) {
        return this.datasetRepository.getByShortName(event.getShortName());
    }
}
