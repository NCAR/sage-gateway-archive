package sgf.gateway.model;

import org.safehaus.uuid.UUID;

import java.net.URI;
import java.net.URISyntaxException;

public class GatewayImpl extends AbstractPersistableEntity implements Gateway {

    private static final String INTERNAL_IDP_ENDPOINT_PATH = "/openid/provider.html";

    private static final String INTERNAL_ATTRIBUTE_SERVICE_ENDPOINT_PATH = "/saml/soap/secure/attributeService.htm";

    private String name;

    private String description;

    private String versionStr;

    private String identity;

    private String systemMessage;

    private URI baseURL;

    private URI baseSecureURL;

    private URI attributesServiceURL;

    private URI oaiRepositoryURL;

    private String defaultProjectViewHandlerName;

    private String defaultCollectionViewHandlerName;

    /**
     * The personalized administrator name for sending emails from the Gateway.
     */
    private String administratorPersonal;

    /**
     * The email address of the gateway administrator. Used for sending emails from the Gateway.
     */
    private String administratorEmail;

    /**
     * The exception email address of the gateway administrator. Used for sending exception emails from the Gateway.
     */
    private String administratorEmailException;

    /**
     * The MyProxy endpoint for user proxy generation. Used for DML and other Globus Proxy clients.
     */
    private String myProxyEndpoint;

    /**
     * The Identity Provider endpoint for this gateway, used when creating local records for users authenticated via SSO.
     */
    private String idpEndpoint;

    public GatewayImpl() {
        super(false); // no generated UUID or just call the no arg constructor
    }

    public GatewayImpl(final UUID identifier) {
        super(identifier);
    }

    public GatewayImpl(final UUID identifier, final String name) {
        super(identifier);
        this.name = name;
    }

    public GatewayImpl(final String name, final String description, final String identity, final URI baseURL, final URI baseSecureURL) {
        super(true); // generated UUID
        this.name = name;
        this.description = description;
        this.baseURL = baseURL;
        this.baseSecureURL = baseSecureURL;
    }

    public GatewayImpl(final String name, final String description, final String identity, final UUID identifier, final URI baseURL, final URI baseSecureURL) {
        // TODO Make sure this is correct...
        super(identifier, 1);
        this.name = name;
        this.description = description;
        this.identity = identity;
        this.baseURL = baseURL;
        this.baseSecureURL = baseSecureURL;
    }

    @Override
    public String getHostName() {
        return this.getBaseURL().getHost();
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public String getSystemMessage() {
        return systemMessage;
    }

    public void setSystemMessage(final String systemMessage) {
        this.systemMessage = systemMessage;
    }

    @Override
    public URI getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(final URI baseURL) {
        this.baseURL = baseURL;
    }

    @Override
    public URI getBaseSecureURL() {
        return baseSecureURL;
    }

    public void setBaseSecureURL(final URI baseSecureURL) {
        this.baseSecureURL = baseSecureURL;
    }

    @Override
    public String getIdentity() {
        return identity;
    }

    public void setIdentity(final String identity) {
        this.identity = identity;
    }

    @Override
    public String getDefaultProjectViewHandlerName() {
        return defaultProjectViewHandlerName;
    }

    public void setDefaultProjectViewHandlerName(final String defaultProjectViewHandlerName) {
        this.defaultProjectViewHandlerName = defaultProjectViewHandlerName;
    }

    @Override
    public String getDefaultCollectionViewHandlerName() {
        return defaultCollectionViewHandlerName;
    }

    public void setDefaultCollectionViewHandlerName(final String defaultCollectionViewHandlerName) {
        this.defaultCollectionViewHandlerName = defaultCollectionViewHandlerName;
    }

    @Override
    public URI getAttributesServiceURL() {
        return attributesServiceURL;
    }

    public void setAttributesServiceURL(final URI attributesServiceURL) {
        this.attributesServiceURL = attributesServiceURL;
    }

    @Override
    public URI getOaiRepositoryURL() {
        return oaiRepositoryURL;
    }

    public void setOaiRepositoryURL(final URI oaiRepositoryURL) {
        this.oaiRepositoryURL = oaiRepositoryURL;
    }

    @Override
    public String getAdministratorPersonal() {
        return this.administratorPersonal;
    }

    public void setAdministratorPersonal(final String personal) {
        this.administratorPersonal = personal;
    }

    @Override
    public String getAdministratorEmail() {
        return this.administratorEmail;
    }

    public void setAdministratorEmail(final String address) {
        this.administratorEmail = address;
    }

    @Override
    public String getAdministratorEmailException() {
        return administratorEmailException;
    }

    public void setAdministratorEmailException(String address) {
        this.administratorEmailException = address;
    }

    @Override
    public String getMyProxyEndpoint() {
        return this.myProxyEndpoint;
    }

    public void setMyProxyEndpoint(String myProxyEndpoint) {
        this.myProxyEndpoint = myProxyEndpoint;
    }

    @Override
    public String getIdpEndpoint() {
        return idpEndpoint;
    }

    public void setIdpEndpoint(String idpEndpoint) {
        this.idpEndpoint = idpEndpoint;
    }

    @Override
    public String getVersionStr() {
        return versionStr;
    }

    public void setVersionStr(String version) {
        this.versionStr = version;
    }

    @Override
    public URI getOpenIdProviderEndpoint() {

        URI openIdProviderEndpoint = null;

        try {

            openIdProviderEndpoint = new URI(this.getBaseSecureURL().toString() + "/" + GatewayImpl.INTERNAL_IDP_ENDPOINT_PATH);
            openIdProviderEndpoint = openIdProviderEndpoint.normalize();

        } catch (URISyntaxException e) {

            new RuntimeException(e);
        }

        return openIdProviderEndpoint;
    }

    @Override
    public URI getAttributeServiceEndpoint() {
        URI endPoint = null;
        try {
            endPoint = new URI(this.getBaseSecureURL().toString() + "/" + GatewayImpl.INTERNAL_ATTRIBUTE_SERVICE_ENDPOINT_PATH);
            endPoint = endPoint.normalize();
        } catch (URISyntaxException e) {
            new RuntimeException(e);
        }
        return endPoint;
    }
}
