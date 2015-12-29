package sgf.gateway.service.publishing.api;

/**
 * The Class PublishingException.
 */
public class PublishingException extends RuntimeException {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 9135709171648630014L;

    /**
     * Instantiates a new publishing exception.
     */
    public PublishingException() {

        super();
    }

    /**
     * The Constructor.
     *
     * @param s         the s
     * @param throwable the throwable
     */
    public PublishingException(String s, Throwable throwable) {

        super(s, throwable);
    }

    /**
     * The Constructor.
     *
     * @param s the s
     */
    public PublishingException(String s) {

        super(s);
    }

    /**
     * The Constructor.
     *
     * @param throwable the throwable
     */
    public PublishingException(Throwable throwable) {

        super(throwable);
    }
}
