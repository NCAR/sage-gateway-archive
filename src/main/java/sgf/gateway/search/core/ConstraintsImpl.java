package sgf.gateway.search.core;

import sgf.gateway.search.api.Constraint;
import sgf.gateway.search.api.Constraints;

import java.util.ArrayList;
import java.util.Collection;

public class ConstraintsImpl extends ArrayList<Constraint> implements Constraints {

    private static final long serialVersionUID = 0L;

    public ConstraintsImpl() {
        super();
    }

    public ConstraintsImpl(Collection<? extends ConstraintImpl> c) {
        super(c);
    }

    public ConstraintsImpl(int initialCapacity) {
        super(initialCapacity);
    }

}