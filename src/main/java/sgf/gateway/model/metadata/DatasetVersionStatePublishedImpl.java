package sgf.gateway.model.metadata;

import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.metadata.inventory.Variable;

public class DatasetVersionStatePublishedImpl implements DatasetVersionState {

    private final PublishedState publishedState;

    private final DatasetVersionImpl datasetVersionImpl;

    public DatasetVersionStatePublishedImpl(DatasetVersionImpl datasetVersionImpl) {

        this.publishedState = PublishedState.PUBLISHED;
        this.datasetVersionImpl = datasetVersionImpl;
    }

    public boolean isPrepublished() {

        return false;
    }

    public boolean isPublished() {

        return true;
    }

    public boolean isRetracted() {

        return false;
    }

    public boolean isDeleted() {

        return false;
    }

    public PublishedState getPublishedState() {

        return this.publishedState;
    }

    public void changeState(PublishedState newPublishedState) {

        if (PublishedState.PRE_PUBLISHED == newPublishedState) {

            throw new IllegalStateException("Transitioning from PUBLISHED state to PRE-PUBLISHED state is not allowed");

        } else if (PublishedState.RETRACTED == newPublishedState) {

            DatasetVersionState newDatasetVersionState = new DatasetVersionStateRetractedImpl(this.datasetVersionImpl);
            datasetVersionImpl.setDatasetVersionState(newDatasetVersionState);

        } else if (PublishedState.DELETED == newPublishedState) {

            DatasetVersionState newDatasetVersionState = new DatasetVersionStateDeletedImpl(this.datasetVersionImpl);
            datasetVersionImpl.setDatasetVersionState(newDatasetVersionState);
        }
    }

    public void addLogicalFile(LogicalFile logicalFile) {

        // FIXME -Temporary until we can inject via hibernate
        this.datasetVersionImpl.getLogicalFilesReference().add(logicalFile);
    }

    public void addVariable(final Variable variable) {

        this.datasetVersionImpl.getVariablesReference().add(variable);
    }

    public void removeVariable(Variable variable) {

        this.datasetVersionImpl.getVariablesReference().remove(variable);
    }

    public void removeLogicalFile(final LogicalFile logicalFile) {

        this.datasetVersionImpl.getLogicalFilesReference().remove(logicalFile);
    }

}
