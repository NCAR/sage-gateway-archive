package sgf.gateway.model.metadata;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.safehaus.uuid.UUID;
import sgf.gateway.model.AbstractPersistableEntity;

/**
 * The TimeFrequency class is intended to represent a controlled vocabulary for the time frequency of a geophysical dataset, used both in describing a dataset,
 * and for search and discovery purposes. Example values include "Daily", "Hourly", etc... This class is NOT an enumeration to allow for the possibility of
 * instantiating and persisting new instances dynamically. Note that many Dataset instances may reference the same TimeFrequency instance.
 * <p/>
 * FIXME: this class needs to be refactored as an interface and corresponding implementation. FIXME: constructors need review
 */
public class TimeFrequency extends AbstractPersistableEntity {

    private String name;

    private String description;

    public TimeFrequency() {

        super(false); // do not generate UUI
    }

    public TimeFrequency(UUID identifier, Integer version, String name, String description) {

        super(identifier, version);
        setName(name);
        setDescription(description);
    }

    public String getName() {

        return this.name;
    }

    public void setName(String name) {

        this.name = name;
    }

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
                .append("description", this.getDescription()).toString();
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof TimeFrequency)) {
            return false;
        }

        if (this == object) {
            return true;
        }

        TimeFrequency other = (TimeFrequency) object;

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
