package sgf.gateway.publishing.thredds;

import sgf.gateway.model.metadata.Dataset;

public interface ThreddsPublishingService {
    Dataset publishThreddsCatalog(ThreddsDatasetDetails details);
}
