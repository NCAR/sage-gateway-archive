package org.openid4java.consumer.validation.impl;

import org.openid4java.consumer.validation.IDPValidator;
import org.openid4java.consumer.validation.UnsupportedIDPException;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.SpringSecurityMessageSource;

import java.net.URI;

public class SecureSchemeValidator implements IDPValidator, MessageSourceAware {

    private static final String SECURE_SCHEME = "https";

    private boolean enabled = true;

    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @Override
    public void validate(URI opEndPoint) {

        if (enabled) {

            String idpScheme = opEndPoint.getScheme();

            if (!SECURE_SCHEME.equalsIgnoreCase(idpScheme)) {

                throw new UnsupportedIDPException(messages.getMessage("SecureSchemeValidator.invalidScheme", "Invalid Scheme"));
            }
        }
    }

    public void setEnabled(final boolean enabled) {

        this.enabled = enabled;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {

        this.messages = new MessageSourceAccessor(messageSource);
    }

}
