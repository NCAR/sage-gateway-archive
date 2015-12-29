package sgf.gateway.saml;

import java.net.URI;

public class SAMLException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final URI endpoint;

    private final String openid;

    public SAMLException(String message, URI endpoint, String openid) {

        super(message);
        this.endpoint = endpoint;
        this.openid = openid;
    }

    public SAMLException(Throwable cause, URI endpoint, String openid) {

        super(cause);
        this.endpoint = endpoint;
        this.openid = openid;
    }

    public String getMessage() {

        StringBuilder stringBuilder = new StringBuilder(super.getMessage());
        stringBuilder.append(" ");
        stringBuilder.append("Endpoint: " + endpoint);
        stringBuilder.append(" ");
        stringBuilder.append("Openid: " + openid);

        String message = stringBuilder.toString();

        return message;
    }

    public URI getEndpoint() {

        return this.endpoint;
    }

    public String getOpenID() {

        return this.openid;
    }
}
