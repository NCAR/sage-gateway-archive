package sgf.gateway.model.metadata;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import sgf.gateway.model.AbstractPersistableEntity;

import java.io.Serializable;

public class LocationImpl extends AbstractPersistableEntity implements Location {

    private String name;

    private String description;

    private Taxonomy type;

    public LocationImpl() {

        super(false); // don't generate UUID
    }

    public LocationImpl(Serializable identifier, Serializable version, String name, Taxonomy type) {

        super(identifier, version); // generate UUID
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

    @Override
    public String getDescription() {

        return this.description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(this.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(this)))
                .append("identifier", this.getIdentifier())
                .append("name", this.getName())
                .append("description", this.getDescription())
                .append("type", this.getType().name())
                .toString();
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof LocationImpl)) {
            return false;
        }

        if (this == object) {
            return true;
        }

        LocationImpl other = (LocationImpl) object;

        return new EqualsBuilder()
                .append(this.getName(), other.getName())
                .append(this.getDescription(), other.getDescription())
                .isEquals();
    }

    @Override
    public int hashCode() {

        return new HashCodeBuilder(17, 37).
                append(this.getName()).
                append(this.getDescription()).
                toHashCode();
    }

}
