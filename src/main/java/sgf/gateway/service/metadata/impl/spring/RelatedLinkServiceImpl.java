package sgf.gateway.service.metadata.impl.spring;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.RelatedLink;
import sgf.gateway.model.metadata.descriptive.DescriptiveMetadata;
import sgf.gateway.model.metadata.factory.LinkFactory;
import sgf.gateway.service.metadata.RelatedLinkService;
import sgf.gateway.web.controllers.dataset.relatedLink.RelatedLinkCommand;

import java.net.URI;

public class RelatedLinkServiceImpl implements RelatedLinkService {

    private final LinkFactory linkFactory;

    public RelatedLinkServiceImpl(LinkFactory linkFactory) {

        this.linkFactory = linkFactory;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public RelatedLink addRelatedLinkToDataset(Dataset dataset, RelatedLinkCommand command) {

        DescriptiveMetadata descriptiveMetadata = dataset.getDescriptiveMetadata();

        URI linkUri = URI.create(command.getLinkUri());

        RelatedLink link = linkFactory.createLink(command.getLinkText(), linkUri);

        descriptiveMetadata.addRelatedLink(link);

        return link;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void removeRelatedLinkFromDataset(Dataset dataset, RelatedLink link) {

        DescriptiveMetadata descriptiveMetadata = dataset.getDescriptiveMetadata();

        descriptiveMetadata.removeRelatedLink(link);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateRelatedLink(Dataset dataset, RelatedLink newLink) {

        DescriptiveMetadata descriptiveMetadata = dataset.getDescriptiveMetadata();

        RelatedLink oldLink = descriptiveMetadata.getRelatedLink(newLink.getIdentifier());

        oldLink.setText(newLink.getText());
        oldLink.setUri(newLink.getUri());
    }

}
