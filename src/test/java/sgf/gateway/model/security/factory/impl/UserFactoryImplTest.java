package sgf.gateway.model.security.factory.impl;

import esg.saml.attr.service.api.SAMLAttributes;
import org.junit.Test;
import org.safehaus.uuid.UUID;
import sgf.gateway.dao.NewInstanceIdentifierStrategy;
import sgf.gateway.dao.impl.hibernate.VersionedUUIDIdentifier;
import sgf.gateway.model.security.User;
import sgf.gateway.saml.attr.service.impl.esg.RemoteAttributesServiceProxySamlSoapImpl;
import sgf.gateway.service.yadis.impl.RemoteYadisProxyImpl;

import java.net.MalformedURLException;
import java.net.URI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserFactoryImplTest {

    private static final String REMOTE_SAML_ATTRIBUTE_ENDPOINT = "https://test.com/remoteSamlAttributeEndpoint";

    private static final String TEST_OPENID = "https://test.com/openid/testuser";

    private static final UUID NEW_IDENTIFIER = UUID.valueOf("6766e0be-9a1b-4fda-ab80-258c17f9b9c5");

    @Test
    public void testCreateUserString() throws MalformedURLException, IllegalArgumentException {

        RemoteYadisProxyImpl mockRemoteYadisProxyImpl = mock(RemoteYadisProxyImpl.class);

        URI testAttributeEndpoint = URI.create(REMOTE_SAML_ATTRIBUTE_ENDPOINT);

        when(mockRemoteYadisProxyImpl.getSamlAttributeEndpoint(TEST_OPENID)).thenReturn(testAttributeEndpoint);

        RemoteAttributesServiceProxySamlSoapImpl mockProxy = mock(RemoteAttributesServiceProxySamlSoapImpl.class);

        SAMLAttributes mockAttributes = mock(SAMLAttributes.class);
        when(mockAttributes.getFirstName()).thenReturn("FirstNameTest");
        when(mockAttributes.getLastName()).thenReturn("LastNameTest");
        when(mockAttributes.getEmail()).thenReturn("email@test.com");

        when(mockProxy.getUserAttributes(TEST_OPENID, testAttributeEndpoint)).thenReturn(mockAttributes);

        VersionedUUIDIdentifier mockIdentifier = mock(VersionedUUIDIdentifier.class);

        when(mockIdentifier.getVersion()).thenReturn(null);
        when(mockIdentifier.getIdentifierValue()).thenReturn(NEW_IDENTIFIER);

        NewInstanceIdentifierStrategy mockNewInstanceIdentifierStrategy = mock(NewInstanceIdentifierStrategy.class);

        when(mockNewInstanceIdentifierStrategy.generateNewIdentifier(User.class)).thenReturn(mockIdentifier);

        UserFactoryImpl userFactoryImpl = new UserFactoryImpl(mockNewInstanceIdentifierStrategy, mockRemoteYadisProxyImpl, mockProxy);

        User user = userFactoryImpl.createRemoteUser(TEST_OPENID);

        assertThat("New remote user", user, is(notNullValue()));
        assertThat(user.getIdentifier(), is(equalTo(NEW_IDENTIFIER)));
        assertThat(user.getFirstName(), is(equalTo("FirstNameTest")));
        assertThat(user.getLastName(), is(equalTo("LastNameTest")));
        assertThat(user.getEmail(), is(equalTo("email@test.com")));
        assertThat(user.getOpenid(), is(equalTo(TEST_OPENID)));
    }

    @Test
    public void testCreateRemoteUserViaRemoteUserRequest() {

        VersionedUUIDIdentifier mockIdentifier = mock(VersionedUUIDIdentifier.class);

        when(mockIdentifier.getVersion()).thenReturn(null);
        when(mockIdentifier.getIdentifierValue()).thenReturn(NEW_IDENTIFIER);

        NewInstanceIdentifierStrategy mockNewInstanceIdentifierStrategy = mock(NewInstanceIdentifierStrategy.class);

        when(mockNewInstanceIdentifierStrategy.generateNewIdentifier(User.class)).thenReturn(mockIdentifier);

        UserFactoryImpl userFactoryImpl = new UserFactoryImpl(mockNewInstanceIdentifierStrategy, null, null);

        User user = userFactoryImpl.createRemoteUser("openid", "firstName", "lastName", "email");

        assertThat(user, is(notNullValue()));
        assertThat(user.getIdentifier(), is(equalTo(NEW_IDENTIFIER)));
        assertThat(user.getFirstName(), is(equalTo("firstName")));
        assertThat(user.getLastName(), is(equalTo("lastName")));
        assertThat(user.getEmail(), is(equalTo("email")));
        assertThat(user.getOpenid(), is(equalTo("openid")));
    }
}
