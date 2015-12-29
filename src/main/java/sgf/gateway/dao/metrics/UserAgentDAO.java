package sgf.gateway.dao.metrics;

import sgf.gateway.dao.Repository;
import sgf.gateway.model.metrics.UserAgent;

import java.io.Serializable;
import java.util.List;

public interface UserAgentDAO extends Repository<UserAgent, Serializable> {

    /**
     * Gets the user agent.
     *
     * @param userAgentString the user agent string
     * @return the user agent
     */
    UserAgent getUserAgent(String userAgentString);

    /**
     * Gets the user agents.
     *
     * @return the user agents
     */
    List<UserAgent> getUserAgents();

    /**
     * Store user agent.
     *
     * @param userAgent the user agent
     */
    void storeUserAgent(UserAgent userAgent);

}
