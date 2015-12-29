package sgf.gateway.search.extract.remoteindexable;

import sgf.gateway.search.api.RemoteIndexable;
import sgf.gateway.search.extract.FieldExtractor;


public class RemoteIndexableDescriptionFieldExtractor implements FieldExtractor {

    @Override
    public Object extract(Object object) {

        RemoteIndexable remote = (RemoteIndexable) object;
        Object value = remote.getDescription();

        return value;
    }

}
