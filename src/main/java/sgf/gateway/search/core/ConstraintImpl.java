package sgf.gateway.search.core;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import sgf.gateway.search.api.Constraint;
import sgf.gateway.search.api.Facet;
import sgf.gateway.search.api.Operation;


public class ConstraintImpl implements Constraint {

    private Facet facet;
    private String name;
    private String description;
    private Long count = 0L;
    private Operation operation;

    public ConstraintImpl(Facet facet, String name) {
        this.name = name;
        this.facet = facet;
    }

    public ConstraintImpl(Facet facet, String name, String description, Long count) {
        this.name = name;
        this.description = description;
        this.count = count;
        this.facet = facet;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Operation getOperation() {
        return operation;
    }

    public Facet getFacet() {
        return facet;
    }

    public String toString() {
        return new ToStringBuilder(this).
                append("name", name).
                append("description", description).
                append("count", count).
                toString();
    }

    public int hashCode() {
        // pick a hard-coded, randomly chosen, non-zero, odd number
        // ideally different for each class
        return new HashCodeBuilder(3, 7).
                append(name).
                append(description).
                append(count).
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
            return false;
        }
        ConstraintImpl rhs = (ConstraintImpl) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(name, rhs.name)
                .append(description, rhs.description)
                .append(count, rhs.count)
                .isEquals();
    }
}
