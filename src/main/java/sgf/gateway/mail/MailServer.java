package sgf.gateway.mail;

public interface MailServer {

    /**
     * If anything goes wrong when send a MailMessage a MailException will be thrown.
     *
     * @param mailMessage the mail message
     */
    void send(MailMessage mailMessage);
}
