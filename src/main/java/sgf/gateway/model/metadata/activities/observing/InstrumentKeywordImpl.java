package sgf.gateway.model.metadata.activities.observing;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import sgf.gateway.model.AbstractPersistableEntity;

public class InstrumentKeywordImpl extends AbstractPersistableEntity implements InstrumentKeyword {

    private String category;

    private String instrumentClass;

    private String type;

    private String subtype;

    private String shortName;

    private String longName;

    private String displayText;

    /* For Hibernate */
    public InstrumentKeywordImpl() {
        super(false); // don't generate UUID
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String getInstrumentClass() {
        return instrumentClass;
    }

    @Override
    public void setInstrumentClass(String instrumentClass) {
        this.instrumentClass = instrumentClass;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getSubtype() {
        return subtype;
    }

    @Override
    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    @Override
    public String getShortName() {
        return this.shortName;
    }

    @Override
    public void setShortName(final String shortName) {
        this.shortName = shortName;
    }

    @Override
    public String getLongName() {
        return this.longName;
    }

    @Override
    public void setLongName(final String longName) {
        this.longName = longName;
    }

    @Override
    public String getDisplayText() {

        return this.displayText;
    }

    public boolean equals(Object object) {

        if (!(object instanceof InstrumentKeywordImpl)) {
            return false;
        }

        if (this == object) {
            return true;
        }

        InstrumentKeywordImpl other = (InstrumentKeywordImpl) object;

        return new EqualsBuilder()
                .append(this.getCategory(), other.getCategory())
                .append(this.getInstrumentClass(), other.getInstrumentClass())
                .append(this.getType(), other.getType())
                .append(this.getSubtype(), other.getSubtype())
                .append(this.getShortName(), other.getShortName())
                .append(this.getLongName(), other.getLongName())
                .isEquals();
    }

    public int hashCode() {

        return new HashCodeBuilder(17, 37)
                .append(this.getCategory())
                .append(this.getInstrumentClass())
                .append(this.getType())
                .append(this.getSubtype())
                .append(this.getShortName())
                .append(this.getLongName())
                .toHashCode();
    }
}
