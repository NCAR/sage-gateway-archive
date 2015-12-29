package sgf.gateway.search.core;

import sgf.gateway.search.api.Facet;
import sgf.gateway.search.api.Facets;

import java.util.ArrayList;
import java.util.Collection;

public class FacetsImpl extends ArrayList<Facet> implements Facets {

    private static final long serialVersionUID = 1L;

    public FacetsImpl() {
        super();
    }

    public FacetsImpl(Collection<? extends Facet> c) {
        super(c);
    }

    public FacetsImpl(int initialCapacity) {
        super(initialCapacity);
    }
}
