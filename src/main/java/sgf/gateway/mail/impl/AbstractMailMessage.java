package sgf.gateway.mail.impl;

import org.apache.commons.lang.NotImplementedException;
import sgf.gateway.mail.MailException;
import sgf.gateway.mail.MailMessage;
import sgf.gateway.model.security.User;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMailMessage implements MailMessage {

    /**
     * The file attachment map.
     */
    private Map<String, File> fileAttachmentMap;

    /**
     * The bcc list.
     */
    private List<InternetAddress> bccList;

    /**
     * The cc list.
     */
    private List<InternetAddress> ccList;

    /**
     * The to list.
     */
    private List<InternetAddress> toList;

    /**
     * The from internet address.
     */
    private InternetAddress fromInternetAddress;

    /**
     * The reply internet address.
     */
    private InternetAddress replyInternetAddress;

    /**
     * The plain text.
     */
    private String plainText;

    /**
     * The subject.
     */
    private String subject;

    /**
     * Instantiates a new abstract mail message.
     */
    public AbstractMailMessage() {

        this.fileAttachmentMap = new HashMap<String, File>();

        this.bccList = new ArrayList<InternetAddress>();
        this.ccList = new ArrayList<InternetAddress>();
        this.toList = new ArrayList<InternetAddress>();
    }

    /**
     * {@inheritDoc}
     */
    public void addAttachment(final String attachmentFilename, final File file) {

        throw new NotImplementedException("Attachments have not been implemented yet.");
    }

    /**
     * {@inheritDoc}
     */
    public void addBcc(final User bcc) {

        this.bccList.add(createInternetAddress(bcc));
    }

    /**
     * {@inheritDoc}
     */
    public void addBcc(final InternetAddress bcc) {

        this.bccList.add(bcc);
    }

    /**
     * {@inheritDoc}
     */
    public void addBcc(final String bcc) {

        try {

            this.bccList.add(new InternetAddress(bcc));

        } catch (MessagingException e) {
            throw new MailException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void addBcc(final String bcc, final String personal) {

        try {

            this.bccList.add(new InternetAddress(bcc, personal));

        } catch (UnsupportedEncodingException e) {
            throw new MailException(e);
        }
    }

    /**
     * Gets the bcc.
     *
     * @return the bcc
     */
    public List<InternetAddress> getBcc() {

        return this.bccList;
    }

    /**
     * {@inheritDoc}
     */
    public void addCc(final User cc) {

        this.ccList.add(createInternetAddress(cc));
    }

    /**
     * {@inheritDoc}
     */
    public void addCc(final InternetAddress cc) {

        this.ccList.add(cc);
    }

    /**
     * {@inheritDoc}
     */
    public void addCc(final String cc) {

        try {

            this.ccList.add(new InternetAddress(cc));

        } catch (MessagingException e) {
            throw new MailException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void addCc(final String cc, final String personal) {

        try {

            this.ccList.add(new InternetAddress(cc, personal));

        } catch (UnsupportedEncodingException e) {
            throw new MailException(e);
        }
    }

    /**
     * Gets the cc.
     *
     * @return the cc
     */
    public List<InternetAddress> getCc() {

        return this.ccList;
    }

    /**
     * {@inheritDoc}
     */
    public void addTo(final User to) {

        this.toList.add(createInternetAddress(to));
    }

    /**
     * {@inheritDoc}
     */
    public void addTo(final InternetAddress to) {

        this.toList.add(to);
    }

    /**
     * {@inheritDoc}
     */
    public void addTo(final String to) {

        try {

            this.toList.add(new InternetAddress(to));

        } catch (MessagingException e) {
            throw new MailException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void addTo(final String to, final String personal) {

        try {

            this.toList.add(new InternetAddress(to, personal));

        } catch (UnsupportedEncodingException e) {
            throw new MailException(e);
        }
    }

    /**
     * Gets the to.
     *
     * @return the to
     */
    public List<InternetAddress> getTo() {

        return this.toList;
    }

    /**
     * {@inheritDoc}
     */
    public abstract MimeMessage createMimeMessage(MimeMessage mimeMessage);

    /**
     * Checks for attachments.
     *
     * @return true, if successful
     */
    public boolean hasAttachments() {

        boolean test = false;

        if (!this.fileAttachmentMap.isEmpty()) {

            test = true;
        }

        return test;
    }

    /**
     * {@inheritDoc}
     */
    public void setFrom(final User from) {

        this.fromInternetAddress = createInternetAddress(from);
    }

    /**
     * {@inheritDoc}
     */
    public void setFrom(final InternetAddress from) {

        this.fromInternetAddress = from;
    }

    /**
     * {@inheritDoc}
     */
    public void setFrom(final String from) {

        try {

            this.fromInternetAddress = new InternetAddress(from);

        } catch (MessagingException e) {
            throw new MailException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void setFrom(final String from, final String personal) {

        try {

            this.fromInternetAddress = new InternetAddress(from, personal);

        } catch (UnsupportedEncodingException e) {
            throw new MailException(e);
        }
    }

    /**
     * Gets the from.
     *
     * @return the from
     */
    public InternetAddress getFrom() {

        return this.fromInternetAddress;
    }

    /**
     * {@inheritDoc}
     */
    public void setPlainText(final String text) {

        this.plainText = text;
    }

    /**
     * Gets the plain text.
     *
     * @return the plain text
     */
    public String getPlainText() {

        return this.plainText;
    }

    /**
     * {@inheritDoc}
     */
    public void setReplyTo(final User replyTo) {

        this.replyInternetAddress = createInternetAddress(replyTo);
    }

    /**
     * {@inheritDoc}
     */
    public void setReplyTo(final InternetAddress replyTo) {

        this.replyInternetAddress = replyTo;
    }

    /**
     * {@inheritDoc}
     */
    public void setReplyTo(final String replyTo) {

        try {

            this.replyInternetAddress = new InternetAddress(replyTo);

        } catch (MessagingException e) {
            throw new MailException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void setReplyTo(final String replyTo, final String personal) {

        try {

            this.replyInternetAddress = new InternetAddress(replyTo, personal);

        } catch (UnsupportedEncodingException e) {
            throw new MailException(e);
        }
    }

    /**
     * Gets the reply to.
     *
     * @return the reply to
     */
    public InternetAddress getReplyTo() {

        return this.replyInternetAddress;
    }

    /**
     * {@inheritDoc}
     */
    public void setSubject(final String subject) {

        this.subject = subject;
    }

    /**
     * Gets the subject.
     *
     * @return the subject
     */
    public String getSubject() {

        String returnSubject;

        if (this.subject != null) {

            returnSubject = this.subject;

        } else {

            returnSubject = "No Subject";
        }

        return returnSubject;
    }

    /**
     * Creates the internet address.
     *
     * @param user the user
     * @return the internet address
     */
    public InternetAddress createInternetAddress(final User user) {

        InternetAddress internetAddress;

        String personal = user.getFirstName() + " " + user.getLastName();

        try {

            internetAddress = new InternetAddress(user.getEmail(), personal);

        } catch (UnsupportedEncodingException e) {
            throw new MailException(e);
        }

        return internetAddress;
    }

}
