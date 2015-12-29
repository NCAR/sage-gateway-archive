package sgf.gateway.service.yadis.impl;

import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.xrds.XrdsServiceEndpoint;
import org.openid4java.discovery.yadis.YadisResolver;
import org.openid4java.discovery.yadis.YadisResult;
import org.openid4java.util.HttpCache;
import org.openid4java.util.HttpRequestOptions;
import sgf.gateway.service.yadis.RemoteYadisProxy;
import sgf.gateway.service.yadis.RemoteYadisProxyException;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

public class RemoteYadisProxyImpl implements RemoteYadisProxy {

    private final YadisResolver yadisResolver;

    private HttpCache httpCache;

    public RemoteYadisProxyImpl(final YadisResolver yadisResolver) {

        this.yadisResolver = yadisResolver;
        this.httpCache = new HttpCache();
        HttpRequestOptions httpRequestOptions = httpCache.getDefaultRequestOptions();
        // Set timeout in milliseconds.
        httpRequestOptions.setConnTimeout(60000);
        httpRequestOptions.setSocketTimeout(60000);
    }

    @Override
    public URI getSamlAttributeEndpoint(String openId) {

        Set<String> serviceTypes = new HashSet<String>();

        serviceTypes.add("urn:esg:security:attribute-service");

        URI samlAttributeEndpoint;

        try {

            YadisResult yadisResult = this.yadisResolver.discover(openId, 10, this.httpCache, serviceTypes);

            XrdsServiceEndpoint endpoint = (XrdsServiceEndpoint) yadisResult.getEndpoints().get(0);

            samlAttributeEndpoint = URI.create(endpoint.getUri());

        } catch (DiscoveryException e) {

            throw new RemoteYadisProxyException(openId, e);
        }

        return samlAttributeEndpoint;
    }
}
