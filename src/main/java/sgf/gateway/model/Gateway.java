package sgf.gateway.model;

import org.safehaus.uuid.UUID;

import java.net.URI;
import java.util.Date;

public interface Gateway {

    UUID getIdentifier();

    String getName();

    String getDescription();

    String getSystemMessage();

    URI getBaseURL();

    String getHostName();

    URI getBaseSecureURL();

    String getIdentity();

    String getDefaultProjectViewHandlerName();

    String getDefaultCollectionViewHandlerName();

    Date getDateUpdated();

    URI getAttributesServiceURL();

    URI getOaiRepositoryURL();

    // the administrator's personalized name for sending emails from the Gateway.
    String getAdministratorPersonal();

    // the administrator's address for sending emails from the Gateway.
    String getAdministratorEmail();

    // the administrator's address for sending exception emails from the Gateway.
    String getAdministratorEmailException();

    String getMyProxyEndpoint();

    String getIdpEndpoint();

    URI getOpenIdProviderEndpoint();

    URI getAttributeServiceEndpoint();

    String getVersionStr();
}
