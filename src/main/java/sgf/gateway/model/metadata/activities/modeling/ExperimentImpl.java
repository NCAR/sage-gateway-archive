package sgf.gateway.model.metadata.activities.modeling;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.safehaus.uuid.UUID;
import sgf.gateway.model.metadata.activities.ActivityImpl;
import sgf.gateway.model.metadata.activities.ActivityType;

public class ExperimentImpl extends ActivityImpl implements Experiment {

    private static final ActivityType TYPE = ActivityType.EXPERIMENT;

    private String shortName;
    private String experimentNumber;

    protected ExperimentImpl() {
        super(TYPE);
    }

    public ExperimentImpl(UUID identifier, Integer version, String name) {
        super(ActivityType.EXPERIMENT, identifier, version, name);
    }

    public String getShortName() {

        return this.shortName;
    }

    public String getExperimentNumber() {

        return this.experimentNumber;
    }

    public void setShortName(String shortName) {

        this.shortName = shortName;
    }

    public void setExperimentNumber(String experimentNumber) {

        this.experimentNumber = experimentNumber;
    }

    public boolean equals(Object object) {

        if (!(object instanceof ExperimentImpl)) {
            return false;
        }

        if (this == object) {
            return true;
        }

        ExperimentImpl other = (ExperimentImpl) object;

        return new EqualsBuilder()
                .append(this.getName(), other.getName())
                .append(this.getDescription(), other.getDescription())
                .append(this.getShortName(), other.getShortName())
                .append(this.getExperimentNumber(), other.getExperimentNumber())
                .isEquals();
    }

    public int hashCode() {

        return new HashCodeBuilder(17, 37)
                .append(this.getName())
                .append(this.getDescription())
                .append(this.getShortName())
                .append(this.getExperimentNumber())
                .toHashCode();
    }
}
