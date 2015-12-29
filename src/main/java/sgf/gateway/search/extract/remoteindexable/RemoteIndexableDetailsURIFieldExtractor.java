package sgf.gateway.search.extract.remoteindexable;

import sgf.gateway.search.api.RemoteIndexable;
import sgf.gateway.search.extract.FieldExtractor;

public class RemoteIndexableDetailsURIFieldExtractor implements FieldExtractor {

    @Override
    public Object extract(Object object) {

        RemoteIndexable remote = (RemoteIndexable) object;
        Object value = remote.getDetailsURI().toString();

        return value;
    }

}
