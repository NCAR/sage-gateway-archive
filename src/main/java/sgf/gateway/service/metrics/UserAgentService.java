package sgf.gateway.service.metrics;

import sgf.gateway.model.metrics.UserAgent;

/**
 * The Interface UserAgentService.
 */
public interface UserAgentService {

    /**
     * Gets the user agent.
     *
     * @param name the name
     * @return the user agent
     */
    UserAgent getUserAgent(String name);

    /**
     * Gets the user agent.
     *
     * @param name the name
     * @param add  the add
     * @return the user agent
     */
    UserAgent getUserAgent(String name, boolean add);
}
