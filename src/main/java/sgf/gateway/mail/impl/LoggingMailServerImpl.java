package sgf.gateway.mail.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sgf.gateway.mail.MailMessage;
import sgf.gateway.mail.MailServer;

import javax.mail.internet.InternetAddress;

public class LoggingMailServerImpl implements MailServer {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingMailServerImpl.class);

    public void send(MailMessage mailMessage) {

        if (LOG.isInfoEnabled()) {

            logMessage(mailMessage);
        }
    }

    public void logMessage(MailMessage mailMessage) {

        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append("Mail Message Sent\n");

        appendToRecipients(mailMessage, stringBuffer);
        appendCCRecipients(mailMessage, stringBuffer);
        appendBCCRecipients(mailMessage, stringBuffer);

        appendFrom(mailMessage, stringBuffer);

        appendReplyTo(mailMessage, stringBuffer);

        appendSubject(mailMessage, stringBuffer);

        appendMessage(mailMessage, stringBuffer);

        LOG.info(stringBuffer.toString());
    }

    public void appendSubject(MailMessage mailMessage, StringBuffer stringBuffer) {

        stringBuffer.append("Subject:\n");
        stringBuffer.append("    " + mailMessage.getSubject() + "\n");
    }

    public void appendMessage(MailMessage mailMessage, StringBuffer stringBuffer) {

        stringBuffer.append("Message:\n");
        stringBuffer.append(mailMessage.getPlainText() + "\n");
    }

    public void appendReplyTo(MailMessage mailMessage, StringBuffer stringBuffer) {

        if (mailMessage.getReplyTo() != null) {

            stringBuffer.append("Reply-To:\n");
            stringBuffer.append("    " + mailMessage.getReplyTo() + "\n");
        }
    }

    public void appendFrom(MailMessage mailMessage, StringBuffer stringBuffer) {

        stringBuffer.append("From:\n");
        stringBuffer.append("    " + mailMessage.getFrom() + "\n");
    }

    public void appendBCCRecipients(MailMessage mailMessage, StringBuffer stringBuffer) {

        if (!mailMessage.getBcc().isEmpty()) {

            stringBuffer.append("BCC Recipients:\n");

            for (InternetAddress internetAddress : mailMessage.getBcc()) {

                stringBuffer.append("    " + internetAddress.getPersonal() + " <" + internetAddress.getAddress() + ">\n");
            }
        }
    }

    public void appendCCRecipients(MailMessage mailMessage, StringBuffer stringBuffer) {

        if (!mailMessage.getCc().isEmpty()) {

            stringBuffer.append("CC Recipients:\n");

            for (InternetAddress internetAddress : mailMessage.getCc()) {

                stringBuffer.append("    " + internetAddress.getPersonal() + " <" + internetAddress.getAddress() + ">\n");
            }
        }
    }

    public void appendToRecipients(MailMessage mailMessage, StringBuffer stringBuffer) {

        if (!mailMessage.getTo().isEmpty()) {

            stringBuffer.append("TO Recipients:\n");

            for (InternetAddress internetAddress : mailMessage.getTo()) {

                stringBuffer.append("    " + internetAddress.getPersonal() + " <" + internetAddress.getAddress() + ">\n");
            }
        }
    }
}
