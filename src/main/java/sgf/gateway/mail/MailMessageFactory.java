package sgf.gateway.mail;

import sgf.gateway.model.security.Membership;
import sgf.gateway.model.security.User;
import sgf.gateway.model.workspace.DataTransferRequest;
import sgf.gateway.publishing.thredds.ThreddsPublishingResult;
import sgf.gateway.service.feedback.FeedbackRequest;
import sgf.gateway.service.messaging.UnhandledException;
import sgf.gateway.service.metadata.BrokeredDatasetsAuditReport;

import java.util.Collection;
import java.util.List;

/**
 * A factory for creating MailMessage objects.
 */
public interface MailMessageFactory {

    /**
     * Gets the simple mail message.
     *
     * @param subject the subject
     * @param message the message
     * @return the simple mail message
     */
    MailMessage getSimpleMailMessage(String subject, String message);

    MailMessage getSimpleFromMailMessage(String from, String subject, String message);

    MailMessage getDataTransferCompleteMailMessage(DataTransferRequest dataTransferRequest);

    MailMessage getUnhandledExceptionMailMessage(UnhandledException unhandledException);

    MailMessage getAccountConfirmationMailMessage(User user);

    MailMessage getAccountConfirmedMailMessage(User user);

    MailMessage getAdminRegistrationMailMessage(User user, String portalUrl);

    MailMessage getAdminGroupRegistrationMailMessage(User user, String groupName, String portalUrl);

    MailMessage getAdminGroupRoleUpdateMailMessage(User user, String groupName, Collection<Membership> memberships);

    MailMessage getUserGroupUpdateMailMessage(User user, String groupName, Collection<Membership> memberships, String adminMessage, String portalUrl, List<String> dataAccessUrls);

    MailMessage getUserGroupRejectionMailMessage(User user, String groupName, String adminMessage);

    MailMessage getUserOpenidReminderMailMessage(List<User> userAccounts);

    MailMessage getUserUsernameReminderMailMessage(User user);

    MailMessage getUserPasswordReminderMailMessage(User user, String newPassword);

    MailMessage getBrokeredDatasetsAuditReportFailureMailMessage(BrokeredDatasetsAuditReport auditReport);

    MailMessage getBrokeredDatasetsAuditReportSuccessMailMessage();

    MailMessage getPublishingSuccessMailMessage(ThreddsPublishingResult publishingResult);

    MailMessage getPublishingFailureMailMessage(ThreddsPublishingResult publishingResult);

    MailMessage getFeedbackReceivedResponseMailMessage();

    MailMessage getFeedbackReceivedMailMessage(FeedbackRequest feedbackRequest);
}
