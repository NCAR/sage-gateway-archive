package sgf.gateway.service.yadis;

public class RemoteYadisProxyException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String openid;

    public RemoteYadisProxyException(final String openid, final Throwable cause) {

        super(cause);

        this.openid = openid;
    }

    public String getMessage() {

        return "Error getting Yadis Document for OpenID: " + this.openid;
    }
}
