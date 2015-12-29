package sgf.gateway.search.api;

import java.net.URI;


public interface RemoteIndexable {

    String getAuthoritativeIdentifier();

    URI getAuthoritativeSourceURI();

    URI getDetailsURI();

    String getDataCenter();

    String getTitle();

    String getDescription();
}
