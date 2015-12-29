package sgf.gateway.mail.impl;

import freemarker.template.Configuration;
import sgf.gateway.model.Gateway;

public class SimpleMailMessage extends FreeMarkerCommonMailMessage {

    /**
     * Instantiates a new simple mail message.
     *
     * @param configuration            the configuration
     * @param gateway                  the gateway
     * @param subjectTemplate          the subject template
     * @param plainTextMessageTemplate the plain text message template
     */
    public SimpleMailMessage(Configuration configuration, Gateway gateway, String commonSubjectTemplate, String commonPlainTextMessageTemplate, String subjectTemplate, String plainTextMessageTemplate) {

        super(configuration, gateway, commonSubjectTemplate, commonPlainTextMessageTemplate, subjectTemplate, plainTextMessageTemplate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlainText(String message) {
        super.addObject("plainText", message);
    }

}
