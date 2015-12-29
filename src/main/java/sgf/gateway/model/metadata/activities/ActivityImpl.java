package sgf.gateway.model.metadata.activities;

import org.apache.commons.collections.collection.UnmodifiableCollection;
import sgf.gateway.model.AbstractPersistableEntity;
import sgf.gateway.model.metadata.Dataset;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ActivityImpl extends AbstractPersistableEntity implements Activity {

    private ActivityType activityType;

    private String name;

    private String description;

    private Set<Dataset> associatedDatasetsReference;

    protected ActivityImpl(ActivityType activityType) {

        super();
        this.activityType = activityType;
    }

    protected ActivityImpl(ActivityType activityType, Serializable identifier, Serializable version, String name) {

        super(identifier, version);

        this.activityType = activityType;
        this.name = name;

        this.associatedDatasetsReference = new HashSet<Dataset>();
    }

    public ActivityType getActivityType() {

        return this.activityType;
    }

    @Override
    public String getName() {

        return this.name;
    }

    @Override
    public void setName(String name) {

        this.name = name;
    }

    @Override
    public String getDescription() {

        return this.description;
    }

    @Override
    public void setDescription(final String description) {

        this.description = description;
    }

    public Collection<Dataset> getAssociatedDatasets() {

        return UnmodifiableCollection.decorate(this.getAssociatedDatasetsReference());
    }

    protected Set<Dataset> getAssociatedDatasetsReference() {
        return this.associatedDatasetsReference;
    }

    public Long getAssociatedDatasetCount() {

        return Long.valueOf(this.getAssociatedDatasetsReference().size());
    }

    public void addBackLink(Dataset dataset) {

        this.getAssociatedDatasetsReference().add(dataset);
    }

    public void removeBackLink(Dataset dataset) {

        this.getAssociatedDatasetsReference().remove(dataset);
    }
}
