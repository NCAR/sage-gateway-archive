package sgf.gateway.model.metadata;

import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.metadata.inventory.Variable;

public interface DatasetVersionState {

    boolean isPrepublished();

    boolean isPublished();

    boolean isRetracted();

    boolean isDeleted();

    PublishedState getPublishedState();

    void changeState(PublishedState publishedState);

    void addLogicalFile(LogicalFile logicalFile);

    void removeLogicalFile(final LogicalFile logicalFile);

    void addVariable(Variable variable);

    void removeVariable(Variable variable);

}
