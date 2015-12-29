package sgf.gateway.service.security.impl.acegi;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.safehaus.uuid.UUID;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sgf.gateway.model.security.Status;
import sgf.gateway.model.security.User;

import java.util.Collection;

/**
 * Implementation of Acegi UserDetails interface that holds a reference to the User object, and generates the Acegi GrantedAuthorities from the User
 * memberships. This class is a wrapper between the User object in the ESKE domain model and the Spring Security framework.
 */
public class AcegiUserDetails implements UserDetails {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    private final UUID userIdentifier;
    /**
     * The user.
     */
    private final transient User user;

    /**
     * The authorities.
     */
    private final transient Collection<GrantedAuthority> authorities;

    /**
     * Instantiates a new SpringSecurity UserDetails object from an ESKE User object.
     *
     * @param user the user
     */
    public AcegiUserDetails(User user) {

        this.user = user;
        this.userIdentifier = user.getIdentifier();

        // transform user permissions into Acegi granted authorities, and add anonymous authorities
        this.authorities = AcegiUtils.getGrantedAuthorities(user, true);
    }

    /**
     * {@inheritDoc}
     */
    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    /**
     * {@inheritDoc}
     */
    public String getPassword() {
        return this.user.getPassword();
    }

    /**
     * {@inheritDoc}
     */
    public String getUsername() {
        if (this.user != null) {
            return this.user.getUserName();
        } else {
            return "";
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean isAccountNonExpired() {
        return (this.user.getStatus() == Status.VALID);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isAccountNonLocked() {
        return (this.user.getStatus() == Status.VALID);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isCredentialsNonExpired() {
        return (this.user.getStatus() == Status.VALID);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isEnabled() {
        return (this.user.getStatus() == Status.VALID);
    }

    /**
     * Gets the details.
     *
     * @return the details
     */
    public User getDetails() {
        return this.user;
    }

    public User getUser() {
        return this.user;
    }

    public UUID getUserIdentifier() {

        return this.userIdentifier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.userIdentifier).append(this.authorities).toHashCode();
    }
}
