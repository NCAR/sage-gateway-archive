package sgf.gateway.model.metadata.factory.impl;

import org.safehaus.uuid.UUID;
import org.springframework.util.Assert;
import sgf.gateway.dao.NewInstanceIdentifierStrategy;
import sgf.gateway.dao.impl.hibernate.VersionedUUIDIdentifier;
import sgf.gateway.model.metadata.RelatedLink;
import sgf.gateway.model.metadata.RelatedLinkImpl;
import sgf.gateway.model.metadata.activities.project.ProjectImpl;
import sgf.gateway.model.metadata.factory.LinkFactory;

import java.net.URI;

public class LinkFactoryImpl implements LinkFactory {

    private final NewInstanceIdentifierStrategy newInstanceIdentifierStrategy;

    public LinkFactoryImpl(NewInstanceIdentifierStrategy identifierStrategy) {
        this.newInstanceIdentifierStrategy = identifierStrategy;
    }

    public RelatedLink createLink(String text, URI uri) {

        Assert.notNull(text, "Link text cannot be null.");
        Assert.notNull(uri, "Link URI cannot be null.");

        VersionedUUIDIdentifier vuId = (VersionedUUIDIdentifier) this.newInstanceIdentifierStrategy.generateNewIdentifier(ProjectImpl.class);

        RelatedLink link = new RelatedLinkImpl((UUID) vuId.getIdentifierValue(), text, uri);

        return link;
    }
}
