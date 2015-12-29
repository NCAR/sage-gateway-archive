package sgf.gateway.search.listener;

import org.springframework.context.ApplicationListener;
import sgf.gateway.event.DatasetDeletedEvent;
import sgf.gateway.search.service.DeletionService;

public class DatasetDeletedListener implements ApplicationListener<DatasetDeletedEvent> {

    private DeletionService deletionService;

    public DatasetDeletedListener(DeletionService deletionService) {
        this.deletionService = deletionService;
    }

    @Override
    public void onApplicationEvent(DatasetDeletedEvent event) {
        String shortName = event.getShortName();
        this.deletionService.deleteDatasetByShortName(shortName);
    }
}
