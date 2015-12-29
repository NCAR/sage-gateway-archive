package sgf.gateway.model.metadata.activities.observing;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import sgf.gateway.model.AbstractPersistableEntity;

import java.io.Serializable;

public class PlatformTypeImpl extends AbstractPersistableEntity implements PlatformType {

    private String shortName;

    private String longName;

    public PlatformTypeImpl() {
        super(false); // don't generate UUID
    }

    public PlatformTypeImpl(Serializable identifier, Serializable version, String shortName) {

        super(identifier, version);
        this.shortName = shortName;
    }

    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return this.longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(this.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(this)))
                .append("identifier", this.getIdentifier())
                .append("shortName", shortName)
                .append("longName", longName)
                .toString();
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof PlatformTypeImpl)) {
            return false;
        }

        if (this == object) {
            return true;
        }

        PlatformTypeImpl other = (PlatformTypeImpl) object;
        return new EqualsBuilder()
                .append(this.getShortName(), other.getShortName())
                .append(this.getLongName(), other.getLongName())
                .isEquals();
    }

    @Override
    public int hashCode() {

        return new HashCodeBuilder(17, 37).
                append(this.getShortName()).
                append(this.getLongName()).
                toHashCode();
    }
}
