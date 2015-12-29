package sgf.gateway.web.controllers.security.openid;

import java.net.URI;

public interface OpenIdConfiguration {

    URI getProviderEndPoint();

    URI getBaseOpenIdURI();

    URI getNewUserRegistrationLink();

}
