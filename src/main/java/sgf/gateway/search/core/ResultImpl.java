package sgf.gateway.search.core;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import sgf.gateway.search.api.Result;

import java.io.Serializable;

public class ResultImpl implements Result {

    private String title;
    private String description;
    private String shortName;
    private String authoritativeSourceURI;
    private String detailsURI;
    private String identifier;
    private String type;
    private boolean downloadable;
    private boolean remoteIndexable;

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public String getAuthoritativeSourceURI() {
        return this.authoritativeSourceURI;
    }

    public void setAuthoritativeSourceURI(String authoritativeSourceURI) {
        this.authoritativeSourceURI = authoritativeSourceURI;
    }

    @Override
    public String getDetailsURI() {
        return this.detailsURI;
    }

    public void setDetailsURI(String detailsURI) {
        this.detailsURI = detailsURI;
    }

    @Override
    public Serializable getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean isDownloadable() {
        return this.downloadable;
    }

    public void setDownloadable(Boolean downloadable) {
        this.downloadable = downloadable;
    }

    @Override
    public boolean isRemoteIndexable() {
        return this.remoteIndexable;
    }

    public void setRemoteIndexable(Boolean remoteIndexable) {
        this.remoteIndexable = remoteIndexable;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("title", title).append("description", description).append("shortName", shortName)
                .append("authoritativeSourceURI", authoritativeSourceURI).append("detailsURI", detailsURI).append("identifier", identifier).append("type", type).toString();
    }

    @Override
    public int hashCode() {
        // pick a hard-coded, randomly chosen, non-zero, odd number
        // ideally different for each class
        return new HashCodeBuilder(7, 35).append(title).append(description).append(shortName).append(authoritativeSourceURI).append(detailsURI).append(identifier).append(type)
                .toHashCode();
    }

    @Override
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
        ResultImpl rhs = (ResultImpl) obj;
        return new EqualsBuilder().appendSuper(super.equals(obj)).append(title, rhs.title).append(description, rhs.description).append(shortName, rhs.shortName)
                .append(authoritativeSourceURI, rhs.authoritativeSourceURI).append(detailsURI, rhs.detailsURI).append(identifier, rhs.identifier).append(type, rhs.type).isEquals();
    }
}
