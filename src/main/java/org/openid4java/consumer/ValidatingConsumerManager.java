package org.openid4java.consumer;

import org.apache.commons.lang.NotImplementedException;
import org.openid4java.consumer.validation.IDPValidator;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.MessageException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class ValidatingConsumerManager extends ConsumerManager {

    private IDPValidator idpValidator;
    private boolean enforceValidation;

    public ValidatingConsumerManager(IDPValidator idpValidator, boolean enforceValidation) throws ConsumerException {

        this.idpValidator = idpValidator;
        this.enforceValidation = enforceValidation;
    }

    @Override
    public AuthRequest authenticate(DiscoveryInformation discovered, String returnToUrl) throws MessageException, ConsumerException {

        URI idpEnpoint = this.getIDPEndpoint(discovered);

        this.validateProvider(idpEnpoint);

        return super.authenticate(discovered, returnToUrl);
    }

    @Override
    public AuthRequest authenticate(DiscoveryInformation discovered, String returnToUrl, String realm) throws MessageException, ConsumerException {

        URI idpEnpoint = this.getIDPEndpoint(discovered);

        this.validateProvider(idpEnpoint);

        return super.authenticate(discovered, returnToUrl, realm);
    }

    @Override
    public AuthRequest authenticate(List discoveries, String returnToUrl) throws ConsumerException, MessageException {

        throw new NotImplementedException("Validation for raw discoveries has not yet been implemented.");
    }

    @Override
    public AuthRequest authenticate(List discoveries, String returnToUrl, String realm) throws ConsumerException, MessageException {

        throw new NotImplementedException("Validation for raw discoveries has not yet been implemented.");
    }

    protected URI getIDPEndpoint(DiscoveryInformation discovered) throws ConsumerException {

        if (discovered == null) {

            throw new ConsumerException("Invalid OPEndpoint: no endpoint discovered");
        }

        URI result;

        try {

            result = discovered.getOPEndpoint().toURI();

        } catch (URISyntaxException e) {

            throw new ConsumerException("Invalid OPEndpoint: " + discovered.getOPEndpoint().toString());
        }

        return result;
    }

    protected void validateProvider(URI opEndPoint) {

        if (this.enforceValidation) {

            this.idpValidator.validate(opEndPoint);
        }
    }

}
