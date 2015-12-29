package org.openid4java.consumer.validation.impl;

import org.junit.Before;
import org.junit.Test;
import org.openid4java.consumer.validation.UnsupportedIDPException;

import java.net.URI;

public class SecureSchemeValidatorTest {

    private static final URI SECURE_IDP = URI.create("https://myopenid.com/server");
    private static final URI INSECURE_IDP = URI.create("http://myopenid.com/server");

    private SecureSchemeValidator securePortValidator;

    @Before
    public void setUp() throws Exception {
        this.securePortValidator = new SecureSchemeValidator();
    }

    @Test(expected = UnsupportedIDPException.class)
    public void testValiadateHTTPFails() {

        this.securePortValidator.validate(INSECURE_IDP);
    }

    @Test
    public void testValiadateHTTPSSuceeds() {

        this.securePortValidator.validate(SECURE_IDP);
    }

}
