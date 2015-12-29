package sgf.gateway.web.controllers.security.openid;

import sgf.gateway.model.Gateway;

import java.net.URI;
import java.net.URISyntaxException;

public class InternalOpenIdProviderConfiguration implements OpenIdConfiguration {

    private Gateway gateway;

    public InternalOpenIdProviderConfiguration(Gateway gateway) {
        super();
        this.gateway = gateway;
    }

    public URI getProviderEndPoint() {
        return this.gateway.getOpenIdProviderEndpoint();
    }

    public URI getBaseOpenIdURI() {
        String base = this.gateway.getBaseSecureURL().toString() + "myopenid/";
        try {
            return new URI(base);
        } catch (URISyntaxException e) {
            new RuntimeException(e);
        }
        return null;
    }

    public URI getNewUserRegistrationLink() {

        String base = this.gateway.getBaseSecureURL().toString() + "ac/guest/secure/registration.html";
        try {
            return new URI(base);
        } catch (URISyntaxException e) {
            new RuntimeException(e);
        }
        return null;
    }
}
