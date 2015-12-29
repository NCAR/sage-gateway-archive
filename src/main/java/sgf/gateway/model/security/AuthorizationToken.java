package sgf.gateway.model.security;

import org.safehaus.uuid.UUID;
import sgf.gateway.model.AbstractPersistableEntity;

import java.net.URI;
import java.util.Calendar;
import java.util.Date;

/**
 * Class representing a temporary, access token used to authorize access to a given resource (specified by URI).
 */
public class AuthorizationToken extends AbstractPersistableEntity {

    /**
     * URI identifying the resource to be accessed.
     */
    private URI uri;

    /**
     * The token creation time.
     */
    private Date date;

    /**
     * The token expiration date.
     */
    private Date expires;

    /**
     * The user to which the authorization token has been granted.
     */
    private User user;

    /**
     * No arguments constructor for Hibernate.
     */
    public AuthorizationToken() {

        super();
    }

    /**
     * Constructor to match an existing persisted instance.
     *
     * @param identifier the identifier
     * @param version    the version
     */
    public AuthorizationToken(final UUID identifier, final Integer version) {

        super(identifier, version);
    }

    /**
     * Constructor with minimum set of required fields.
     *
     * @param uri           the uri
     * @param user          the user
     * @param lifeTimeUnits the life time units
     * @param lifetime      the lifetime
     */
    public AuthorizationToken(URI uri, User user, int lifeTimeUnits, int lifetime) {

        super(true);

        this.uri = uri;
        this.user = user;

        initializeDates(lifeTimeUnits, lifetime);
    }

    /**
     * Initializes the start and end dates based on the lifetime.
     *
     * @param lifeTimeUnits the life time units
     * @param lifetime      the lifetime
     */
    protected void initializeDates(int lifeTimeUnits, int lifetime) {

        Calendar now = Calendar.getInstance();

        // Set the start date to the current time
        this.date = now.getTime();

        // Set the expires date, if specified, to the current + lifetime.
        now.add(lifeTimeUnits, lifetime);

        this.expires = now.getTime();

    }

    /**
     * Debug method.
     *
     * @return the string
     */
    @Override
    public String toString() {

        final StringBuilder sbldr = new StringBuilder();
        sbldr.append("AuthorizationToken: id=").append(getIdentifier()).append(" uri=" + this.uri + " token=" + getToken() + " date=" + this.date.getTime()
                + " expired=" + this.expires.getTime() + " user=" + this.user);
        return sbldr.toString();
    }

    /**
     * Gets the uri.
     *
     * @return the uri
     */
    public URI getUri() {

        return this.uri;
    }

    /**
     * Sets the uri.
     *
     * @param uri the new uri
     */
    public void setUri(URI uri) {

        this.uri = uri;
    }

    /**
     * Gets the token.
     *
     * @return the token
     */
    public UUID getToken() {

        return getIdentifier();
    }

    /**
     * Gets the date.
     *
     * @return the date
     */
    public Date getDate() {

        return this.date;
    }

    /**
     * Sets the date.
     *
     * @param date the new date
     */
    public void setDate(Date date) {

        this.date = date;
    }

    /**
     * Gets the user.
     *
     * @return the user
     */
    public User getUser() {

        return this.user;
    }

    /**
     * Sets the user.
     *
     * @param user the new user
     */
    public void setUser(User user) {

        this.user = user;
    }

    /**
     * Gets the expires.
     *
     * @return the expires
     */
    public Date getExpires() {

        return this.expires;
    }

    /**
     * Sets the expires.
     *
     * @param expires the new expires
     */
    public void setExpires(Date expires) {

        this.expires = expires;
    }

    /**
     * Checks if is expired.
     *
     * @return true, if is expired
     */
    public boolean isExpired() {

        return (Calendar.getInstance().getTimeInMillis() > this.expires.getTime());
    }
}
