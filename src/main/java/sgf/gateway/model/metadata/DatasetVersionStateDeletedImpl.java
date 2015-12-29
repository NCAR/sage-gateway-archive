package sgf.gateway.model.metadata;

import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.metadata.inventory.Variable;

public class DatasetVersionStateDeletedImpl implements DatasetVersionState {

    private final PublishedState publishedState;

    private final DatasetVersionImpl datasetVersionImpl;

    public DatasetVersionStateDeletedImpl(DatasetVersionImpl datasetVersionImpl) {

        this.publishedState = PublishedState.DELETED;
        this.datasetVersionImpl = datasetVersionImpl;
    }

    public boolean isPrepublished() {

        return false;
    }

    public boolean isPublished() {

        return false;
    }

    public boolean isRetracted() {

        return false;
    }

    public boolean isDeleted() {

        return true;
    }

    public PublishedState getPublishedState() {

        return this.publishedState;
    }

    public void changeState(PublishedState newPublishedState) {

        if (PublishedState.PRE_PUBLISHED == newPublishedState) {

            throw new IllegalStateException("Transitioning from DELETED state to PRE-PUBLISHED state is not allowed");

        } else if (PublishedState.PUBLISHED == newPublishedState) {

            DatasetVersionState newDatasetVersionState = new DatasetVersionStatePublishedImpl(this.datasetVersionImpl);
            datasetVersionImpl.setDatasetVersionState(newDatasetVersionState);

        } else if (PublishedState.RETRACTED == newPublishedState) {

            DatasetVersionState newDatasetVersionState = new DatasetVersionStateRetractedImpl(this.datasetVersionImpl);
            datasetVersionImpl.setDatasetVersionState(newDatasetVersionState);

        }
    }

    public void addLogicalFile(LogicalFile logicalFile) {

        throw new IllegalStateException("Adding LogicalFiles to a DatasetVersion in DELETED state is not allowed");
    }

    public void addVariable(Variable variable) {

        throw new IllegalStateException("Adding Variables to a DatasetVersion in DELETED state is not allowed");
    }

    public void removeVariable(Variable variable) {

        throw new IllegalStateException("Removing Variables from a DatasetVersion in DELETED state is not allowed");
    }

    public void removeLogicalFile(LogicalFile logicalFile) {

        throw new IllegalStateException("Removing LogicalFiles from a DatasetVersion in DELETED state is not allowed");
    }
}
