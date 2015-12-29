package sgf.gateway.model.metadata.activities.observing;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.safehaus.uuid.UUID;
import sgf.gateway.model.metadata.activities.ActivityImpl;
import sgf.gateway.model.metadata.activities.ActivityType;

import java.io.Serializable;


public class CampaignImpl extends ActivityImpl implements Campaign {

    private static final ActivityType TYPE = ActivityType.CAMPAIGN;

    protected CampaignImpl() {
        super(TYPE);
    }

    public CampaignImpl(UUID identifier, Serializable version, String name) {

        super(TYPE, identifier, version, name);
    }

    public boolean equals(Object object) {

        if (!(object instanceof CampaignImpl)) {
            return false;
        }

        if (this == object) {
            return true;
        }

        CampaignImpl other = (CampaignImpl) object;

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
