package sgf.gateway.service.metadata;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.RelatedLink;
import sgf.gateway.web.controllers.dataset.relatedLink.RelatedLinkCommand;

public interface RelatedLinkService {

    RelatedLink addRelatedLinkToDataset(Dataset dataset, RelatedLinkCommand command);

    void removeRelatedLinkFromDataset(Dataset dataset, RelatedLink link);

    void updateRelatedLink(Dataset dataset, RelatedLink newLink);

}
