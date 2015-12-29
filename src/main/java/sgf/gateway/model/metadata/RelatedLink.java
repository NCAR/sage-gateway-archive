package sgf.gateway.model.metadata;

import org.safehaus.uuid.UUID;

import java.net.URI;

public interface RelatedLink {

    UUID getIdentifier();

    String getText();

    void setText(String newText);

    URI getUri();

    void setUri(URI newURI);

}
