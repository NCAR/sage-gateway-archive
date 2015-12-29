package sgf.gateway.service.feedback.impl;

import org.apache.commons.lang.StringUtils;
import sgf.gateway.service.feedback.FeedbackRequest;
import sgf.gateway.service.feedback.FeedbackService;
import sgf.gateway.service.mail.MailService;

public class FeedbackServiceImpl implements FeedbackService {

    private final MailService mailService;

    public FeedbackServiceImpl(MailService mailService) {

        this.mailService = mailService;
    }

    @Override
    public void handleFeedback(FeedbackRequest feedbackRequest) {

        if (StringUtils.isNotBlank(feedbackRequest.getEmail())) {

            this.mailService.sendFeedbackRecievedResponseMailMessage(feedbackRequest);
        }

        this.mailService.sendFeedbackRecievedMailMessage(feedbackRequest);
    }

}
