package sgf.gateway.model;

import java.io.Serializable;

public abstract class AbstractPersistableChild {

    private PersistableEntity parent;

    private Serializable identifier;

    protected AbstractPersistableChild() {
        super();
    }

    /**
     * @param parent
     */
    protected AbstractPersistableChild(PersistableEntity parent) {
        super();
        this.parent = parent;
    }

    protected PersistableEntity getParent() {
        return parent;
    }

    protected void setParent(PersistableEntity parent) {
        this.parent = parent;
    }

}
