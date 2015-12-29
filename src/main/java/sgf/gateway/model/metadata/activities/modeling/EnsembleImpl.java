package sgf.gateway.model.metadata.activities.modeling;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.safehaus.uuid.UUID;
import sgf.gateway.model.metadata.activities.ActivityImpl;
import sgf.gateway.model.metadata.activities.ActivityType;

public class EnsembleImpl extends ActivityImpl implements Ensemble {

    private static final ActivityType TYPE = ActivityType.ENSEMBLE;

    protected EnsembleImpl() {

        super(TYPE);
    }

    public EnsembleImpl(UUID identifier, Integer version, String name) {

        super(TYPE, identifier, version, name);
    }

    public boolean equals(Object object) {

        if (!(object instanceof EnsembleImpl)) {
            return false;
        }

        if (this == object) {
            return true;
        }

        EnsembleImpl other = (EnsembleImpl) object;

        return new EqualsBuilder()
                .append(this.getName(), other.getName())
                .append(this.getDescription(), other.getDescription())
                .isEquals();
    }

    public int hashCode() {

        return new HashCodeBuilder(17, 37)
                .append(this.getName())
                .append(this.getDescription())
                .toHashCode();
    }
}
