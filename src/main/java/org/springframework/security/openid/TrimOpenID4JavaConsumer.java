package org.springframework.security.openid;

import javax.servlet.http.HttpServletRequest;

import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;

public class TrimOpenID4JavaConsumer extends OpenID4JavaConsumer {

    public TrimOpenID4JavaConsumer() throws ConsumerException {

        super();
    }

    public TrimOpenID4JavaConsumer(ConsumerManager consumerManager, AxFetchListFactory axFetchListFactory) throws ConsumerException {

        super(consumerManager, axFetchListFactory);
    }

    public String beginConsumption(HttpServletRequest req, String identityUrl, String returnToUrl, String realm) throws OpenIDConsumerException {

        identityUrl = identityUrl.trim();

        String string = super.beginConsumption(req, identityUrl, returnToUrl, realm);

        return string;
    }

    public OpenIDAuthenticationToken endConsumption(HttpServletRequest request) throws OpenIDConsumerException {

        OpenIDAuthenticationToken openIDAuthenticationToken = super.endConsumption(request);

        return openIDAuthenticationToken;
    }
}
