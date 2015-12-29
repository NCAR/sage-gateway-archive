package sgf.gateway.web.controllers.security.openid.factory;

import org.openid4java.server.ServerManager;

public interface OpenidServerManagerFactory {

    ServerManager createServerManager();
}
