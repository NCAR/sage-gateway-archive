package sgf.gateway.mail.impl;

import freemarker.template.Configuration;
import sgf.gateway.model.Gateway;
import sgf.gateway.service.feedback.FeedbackRequest;

public class FeedbackReceivedMailMessage extends FreeMarkerMailMessage {

    public FeedbackReceivedMailMessage(Configuration configuration, Gateway gateway, String subjectTemplate, String plainTextMessageTemplate) {

        super(configuration, gateway, subjectTemplate, plainTextMessageTemplate);
    }

    public void addFeedbackRequest(FeedbackRequest feedbackRequest) {

        super.addObject("feedbackRequest", feedbackRequest);
    }
}
