package sgf.gateway.mail.impl;

import freemarker.template.Configuration;
import sgf.gateway.model.Gateway;
import sgf.gateway.publishing.thredds.ThreddsPublishingResult;

public class PublishingSuccessMailMessage extends FreeMarkerMailMessage {

    public PublishingSuccessMailMessage(Configuration configuration, Gateway gateway, String subjectTemplate, String plainTextMessageTemplate) {

        super(configuration, gateway, subjectTemplate, plainTextMessageTemplate);
    }

    public void setPublishingDetails(ThreddsPublishingResult publishingResult) {
        super.addObject("publishingResult", publishingResult);
    }
}
