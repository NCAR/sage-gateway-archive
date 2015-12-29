package sgf.gateway.mail;

public class MailException extends RuntimeException {

    public MailException(String message) {

        super(message);
    }

    public MailException(Throwable cause) {

        super(cause);
    }
}
