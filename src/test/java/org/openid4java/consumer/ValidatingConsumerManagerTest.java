package org.openid4java.consumer;

import org.apache.commons.lang.NotImplementedException;
import org.junit.Before;
import org.junit.Test;
import org.openid4java.consumer.validation.IDPValidator;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.message.MessageException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class ValidatingConsumerManagerTest {

    private static final URI TEST_IDP = URI.create("https://myopenid.com/");

    private ValidatingConsumerManager validatingConsumerManager;

    @Before
    public void setUp() throws Exception {

        this.validatingConsumerManager = new ValidatingConsumerManager(null, true);
    }

    @Test(expected = NotImplementedException.class)
    public void testAuthenticateListString() throws ConsumerException, MessageException {

        List discoveries = null;

        this.validatingConsumerManager.authenticate(discoveries, "");
    }

    @Test(expected = NotImplementedException.class)
    public void testAuthenticateListStringString() throws ConsumerException, MessageException {

        List discoveries = null;

        this.validatingConsumerManager.authenticate(discoveries, "", "");

    }


    @Test(expected = ConsumerException.class)
    public void testGetIDPEndpointWithNullDiscovery() throws ConsumerException {

        this.validatingConsumerManager.getIDPEndpoint(null);
    }

    @Test(expected = ConsumerException.class)
    public void testGetIDPEndpointWithInvalidURI() throws ConsumerException, MalformedURLException {

        DiscoveryInformation mockDiscoveryInformation = mock(DiscoveryInformation.class);

        // The space causes the url not to be a valid uri.
        when(mockDiscoveryInformation.getOPEndpoint()).thenReturn(new URL("http://test.com /"));

        this.validatingConsumerManager.getIDPEndpoint(mockDiscoveryInformation);
    }

    @Test
    public void testGetIDPEndpointWithValidURI() throws MalformedURLException, ConsumerException, URISyntaxException {

        DiscoveryInformation mockDiscoveryInformation = mock(DiscoveryInformation.class);

        // removed the space from the url above.
        when(mockDiscoveryInformation.getOPEndpoint()).thenReturn(new URL("http://test.com/"));

        URI discovery = this.validatingConsumerManager.getIDPEndpoint(mockDiscoveryInformation);

        assertThat(discovery, is(new URI("http://test.com/")));
    }

    @Test
    public void testValidationDisabled() throws ConsumerException, MessageException, URISyntaxException {

        IDPValidator mockIdpValidator = mock(IDPValidator.class);

        this.validatingConsumerManager = new ValidatingConsumerManager(mockIdpValidator, false);

        this.validatingConsumerManager.validateProvider(TEST_IDP);

        verify(mockIdpValidator, never()).validate(TEST_IDP);

    }

    @Test
    public void testValidationEnabled() throws ConsumerException, MessageException, URISyntaxException {

        IDPValidator mockIdpValidator = mock(IDPValidator.class);

        this.validatingConsumerManager = new ValidatingConsumerManager(mockIdpValidator, true);

        this.validatingConsumerManager.validateProvider(TEST_IDP);

        verify(mockIdpValidator, times(1)).validate(TEST_IDP);

    }
}
