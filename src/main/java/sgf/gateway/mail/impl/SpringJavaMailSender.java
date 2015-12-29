package sgf.gateway.mail.impl;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import sgf.gateway.mail.MailMessage;
import sgf.gateway.mail.MailServer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * The Class SpringJavaMailSender.
 */
public class SpringJavaMailSender implements MailServer {

    /**
     * The java mail sender.
     */
    private final JavaMailSender javaMailSender;

    /**
     * Instantiates a new spring java mail sender.
     *
     * @param javaMailSender the java mail sender
     */
    public SpringJavaMailSender(JavaMailSender javaMailSender) {

        this.javaMailSender = javaMailSender;
    }

    /**
     * {@inheritDoc}
     */
    public void send(final MailMessage mailMessage) {

        MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {

            public void prepare(final MimeMessage mimeMessage) throws MessagingException {

                mailMessage.createMimeMessage(mimeMessage);
            }
        };

        this.javaMailSender.send(mimeMessagePreparator);
    }
}
