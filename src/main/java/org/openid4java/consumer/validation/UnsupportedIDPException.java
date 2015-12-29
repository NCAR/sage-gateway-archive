package org.openid4java.consumer.validation;

import org.springframework.security.core.AuthenticationException;

public class UnsupportedIDPException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    public UnsupportedIDPException(String message) {

        super(message);
    }

    public UnsupportedIDPException(String message, Throwable cause) {

        super(message, cause);
    }

}
