package sgf.gateway.model.metadata;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import sgf.gateway.model.AbstractPersistableEntity;

import java.io.Serializable;

public class DataFormatImpl extends AbstractPersistableEntity implements DataFormat {

    /**
     * The name of this data format, ie NetCDF or XLS.
     */
    private String name;

    private String description;

    /**
     * Default constructor, instantiation of objects from the persistence mechanism.
     */
    public DataFormatImpl() {

    }

    public DataFormatImpl(Serializable identifier, Integer version, String dataFormatName, String dataFormatDescription) {

        super(identifier, version);

        this.name = dataFormatName;
        this.description = dataFormatDescription;
    }

    @Override
    public String getName() {

        return this.name;
    }

    public void setName(String name) {

        this.name = name;
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
                .append("name", name)
                .append("description", description).toString();
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof DataFormatImpl)) {
            return false;
        }

        if (this == object) {
            return true;
        }

        DataFormatImpl other = (DataFormatImpl) object;

        return new EqualsBuilder()
                .append(this.getName(), other.getName())
                .append(this.getDescription(), other.getDescription())
                .isEquals();
    }

    @Override
    public int hashCode() {

        return new HashCodeBuilder(17, 37)
                .append(this.getName())
                .append(this.getDescription())
                .toHashCode();
    }

}
