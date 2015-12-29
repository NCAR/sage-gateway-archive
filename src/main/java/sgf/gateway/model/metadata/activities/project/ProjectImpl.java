package sgf.gateway.model.metadata.activities.project;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import sgf.gateway.model.metadata.activities.ActivityImpl;
import sgf.gateway.model.metadata.activities.ActivityType;

import java.io.Serializable;
import java.net.URI;

public class ProjectImpl extends ActivityImpl implements Project {

    private URI projectURI;
    private String persistentIdentifier;

    protected ProjectImpl() {
        super(ActivityType.PROJECT);
    }

    public ProjectImpl(final Serializable identifier, final Serializable version, final String name) {
        super(ActivityType.PROJECT, identifier, version, name);
    }

    @Override
    public String getPersistentIdentifier() {
        return persistentIdentifier;
    }

    @Override
    public void setPersistentIdentifier(String persistentIdentifier) {
        this.persistentIdentifier = persistentIdentifier;
    }

    @Override
    public URI getProjectURI() {
        return this.projectURI;
    }

    @Override
    public void setProjectURI(URI projectURI) {
        this.projectURI = projectURI;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof ProjectImpl)) {
            return false;
        }

        if (this == object) {
            return true;
        }

        ProjectImpl other = (ProjectImpl) object;

        return new EqualsBuilder()
                .append(this.getName(), other.getName())
                .append(this.getDescription(), other.getDescription())
                .append(this.getPersistentIdentifier(), other.getPersistentIdentifier())
                .append(this.getProjectURI(), other.getProjectURI())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
                .append(this.getName())
                .append(this.getDescription())
                .append(this.getPersistentIdentifier())
                .append(this.getProjectURI())
                .toHashCode();
    }
}
