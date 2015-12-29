package sgf.gateway.model.metadata.activities;

import sgf.gateway.model.metadata.Dataset;

import java.util.Collection;

public interface Activity {

    ActivityType getActivityType();

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    Collection<Dataset> getAssociatedDatasets();

    void addBackLink(Dataset dataset);

    void removeBackLink(Dataset dataset);

    Long getAssociatedDatasetCount();
}
