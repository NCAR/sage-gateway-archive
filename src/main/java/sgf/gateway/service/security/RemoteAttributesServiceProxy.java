package sgf.gateway.service.security;

import esg.saml.attr.service.api.SAMLAttributes;

import java.net.URI;

/**
 * Client-side interface for a invoking a {@link RemoteAttributesService} with a variable endpoint.
 */
public interface RemoteAttributesServiceProxy {

    /**
     * Invokes the method getUserInfo() on the given remote service endpoint.
     */
    String[] getUserInfo(String openid, URI endpoint);

    /**
     * Invokes the method getUserAttributes() on the given remote service endpoint.
     */
    SAMLAttributes getUserAttributes(String openid, URI endpoint);

}
