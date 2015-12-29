package sgf.gateway.model.metadata.activities.project;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.envers.Audited;

@Audited
public class Award {

    private String awardNumber;

    public Award() {
    }

    public Award(String awardNumber) {
        this.awardNumber = awardNumber;
    }

    public String getAwardNumber() {
        return awardNumber;
    }

    public void setAwardNumber(String awardNumber) {
        this.awardNumber = awardNumber;
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Award)) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        Award other = (Award) obj;

        return new EqualsBuilder()
                .append(awardNumber, other.getAwardNumber())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
                .append(awardNumber)
                .toHashCode();
    }
}
