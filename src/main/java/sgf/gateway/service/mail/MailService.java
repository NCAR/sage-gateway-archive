package sgf.gateway.service.mail;

import sgf.gateway.model.security.Membership;
import sgf.gateway.model.security.User;
import sgf.gateway.model.workspace.DataTransferRequest;
import sgf.gateway.publishing.thredds.ThreddsPublishingResult;
import sgf.gateway.service.feedback.FeedbackRequest;
import sgf.gateway.service.messaging.UnhandledException;
import sgf.gateway.service.metadata.BrokeredDatasetsAuditReport;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * The Interface MailService.
 */
public interface MailService {

    void sendSimpleMailMessage(Collection<User> to, String subject, String message);

    void sendSimpleFromMailMessage(String to, String from, String subject, String message);

    void sendSimpleFromMailMessage(Collection<User> to, String from, String subject, String message);

    void sendDataTransferCompleteMailMessage(Collection<User> to, DataTransferRequest dataTransferRequest);

    void sendUnhandledExceptionMailMessage(Set<String> to, UnhandledException unhandledException);

    void sendAccountConfirmationMailMessage(Collection<User> to, User newUser);

    void sendAccountConfirmedMailMessage(User newUser);

    void sendAdminRegistrationMailMessage(Collection<User> to, User newUser, String portalUrl);

    void sendAdminGroupRegistrationMailMessage(Collection<User> to, User newUser, String groupName, String portalUrl);

    void sendAdminGroupRoleUpdateMailMessage(Collection<User> to, User newUser, String groupName, Collection<Membership> memberships);

    void sendUserGroupUpdateMailMessage(Collection<User> to, User newUser, String groupName,
                                        Collection<Membership> memberships, String adminMessage, String portalUrl, List<String> dataAccessUrls);

    void sendUserGroupRejectionMailMessage(Collection<User> to, User newUser, String groupName, String adminMessage);

    void sendUserOpenidReminderMailMessage(User to, List<User> userAccounts);

    void sendUserUsernameReminderMailMessage(Collection<User> to, User user);

    void sendUserPasswordReminderMailMessage(Collection<User> to, String newPassword);

    void sendBrokeredDatasetsAuditReportFailureMailMessage(Set<String> to, BrokeredDatasetsAuditReport auditReport);

    void sendBrokeredDatasetsAuditReportSuccessMailMessage(Set<String> to);

    void sendPublishSuccessMailMessage(Set<String> to, ThreddsPublishingResult publishingResult);

    void sendPublishFailureMailMessage(Set<String> to, ThreddsPublishingResult publishingResult);

    void sendFeedbackRecievedResponseMailMessage(FeedbackRequest feedbackRequest);

    void sendFeedbackRecievedMailMessage(FeedbackRequest feedbackRequest);
}
