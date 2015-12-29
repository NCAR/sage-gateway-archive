package sgf.gateway.web.controllers.security.openid.factory.impl;

import org.openid4java.server.ServerManager;
import sgf.gateway.model.Gateway;
import sgf.gateway.web.controllers.security.openid.factory.OpenidServerManagerFactory;

public class OpenidServerManagerFactoryImpl implements OpenidServerManagerFactory {

    private final Gateway gateway;

    public OpenidServerManagerFactoryImpl(Gateway gateway) {

        this.gateway = gateway;
    }

    @Override
    public ServerManager createServerManager() {

        ServerManager serverManager = new ServerManager();
        serverManager.setOPEndpointUrl(this.gateway.getIdpEndpoint());

        return serverManager;
    }

}
