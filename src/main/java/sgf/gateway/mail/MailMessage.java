package sgf.gateway.mail;

import sgf.gateway.model.security.User;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;

public interface MailMessage {

    /**
     * Adds the attachment.
     *
     * @param attachmentFilename the attachment filename
     * @param file               the file
     */
    void addAttachment(String attachmentFilename, File file);

    /**
     * Adds the bcc.
     *
     * @param bcc the bcc
     */
    void addBcc(User bcc);

    /**
     * Adds the bcc.
     *
     * @param bcc the bcc
     */
    void addBcc(InternetAddress bcc);

    /**
     * Adds the bcc.
     *
     * @param bcc the bcc
     */
    void addBcc(String bcc);

    /**
     * Adds the bcc.
     *
     * @param bcc      the bcc
     * @param personal the personal
     */
    void addBcc(String bcc, String personal);

    List<InternetAddress> getBcc();

    /**
     * Adds the cc.
     *
     * @param cc the cc
     */
    void addCc(User cc);

    /**
     * Adds the cc.
     *
     * @param cc the cc
     */
    void addCc(InternetAddress cc);

    /**
     * Adds the cc.
     *
     * @param cc the cc
     */
    void addCc(String cc);

    /**
     * Adds the cc.
     *
     * @param cc       the cc
     * @param personal the personal
     */
    void addCc(String cc, String personal);

    List<InternetAddress> getCc();

    /**
     * Adds the to.
     *
     * @param to the to
     */
    void addTo(User to);

    /**
     * Adds the to.
     *
     * @param to the to
     */
    void addTo(InternetAddress to);

    /**
     * Adds the to.
     *
     * @param to the to
     */
    void addTo(String to);

    /**
     * Adds the to.
     *
     * @param to       the to
     * @param personal the personal
     */
    void addTo(String to, String personal);

    List<InternetAddress> getTo();

    /**
     * Creates the mime message.
     *
     * @param mimeMessage the mime message
     * @return the mime message
     */
    MimeMessage createMimeMessage(MimeMessage mimeMessage);

    /**
     * Sets the from.
     *
     * @param from the new from
     */
    void setFrom(User from);

    /**
     * Sets the from.
     *
     * @param from the new from
     */
    void setFrom(InternetAddress from);

    /**
     * Sets the from.
     *
     * @param from the new from
     */
    void setFrom(String from);

    /**
     * Sets the from.
     *
     * @param from     the from
     * @param personal the personal
     */
    void setFrom(String from, String personal);

    InternetAddress getFrom();

    /**
     * Sets the plain text.
     *
     * @param text the new plain text
     */
    void setPlainText(String text);

    String getPlainText();

    /**
     * Sets the reply to.
     *
     * @param replyTo the new reply to
     */
    void setReplyTo(User replyTo);

    /**
     * Sets the reply to.
     *
     * @param replyTo the new reply to
     */
    void setReplyTo(InternetAddress replyTo);

    /**
     * Sets the reply to.
     *
     * @param replyTo the new reply to
     */
    void setReplyTo(String replyTo);

    /**
     * Sets the reply to.
     *
     * @param replyTo  the reply to
     * @param personal the personal
     */
    void setReplyTo(String replyTo, String personal);

    InternetAddress getReplyTo();

    /**
     * Sets the subject.
     *
     * @param subject the new subject
     */
    void setSubject(String subject);

    String getSubject();
}
