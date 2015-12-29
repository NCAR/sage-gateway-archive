package org.openid4java.consumer.validation;

import java.net.URI;

public interface IDPValidator {

    void validate(URI opEndPoint);

}
