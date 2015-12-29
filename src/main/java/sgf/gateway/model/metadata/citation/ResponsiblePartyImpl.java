package sgf.gateway.model.metadata.citation;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.safehaus.uuid.UUID;
import sgf.gateway.audit.Auditable;

import java.io.Serializable;
import java.util.Date;

@Audited
public class ResponsiblePartyImpl implements ResponsibleParty, Serializable, Auditable {

    private UUID identifier;

    @NotAudited
    private Date dateCreated;

    @NotAudited
    private Date dateUpdated;

    @NotAudited
    private Integer version;

    private String individualName;

    private String organizationName;

    private String positionName;

    private String email;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private ResponsiblePartyRole role;


    protected ResponsiblePartyImpl() {

    }

    public ResponsiblePartyImpl(UUID identifier, Integer version, ResponsiblePartyRole role) {

        this.identifier = identifier;
        this.version = version;
        this.role = role;
    }

    public UUID getIdentifier() {

        return this.identifier;
    }

    @Override
    public Date getDateCreated() {

        return this.dateCreated;
    }

    @Override
    public Date getDateUpdated() {

        return this.dateUpdated;
    }

    @Override
    public String getIndividualName() {
        return individualName;
    }

    public void setIndividualName(String individualName) {
        this.individualName = individualName;
    }

    @Override
    public String getOrganizationName() {
        return this.organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    @Override
    public String getPositionName() {
        return this.positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(ResponsiblePartyRole role) {
        this.role = role;
    }

    @Override
    public ResponsiblePartyRole getRole() {
        return this.role;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof ResponsiblePartyImpl)) {
            return false;
        }

        if (this == object) {
            return true;
        }

        ResponsiblePartyImpl other = (ResponsiblePartyImpl) object;

        return new EqualsBuilder()
                .append(this.getIndividualName(), other.getIndividualName())
                .append(this.getOrganizationName(), other.getOrganizationName())
                .append(this.getPositionName(), other.getPositionName())
                .append(this.getEmail(), other.getEmail())
                .append(this.getRole(), other.getRole())
                .isEquals();
    }

    @Override
    public int hashCode() {

        return new HashCodeBuilder(17, 37)
                .append(this.getIndividualName())
                .append(this.getOrganizationName())
                .append(this.getPositionName())
                .append(this.getEmail())
                .append(this.getRole())
                .toHashCode();
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(this.getClass().getName() + " {");
        result.append(" Name: " + individualName);
        result.append("}");
        return result.toString();
    }
}
