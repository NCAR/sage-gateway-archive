package sgf.gateway.saml.attr.service.impl.esg;

import esg.saml.attr.service.api.SAMLAttributeServiceClient;
import esg.saml.attr.service.api.SAMLAttributes;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sgf.gateway.saml.SAMLException;
import sgf.gateway.service.security.RemoteAttributesServiceProxy;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URI;

/**
 * Implementation of {@link RemoteAttributesServiceProxy} that connects to a {@link SAMLService} with SOAP binding.
 */
public class RemoteAttributesServiceProxySamlSoapImpl implements RemoteAttributesServiceProxy {

    private static final Log LOG = LogFactory.getLog(RemoteAttributesServiceProxySamlSoapImpl.class);

    private final SAMLAttributeServiceClient samlAttributeServiceClient;

    private final HttpClient httpClient;

    public RemoteAttributesServiceProxySamlSoapImpl(SAMLAttributeServiceClient samlAttributeServiceClient, HttpClient httpClient) {

        this.samlAttributeServiceClient = samlAttributeServiceClient;
        this.httpClient = httpClient;
    }

    public SAMLAttributes getUserAttributes(String openid, URI endpoint) {

        // We also might consider to remove the use of cookies.
        // This is also the older version of http client and we might consider using the never version.

        PostMethod method = new PostMethod(endpoint.toString());
        method.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);

        SAMLAttributes response = null;

        try {

            // formulate SAML request
            String samlRequest = samlAttributeServiceClient.buildAttributeRequest(openid);

            if (LOG.isInfoEnabled()) {
                LOG.info("SAML request to " + endpoint.toString() + " =\n" + samlRequest);
            }

            // insert SAML AttributeRequest as HTTPRequest body
            byte[] bytes = samlRequest.getBytes();
            ByteArrayRequestEntity requestEntity = new ByteArrayRequestEntity(bytes);
            method.setRequestEntity(requestEntity);

            // Execute the method.
            int statusCode = httpClient.executeMethod(method);

            if (statusCode != HttpStatus.SC_OK) {

                throw new SAMLException("SAML/SOAP method failed: " + method.getStatusLine(), endpoint, openid);
            }

            // Read the response body.
            byte[] responseBody = method.getResponseBody();
            String samlResponse = new String(responseBody);

            if (LOG.isInfoEnabled()) {

                LOG.info("SAML response:\n" + samlResponse);
            }

            SAMLAttributes samlAttributes;

            synchronized (samlAttributeServiceClient) {

                samlAttributes = samlAttributeServiceClient.parseAttributeResponse(samlResponse);
            }


            response = samlAttributes;

        } catch (SocketTimeoutException e) { // FIXME Change to timeout exception.

            throw new SAMLException(e, endpoint, openid);

        } catch (ConnectException e) {

            throw new SAMLException(e, endpoint, openid);

        } catch (Exception e) {

            throw new RuntimeException(e);

        } finally {

            method.releaseConnection();
        }

        return response;
    }

    public String[] getUserInfo(String openid, URI endpoint) {

        // We also might consider to remove the use of cookies.
        // This is also the older version of http client and we might consider using the never version.

        PostMethod method = new PostMethod(endpoint.toString());
        method.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);

        String[] response = null;

        try {

            // formulate SAML request
            //String samlRequest = samlAttributeServiceClient.
            String samlRequest = samlAttributeServiceClient.buildAttributeRequest(openid);

            if (LOG.isInfoEnabled()) {
                LOG.info("SAML request to " + endpoint.toString() + " =\n" + samlRequest);
            }

            // insert SAML AttributeRequest as HTTPRequest body
            byte[] bytes = samlRequest.getBytes();
            ByteArrayRequestEntity requestEntity = new ByteArrayRequestEntity(bytes);
            method.setRequestEntity(requestEntity);

            // Execute the method.
            int statusCode = httpClient.executeMethod(method);

            if (statusCode != HttpStatus.SC_OK) {

                throw new SAMLException("SAML/SOAP method failed: " + method.getStatusLine(), endpoint, openid);
            }

            // Read the response body.
            byte[] responseBody = method.getResponseBody();
            String samlResponse = new String(responseBody);

            if (LOG.isInfoEnabled()) {

                LOG.info("SAML response:\n" + samlResponse);
            }

            SAMLAttributes samlAttributes;

            synchronized (samlAttributeServiceClient) {

                samlAttributes = samlAttributeServiceClient.parseAttributeResponse(samlResponse);
            }


            response = samlAttributes.getAttributes().toArray(new String[0]);

        } catch (SocketTimeoutException e) { // FIXME Change to timeout exception.

            throw new SAMLException(e, endpoint, openid);

        } catch (ConnectException e) {

            throw new SAMLException(e, endpoint, openid);

        } catch (Exception e) {

            throw new RuntimeException(e);

        } finally {

            method.releaseConnection();
        }

        return response;
    }
}
