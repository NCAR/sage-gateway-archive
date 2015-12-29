package sgf.gateway.search.api;

import java.io.Serializable;

public interface Result {

    String getTitle();

    String getDescription();

    String getShortName();

    String getAuthoritativeSourceURI();

    String getDetailsURI();

    Serializable getIdentifier();

    String getType();

    boolean isDownloadable();

    boolean isRemoteIndexable();
}
