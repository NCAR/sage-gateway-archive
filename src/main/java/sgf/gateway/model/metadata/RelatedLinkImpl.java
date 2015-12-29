package sgf.gateway.model.metadata;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.safehaus.uuid.UUID;
import sgf.gateway.audit.Auditable;

import java.net.URI;

@Audited
public class RelatedLinkImpl implements RelatedLink, Auditable {

    private UUID identifier;

    private String text;

    private URI uri;

    @NotAudited
    private Integer version;

    protected RelatedLinkImpl() {
    }

    public RelatedLinkImpl(UUID identifier, String text, URI uri) {

        this.identifier = identifier;
        this.text = text;
        this.uri = uri;
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public void setUri(URI newURI) {
        this.uri = newURI;
    }

    public URI getUri() {
        return this.uri;
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this)
                .append(this.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(this)))
                .append("identifier", this.getIdentifier())
                .append("uri", this.getUri())
                .append("text", getText())
                .toString();
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof RelatedLinkImpl)) {
            return false;
        }

        if (this == object) {
            return true;
        }

        RelatedLinkImpl other = (RelatedLinkImpl) object;

        return new EqualsBuilder()
                .append(this.getText(), other.getText())
                .append(this.getUri(), other.getUri())
                .isEquals();
    }

    @Override
    public int hashCode() {

        return new HashCodeBuilder(17, 37)
                .append(this.getText())
                .append(this.getUri())
                .toHashCode();
    }
}
