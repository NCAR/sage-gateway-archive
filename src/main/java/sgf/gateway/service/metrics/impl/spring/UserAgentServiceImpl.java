package sgf.gateway.service.metrics.impl.spring;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sgf.gateway.dao.metrics.UserAgentDAO;
import sgf.gateway.model.metrics.UserAgent;
import sgf.gateway.model.metrics.factory.UserAgentFactory;
import sgf.gateway.service.metrics.UserAgentService;

/**
 * The Class UserAgentServiceImpl.
 */
public class UserAgentServiceImpl implements UserAgentService {

    /**
     * The user agent dao.
     */
    private final UserAgentDAO userAgentDAO;

    /**
     * The user agent factory.
     */
    private final UserAgentFactory userAgentFactory;

    /**
     * Instantiates a new user agent service impl.
     *
     * @param userAgentDAO     the user agent dao
     * @param userAgentFactory the user agent factory
     */
    public UserAgentServiceImpl(UserAgentDAO userAgentDAO, UserAgentFactory userAgentFactory) {

        this.userAgentDAO = userAgentDAO;
        this.userAgentFactory = userAgentFactory;
    }

    /**
     * {@inheritDoc}
     */
    public UserAgent getUserAgent(String name) {

        return getUserAgent(name, true);
    }

    /**
     * {@inheritDoc}
     */
    public UserAgent getUserAgent(String name, boolean add) {

        UserAgent userAgent = null;

        if (name != null) {

            userAgent = this.userAgentDAO.getUserAgent(name);

            if (userAgent == null) {

                userAgent = this.userAgentFactory.createUserAgent(name);

                if (add) {

                    storeUserAgent(userAgent);
                }
            }
        }

        return userAgent;
    }

    /**
     * Store user agent.
     *
     * @param userAgent the user agent
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void storeUserAgent(UserAgent userAgent) {

        UserAgentServiceImpl.this.userAgentDAO.storeUserAgent(userAgent);

    }
}
