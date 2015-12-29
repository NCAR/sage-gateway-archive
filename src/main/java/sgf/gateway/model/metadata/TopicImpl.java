package sgf.gateway.model.metadata;

import org.apache.commons.collections.collection.UnmodifiableCollection;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.safehaus.uuid.UUID;
import sgf.gateway.model.AbstractPersistableEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

public class TopicImpl extends AbstractPersistableEntity implements Topic {

    private String name;

    private Taxonomy type;

    private Collection<Dataset> datasetsReference = new HashSet<>();

    /**
     * Default constructor required for Hibernate support.
     */
    public TopicImpl() {

        super(false); // don't generate UUID
    }

    /**
     * Constructor to set all immutable properties.
     */
    public TopicImpl(UUID identifier, Serializable version, String name, Taxonomy type) {

        super(identifier, version);
        this.name = name;
        this.type = type;
    }

    @Override
    public String getName() {

        return this.name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public Taxonomy getType() {

        return this.type;
    }

    public void setType(Taxonomy type) {

        this.type = type;
    }

    @SuppressWarnings("unchecked")
    public Collection<Dataset> getDatasets() {

        return UnmodifiableCollection.decorate(this.datasetsReference);
    }

    public void setDatasets(Collection<Dataset> datasets) {

        this.datasetsReference = datasets;
    }

    public void addBackLink(Dataset theDataset) {

        this.datasetsReference.add(theDataset);
    }

    /**
     * {@inheritDoc}
     */
    public void removeBackLink(Dataset theDataset) {
        this.datasetsReference.remove(theDataset);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(this.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(this)))
                .append("identifier", this.getIdentifier())
                .append("name", this.getName())
                .append("type", this.getType()).toString();
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof TopicImpl)) {
            return false;
        }

        if (this == object) {
            return true;
        }

        TopicImpl other = (TopicImpl) object;

        return new EqualsBuilder()
                .append(name, other.getName())
                .append(type, other.getType())
                .isEquals();
    }

    @Override
    public int hashCode() {
        // you pick a hard-coded, randomly chosen, non-zero, odd number
        // ideally different for each class
        return new HashCodeBuilder(17, 37)
                .append(getName())
                .append(getType())
                .toHashCode();
    }
}
