package sgf.gateway.search.core;

import sgf.gateway.search.api.Target;

import java.util.ArrayList;
import java.util.Collection;

public class TargetsImpl extends ArrayList<Target> implements sgf.gateway.search.api.Targets {

    public TargetsImpl() {
        super();
    }

    public TargetsImpl(Collection<? extends Target> c) {
        super(c);
    }

    public TargetsImpl(int initialCapacity) {
        super(initialCapacity);
    }


}
