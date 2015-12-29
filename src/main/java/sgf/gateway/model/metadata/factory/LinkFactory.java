package sgf.gateway.model.metadata.factory;

import sgf.gateway.model.metadata.RelatedLink;

import java.net.URI;

public interface LinkFactory {
    RelatedLink createLink(String text, URI uri);
}
