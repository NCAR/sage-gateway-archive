package sgf.gateway.mail.impl;

import freemarker.template.Configuration;
import sgf.gateway.model.Gateway;

public class FreeMarkerCommonMailMessage extends FreeMarkerMailMessage {

    private final String commonSubjectTemplate;
    private final String commonPlainTextMessageTemplate;

    public FreeMarkerCommonMailMessage(Configuration configuration, Gateway gateway, String commonSubjectTemplate, String commonPlainTextMessageTemplate, String subjectTemplate, String plainTextMessageTemplate) {

        super(configuration, gateway, subjectTemplate, plainTextMessageTemplate);

        this.commonSubjectTemplate = commonSubjectTemplate;
        this.commonPlainTextMessageTemplate = commonPlainTextMessageTemplate;
    }

    @Override
    public String getPlainText() {

        String plainText = super.getPlainText();

        this.addObject("commonPlainText", plainText);

        plainText = this.processTemplate(this.commonPlainTextMessageTemplate);

        return plainText;
    }

    @Override
    public String getSubject() {

        String subject = super.getSubject();

        this.addObject("commonSubject", subject);

        subject = this.processTemplate(this.commonSubjectTemplate);

        return subject;
    }
}
