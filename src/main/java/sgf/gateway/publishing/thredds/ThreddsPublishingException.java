package sgf.gateway.publishing.thredds;

public class ThreddsPublishingException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ThreddsPublishingException() {
        super();
    }

    public ThreddsPublishingException(final Throwable cause) {
        super(cause);
    }
}
