package sgf.gateway.model.metrics.factory;

import sgf.gateway.model.metrics.UserAgent;

public interface UserAgentFactory {

    UserAgent createUserAgent(String name);
}
