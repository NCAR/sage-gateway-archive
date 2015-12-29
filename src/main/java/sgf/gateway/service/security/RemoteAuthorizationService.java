package sgf.gateway.service.security;

/**
 * String-based version of AuthorizationService API that is exposed to remoting clients. Note that the the resource is identified by one of its access URL, and
 * not one of its identifying URIs.
 */
public interface RemoteAuthorizationService {

    public boolean authorize(String openid, String resourceURL, String operation);

}
