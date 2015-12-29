package sgf.gateway.model;

import java.io.Serializable;

public abstract class AbstractPersistableIdentifiableChild {

    private Object parent;

    private Serializable identifier;

    protected AbstractPersistableIdentifiableChild() {
        super();
    }

    /**
     * @param parent
     */
    protected AbstractPersistableIdentifiableChild(Serializable identifier, Object parent) {
        super();
        this.identifier = identifier;
        this.parent = parent;
    }

    protected Object getParent() {
        return parent;
    }

    protected void setParent(Object parent) {
        this.parent = parent;
    }

    // Temp until we can clean up the interfaces
    protected Serializable getInternalIdentifier() {
        return this.identifier;
    }

}
