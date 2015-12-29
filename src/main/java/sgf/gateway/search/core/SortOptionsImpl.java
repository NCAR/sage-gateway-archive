package sgf.gateway.search.core;

import sgf.gateway.search.api.SortOption;
import sgf.gateway.search.api.SortOptions;

import java.util.ArrayList;
import java.util.Collection;

public class SortOptionsImpl extends ArrayList<SortOption> implements SortOptions {

    private static final long serialVersionUID = 1L;

    public SortOptionsImpl() {
        super();
    }

    public SortOptionsImpl(Collection<? extends SortOption> c) {
        super(c);
    }

    public SortOptionsImpl(int initialCapacity) {
        super(initialCapacity);
    }
}
