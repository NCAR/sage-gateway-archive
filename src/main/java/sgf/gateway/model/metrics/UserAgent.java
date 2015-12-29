package sgf.gateway.model.metrics;

import sgf.gateway.model.AbstractPersistableEntity;

import java.io.Serializable;

public class UserAgent extends AbstractPersistableEntity {

    /**
     * The name.
     */
    private String name;

    /**
     * The ignore.
     */
    private Boolean ignore;

    private UserAgentType userAgentType;

    /**
     * Used by Hibernate to instantiate a new user agent.
     */
    protected UserAgent() {

    }

    /**
     * <p>
     * Creates a new instance of UserAgent with a specific UUID, Version and Name.
     * </p>
     *
     * @param identifier the UUID we want this instance of UserAgent to have.
     * @param version    the version
     * @param name       the name
     */
    public UserAgent(Serializable identifier, Serializable version, String name) {

        super(identifier, version);

        this.name = name;
        this.ignore = false;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {

        return this.name;
    }

    /**
     * Checks if is ignore.
     *
     * @return the boolean
     */
    public Boolean isIgnore() {

        return this.ignore;
    }

    /**
     * Sets the ignore.
     *
     * @param ignore the new ignore
     */
    public void setIgnore(Boolean ignore) {

        this.ignore = ignore;
    }

    public UserAgentType getUserAgentType() {

        return this.userAgentType;
    }
}
