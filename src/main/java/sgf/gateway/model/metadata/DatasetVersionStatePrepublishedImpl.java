package sgf.gateway.model.metadata;

import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.metadata.inventory.Variable;

public class DatasetVersionStatePrepublishedImpl implements DatasetVersionState {

    private final PublishedState publishedState;

    private final DatasetVersionImpl datasetVersionImpl;

    public DatasetVersionStatePrepublishedImpl(final DatasetVersionImpl datasetVersionImpl) {

        this.publishedState = PublishedState.PRE_PUBLISHED;
        this.datasetVersionImpl = datasetVersionImpl;
    }

    public boolean isPrepublished() {

        return true;
    }

    public boolean isPublished() {

        return false;
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

        if (PublishedState.PUBLISHED == newPublishedState) {

            DatasetVersionState newDatasetVersionState = new DatasetVersionStatePublishedImpl(this.datasetVersionImpl);
            datasetVersionImpl.setDatasetVersionState(newDatasetVersionState);

        } else if (PublishedState.RETRACTED == newPublishedState) {

            DatasetVersionState newDatasetVersionState = new DatasetVersionStateRetractedImpl(this.datasetVersionImpl);
            datasetVersionImpl.setDatasetVersionState(newDatasetVersionState);

        } else if (PublishedState.DELETED == newPublishedState) {

            DatasetVersionState newDatasetVersionState = new DatasetVersionStateDeletedImpl(this.datasetVersionImpl);
            datasetVersionImpl.setDatasetVersionState(newDatasetVersionState);
        }
    }

    public void addLogicalFile(LogicalFile logicalFile) {

        this.datasetVersionImpl.getLogicalFilesReference().add(logicalFile);
    }

    public void removeLogicalFile(LogicalFile logicalFile) {

        this.datasetVersionImpl.getLogicalFilesReference().remove(logicalFile);

    }

    public void addVariable(Variable variable) {

        this.datasetVersionImpl.getVariablesReference().add(variable);
    }

    public void removeVariable(Variable variable) {

        this.datasetVersionImpl.getVariablesReference().remove(variable);
    }

}
