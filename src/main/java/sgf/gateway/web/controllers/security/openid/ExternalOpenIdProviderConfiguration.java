package sgf.gateway.web.controllers.security.openid;

import org.apache.commons.lang.NotImplementedException;

import java.net.URI;

public class ExternalOpenIdProviderConfiguration implements OpenIdConfiguration {

    private URI newUserRegistrationLink;

    public ExternalOpenIdProviderConfiguration(URI newUserRegistrationLink) {
        super();
        this.newUserRegistrationLink = newUserRegistrationLink;
    }

    public URI getProviderEndPoint() {
        return URI.create("http://localhost/doesn-not-exist");
    }

    public URI getBaseOpenIdURI() {
        throw new NotImplementedException();
    }

    public URI getNewUserRegistrationLink() {
        return newUserRegistrationLink;
    }

}
