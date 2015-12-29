package sgf.gateway.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.URI;

/**
 * This class is used by the Spring configuration to get a singleton instance
 * of the location specific Gateway.
 */
public class GatewayManager {

    private static final Log LOG = LogFactory.getLog(GatewayManager.class);

    private String name;
    private URI baseURI;
    private URI baseSecureURI;
    private String version;
    private String myProxyEndpoint;
    private String administratorEmail;
    private String administratorEmailException;
    private String administratorPersonal;
    private String distinguishedName;

    public GatewayManager(String name, URI baseURI, URI baseSecureURI, String version, String myProxyEndpoint, String administratorEmail, String administratorEmailException,
                          String administratorPersonal, String distinguishedName) {
        this.name = name;
        this.baseURI = baseURI;
        this.baseSecureURI = baseSecureURI;
        this.version = version;
        this.myProxyEndpoint = myProxyEndpoint;
        this.administratorEmail = administratorEmail;
        this.administratorEmailException = administratorEmailException;
        this.administratorPersonal = administratorPersonal;
        this.distinguishedName = distinguishedName;
    }

    public GatewayImpl getGatewayInstance() {

        GatewayImpl gateway = new GatewayImpl(null, this.name);

        gateway.setBaseURL(this.baseURI);
        gateway.setBaseSecureURL(this.baseSecureURI);
        gateway.setIdpEndpoint(this.baseSecureURI.toString() + "openid/provider.html");
        gateway.setVersionStr(this.version);
        gateway.setMyProxyEndpoint(this.myProxyEndpoint);
        gateway.setAdministratorEmail(this.administratorEmail);
        gateway.setAdministratorEmailException(this.administratorEmailException);
        gateway.setAdministratorPersonal(this.administratorPersonal);
        gateway.setIdentity(this.distinguishedName);

        LOG.debug("Loaded Gateway instance with endpoints: " + gateway.getBaseURL() + " " + gateway.getBaseSecureURL());

        return gateway;
    }
}
