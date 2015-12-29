package sgf.gateway.search.extract.remoteindexable;

import sgf.gateway.search.api.RemoteIndexable;
import sgf.gateway.search.extract.FieldExtractor;


public class RemoteIndexableAuthoritativeSourceURIFieldExtractor implements FieldExtractor {

    @Override
    public Object extract(Object object) {

        RemoteIndexable remote = (RemoteIndexable) object;
        Object value = remote.getAuthoritativeSourceURI().toString();

        return value;
    }

}
