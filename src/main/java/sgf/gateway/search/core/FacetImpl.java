package sgf.gateway.search.core;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import sgf.gateway.search.api.Constraints;
import sgf.gateway.search.api.Facet;

public class FacetImpl implements Facet {

    private String name;
    private Constraints constraints;

    public FacetImpl(String name, Constraints constraints) {
        super();
        this.name = name;
        this.constraints = constraints;
    }

    public FacetImpl(String name) {
        this.name = name;
        this.constraints = new ConstraintsImpl();
    }

    public String getName() {
        return name;
    }

    public Constraints getConstraints() {
        return constraints;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setConstraints(Constraints constraints) {
        this.constraints = constraints;
    }

    public String toString() {
        return new ToStringBuilder(this).append("name", name).toString();
    }

    public int hashCode() {
        // you pick a hard-coded, randomly chosen, non-zero, odd number
        // ideally different for each class
        return new HashCodeBuilder(17, 37).
                append(name).
                toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {

            if (obj instanceof String) {
                return (this.name.compareTo((String) obj) == 0);
            }

            return false;
        }
        FacetImpl rhs = (FacetImpl) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(name, rhs.name)
                .isEquals();
    }
}
