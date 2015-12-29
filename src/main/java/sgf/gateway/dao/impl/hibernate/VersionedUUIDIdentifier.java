package sgf.gateway.dao.impl.hibernate;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.safehaus.uuid.UUID;
import sgf.gateway.model.Identifier;

import java.io.Serializable;

public class VersionedUUIDIdentifier implements Identifier {

    /**
     * TODO: Document public field *
     */
    private static final long serialVersionUID = 1L;
    private UUID identifier;
    private Integer version;

    /**
     * @param identifier
     * @param version
     */
    public VersionedUUIDIdentifier(UUID identifier, Integer version) {
        super();
        this.identifier = identifier;
        this.version = version;
    }

    public Serializable getIdentifierValue() {
        return identifier;
    }

    public Integer getVersion() {
        return version;
    }

    public boolean isPersistent() {
        return (null == this.version);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VersionedUUIDIdentifier) {
            VersionedUUIDIdentifier other = (VersionedUUIDIdentifier) obj;
            EqualsBuilder builder = new EqualsBuilder();
            builder.append(getIdentifierValue(), other.getIdentifierValue());
            builder.append(getVersion(), other.getVersion());
            return builder.isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getIdentifierValue());
        builder.append(getVersion());
        return builder.hashCode();
    }

}
