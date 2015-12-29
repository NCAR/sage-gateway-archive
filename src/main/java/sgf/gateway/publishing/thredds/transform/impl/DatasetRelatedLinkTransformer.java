package sgf.gateway.publishing.thredds.transform.impl;

import sgf.gateway.model.metadata.RelatedLink;
import sgf.gateway.model.metadata.descriptive.DescriptiveMetadata;
import sgf.gateway.model.metadata.factory.LinkFactory;
import sgf.gateway.publishing.thredds.transform.ThreddsDescriptiveMetadataTransformer;
import thredds.catalog.InvDataset;
import thredds.catalog.InvDocumentation;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

public class DatasetRelatedLinkTransformer implements ThreddsDescriptiveMetadataTransformer {

    private LinkFactory linkFactory;

    public DatasetRelatedLinkTransformer(LinkFactory linkFactory) {

        this.linkFactory = linkFactory;
    }

    @Override
    public void transform(InvDataset invDataset, DescriptiveMetadata descriptiveMetadata) {

        Set<RelatedLink> removableLinks = new HashSet<RelatedLink>(descriptiveMetadata.getRelatedLinks());

        for (InvDocumentation docLink : invDataset.getDocumentation()) {

            RelatedLink relatedLink = getRelatedLink(docLink);

            if (relatedLink != null) {

                descriptiveMetadata.addRelatedLink(relatedLink);

                removableLinks.remove(relatedLink);
            }
        }

        this.removeRelatedLinks(descriptiveMetadata, removableLinks);

    }

    protected void removeRelatedLinks(DescriptiveMetadata descriptiveMetadata, Set<RelatedLink> removableLinks) {

        for (RelatedLink link : removableLinks) {

            descriptiveMetadata.removeRelatedLink(link);
        }

    }

    protected RelatedLink getRelatedLink(InvDocumentation link) {

        RelatedLink relatedLink = null;

        if (link.hasXlink()) {

            String title = link.getXlinkTitle();
            String uriString = link.getXlinkHref();

            relatedLink = this.linkFactory.createLink(title, URI.create(uriString));
        }

        return relatedLink;
    }

}
