package sgf.gateway.mail.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import sgf.gateway.mail.MailException;
import sgf.gateway.mail.MailMessage;
import sgf.gateway.model.Gateway;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * MailMessage class using Freemarker templates for subject and text.
 */
public class FreeMarkerMailMessage extends AbstractMailMessage implements MailMessage {

    /**
     * The plain text message template.
     */
    private final String plainTextMessageTemplate;

    /**
     * The subject template.
     */
    private final String subjectTemplate;

    /**
     * The message map.
     */
    private Map<String, Object> messageMap = new HashMap<>();

    /**
     * The Freemarker Template Configuration.
     */
    private final Configuration configuration;

    private final Gateway gateway;

    /**
     * Instantiates a new free marker mail message.
     *
     * @param configuration            the configuration
     * @param gateway                  the gateway
     * @param subjectTemplate          the subject template
     * @param plainTextMessageTemplate the plain text message template
     */
    public FreeMarkerMailMessage(Configuration configuration, Gateway gateway, String subjectTemplate, String plainTextMessageTemplate) {

        this.configuration = configuration;
        this.gateway = gateway;
        this.subjectTemplate = subjectTemplate;
        this.plainTextMessageTemplate = plainTextMessageTemplate;

        addObject("gateway", gateway);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MimeMessage createMimeMessage(MimeMessage mimeMessage) {

        MimeMessageHelper mimeMessageHelper;

        if (getTo().isEmpty() && getCc().isEmpty() && getBcc().isEmpty()) {

            throw new MailException("At least one recipient (to, cc, or bcc) must be added to the mail.");
        }

        if (getFrom() == null) {

            throw new MailException("Setting the from field is required.");
        }

        try {

            mimeMessageHelper = new MimeMessageHelper(mimeMessage, hasAttachments(), "UTF-8");

            mimeMessageHelper.setTo(getTo().toArray(new InternetAddress[getTo().size()]));
            mimeMessageHelper.setBcc(getBcc().toArray(new InternetAddress[getBcc().size()]));
            mimeMessageHelper.setCc(getCc().toArray(new InternetAddress[getCc().size()]));
            mimeMessageHelper.setFrom(getFrom());

            if (getReplyTo() != null) {

                mimeMessageHelper.setReplyTo(getReplyTo());
            }

            mimeMessageHelper.setSubject(this.getSubject());

            // TODO - add behavior for html messages. Both plain text and html
            // have to have values. Passing in a null to this method
            // will throw an exception.
            // mimeMessageHelper.setText(processTemplate(this.plainTextMessageTemplate), processTemplate(this.htmlTextMessageTemplate));

            mimeMessageHelper.setText(this.getPlainText());

            // TODO - Add behavior for adding attachments.

        } catch (MessagingException e) {

            throw new MailException(e);
        }

        MimeMessage returnMimeMessage = mimeMessageHelper.getMimeMessage();

        return returnMimeMessage;
    }

    /**
     * Adds the object.
     *
     * @param templateName the template name
     * @param object       the object
     */
    protected void addObject(String templateName, Object object) {

        this.messageMap.put(templateName, object);
    }

    protected Gateway getGateway() {

        return this.gateway;
    }

    protected Configuration getConfiguration() {

        return this.configuration;
    }

    protected String getSubjectTemplate() {

        return this.subjectTemplate;
    }

    protected String getPlainTextMessageTemplate() {

        return this.plainTextMessageTemplate;
    }

    @Override
    public String getPlainText() {

        String plainText = this.processTemplate(this.plainTextMessageTemplate);

        return plainText;
    }

    @Override
    public String getSubject() {

        String subject = this.processTemplate(this.subjectTemplate);

        return subject;
    }

    @Override
    public void setSubject(String subject) {

        super.setSubject(subject);

        this.addObject("subject", subject);
    }

    /**
     * Process template.
     *
     * @param templateName the template name
     * @return the string
     */
    protected String processTemplate(String templateName) {

        String text = null;

        // FIXME - we most likely want to throw an exception here if the template is null.
        // Or remove this check and add an exception to be thrown by the constructor if the templateName is null or empty.
        if (templateName != null) {

            try {

                Template template = configuration.getTemplate(templateName);

                text = FreeMarkerTemplateUtils.processTemplateIntoString(template, this.messageMap);

            } catch (final IOException e) {

                throw new MailException(e);

            } catch (final TemplateException e) {

                throw new MailException(e);
            }
        }

        return text;
    }
}
