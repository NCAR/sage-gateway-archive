package sgf.gateway.model.metadata.activities.modeling;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.safehaus.uuid.UUID;
import sgf.gateway.model.AbstractPersistableEntity;

public class ForcingImpl extends AbstractPersistableEntity implements Forcing {

    private String name;

    private String description;

    private String value;

    private ForcingType type;

    protected ForcingImpl() {
        super(); // do not generate UUID
    }

    public ForcingImpl(UUID identifier, Integer version, String name, ForcingType type) {

        super(identifier, version);
        this.name = name;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ForcingType getType() {
        return type;
    }

    public void setType(ForcingType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(Object object) {

        if (!(object instanceof ForcingImpl)) {
            return false;
        }

        if (this == object) {
            return true;
        }

        ForcingImpl other = (ForcingImpl) object;

        return new EqualsBuilder()
                .append(this.getName(), other.getName())
                .append(this.getDescription(), other.getDescription())
                .append(this.getValue(), other.getValue())
                .append(this.getType(), other.getType())
                .isEquals();
    }

    public int hashCode() {

        return new HashCodeBuilder(17, 37)
                .append(this.getName())
                .append(this.getDescription())
                .append(this.getValue())
                .append(this.getType())
                .toHashCode();
    }
}
