package sgf.gateway.model.security.factory.impl;

import esg.saml.attr.service.api.SAMLAttributes;
import org.springframework.util.Assert;
import sgf.gateway.dao.NewInstanceIdentifierStrategy;
import sgf.gateway.dao.impl.hibernate.VersionedUUIDIdentifier;
import sgf.gateway.model.Identifier;
import sgf.gateway.model.security.User;
import sgf.gateway.model.security.factory.UserFactory;
import sgf.gateway.service.security.RemoteAttributesServiceProxy;
import sgf.gateway.service.yadis.RemoteYadisProxy;

import java.net.URI;

public class UserFactoryImpl implements UserFactory {

    private final NewInstanceIdentifierStrategy newInstanceIdentifierStrategy;

    private final RemoteYadisProxy remoteYadisProxy;

    private final RemoteAttributesServiceProxy remoteAttributesServiceProxy;

    public UserFactoryImpl(NewInstanceIdentifierStrategy newInstanceIdentifierStrategy, RemoteYadisProxy remoteYadisProxy, RemoteAttributesServiceProxy remoteAttributesServiceProxy) {

        this.newInstanceIdentifierStrategy = newInstanceIdentifierStrategy;
        this.remoteYadisProxy = remoteYadisProxy;
        this.remoteAttributesServiceProxy = remoteAttributesServiceProxy;
    }

    @Override
    public User createUser(String username, String openId, String firstName, String lastName, String email) {

        Assert.hasText(username, "Username is required for all User instances.");
        Assert.hasText(openId, "OpenID is required for all User instances.");
        Assert.hasText(firstName, "First Name is required for all User instances.");
        Assert.hasText(lastName, "Last Name is required for all User instances.");
        Assert.hasText(email, "Email is required for all User instances.");

        Identifier newIdentifier = this.newInstanceIdentifierStrategy.generateNewIdentifier(User.class);

        // Temporary Cast
        VersionedUUIDIdentifier vuId = (VersionedUUIDIdentifier) newIdentifier;

        User user = new User(vuId.getIdentifierValue(), vuId.getVersion(), firstName, lastName, email, username, openId);

        return user;
    }

    public User createRemoteUser(String openId, String firstName, String lastName, String email) {

        Assert.hasText(openId, "OpenID is required for all User instances.");
        Assert.hasText(firstName, "First Name is required for all User instances.");
        Assert.hasText(lastName, "Last Name is required for all User instances.");
        Assert.hasText(email, "Email is required for all User instances.");

        Identifier newIdentifier = this.newInstanceIdentifierStrategy.generateNewIdentifier(User.class);

        VersionedUUIDIdentifier vuId = (VersionedUUIDIdentifier) newIdentifier;

        User user = new User(vuId.getIdentifierValue(), vuId.getVersion(), firstName, lastName, email, null, openId);

        return user;
    }

    public User createRemoteUser(String openId) {

        Assert.hasText(openId, "OpenID is required for all User instances.");

        URI samlAttributeEndpoint = this.remoteYadisProxy.getSamlAttributeEndpoint(openId);

        User user;

        SAMLAttributes attributes = this.remoteAttributesServiceProxy.getUserAttributes(openId, samlAttributeEndpoint);

        String firstName = attributes.getFirstName();
        String lastName = attributes.getLastName();
        String email = attributes.getEmail();

        Identifier newIdentifier = this.newInstanceIdentifierStrategy.generateNewIdentifier(User.class);

        VersionedUUIDIdentifier vuId = (VersionedUUIDIdentifier) newIdentifier;

        user = new User(vuId.getIdentifierValue(), vuId.getVersion(), firstName, lastName, email, null, openId);

        return user;
    }
}
