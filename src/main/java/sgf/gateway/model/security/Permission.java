package sgf.gateway.model.security;

import sgf.gateway.model.AbstractPersistableEntity;
import sgf.gateway.model.metadata.Dataset;

/**
 * Object holding authorization information. Specifically, each Permission constitutes an authorization statement that allows a Principal to execute an
 * Operation on a Resource.
 */
public class Permission extends AbstractPersistableEntity {

    private Dataset dataset;

    private Principal principal;

    private Operation operation;

    /**
     * Constructor to instantiate a new instance with all required fields.
     *
     * @param dataset   the dataset
     * @param principal the principal
     * @param operation the operation
     */
    public Permission(Dataset dataset, Principal principal, Operation operation) {

        // note: generates UUID
        super(true);
        this.dataset = dataset;
        this.principal = principal;
        this.operation = operation;
    }

    /**
     * No arguments constructor for Hibernate.
     */
    public Permission() {

        super();
    }

    public Dataset getDataset() {

        return this.dataset;
    }

    public void setDataset(Dataset dataset) {

        this.dataset = dataset;
    }

    public Operation getOperation() {

        return this.operation;
    }

    public void setOperation(Operation operation) {

        this.operation = operation;
    }

    public Principal getPrincipal() {

        return this.principal;
    }

    public void setPrincipal(Principal principal) {

        this.principal = principal;
    }
}
