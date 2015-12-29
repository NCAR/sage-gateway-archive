package sgf.gateway.service.security.impl.acegi;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.security.Operation;

public class DatasetAccessDeniedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Dataset dataset;
    private Operation operation;

    public DatasetAccessDeniedException(Dataset dataset, Operation operation) {
        this.dataset = dataset;
        this.operation = operation;
    }

    public DatasetAccessDeniedException(Dataset dataset, Operation operation, String message) {
        super(message);
        this.dataset = dataset;
        this.operation = operation;
    }

    public DatasetAccessDeniedException(Dataset dataset, Operation operation, Throwable cause) {
        super(cause);
        this.dataset = dataset;
        this.operation = operation;
    }

    public DatasetAccessDeniedException(Dataset dataset, Operation operation, String message, Throwable cause) {
        super(message, cause);
        this.dataset = dataset;
        this.operation = operation;
    }

    public Dataset getDataset() {
        return dataset;
    }

    public Operation getOperation() {
        return operation;
    }

}
