package sgf.gateway.mail.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import sgf.gateway.mail.MailMessage;
import sgf.gateway.mail.MailMessageFactory;
import sgf.gateway.model.security.Membership;
import sgf.gateway.model.security.User;
import sgf.gateway.model.workspace.DataTransferRequest;
import sgf.gateway.publishing.thredds.ThreddsPublishingResult;
import sgf.gateway.service.feedback.FeedbackRequest;
import sgf.gateway.service.messaging.UnhandledException;
import sgf.gateway.service.metadata.BrokeredDatasetsAuditReport;

import java.util.Collection;
import java.util.List;

public class SpringMailMessageFactoryImpl implements MailMessageFactory, ApplicationContextAware {

    private ApplicationContext applicationContext;

    public SpringMailMessageFactoryImpl() {

    }

    public MailMessage getSimpleMailMessage(String subject, String message) {

        SimpleMailMessage simpleMailMessage = (SimpleMailMessage) this.applicationContext.getBean("simpleMailMessage");
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setPlainText(message);

        return simpleMailMessage;
    }

    // Allows setting From (unlike SimpleMailMessage), subject, and a non-formatted messages string (e.g. 1-liners like "SUBSCRIBE".)
    public MailMessage getSimpleFromMailMessage(String from, String subject, String message) {

        MailMessage simpleMailMessage = this.getSimpleMailMessage(subject, message);
        simpleMailMessage.setFrom(from);

        return simpleMailMessage;
    }

    public MailMessage getDataTransferCompleteMailMessage(DataTransferRequest dataTransferRequest) {

        DataTransferCompleteMailMessage dataTransferCompleteMailMessage = (DataTransferCompleteMailMessage) this.applicationContext.getBean("dataTransferCompleteMailMessage");
        dataTransferCompleteMailMessage.setDataTransferRequest(dataTransferRequest);

        return dataTransferCompleteMailMessage;
    }

    public MailMessage getUnhandledExceptionMailMessage(UnhandledException unhandledException) {

        UnhandledExceptionMailMessage unhandledExceptionMailMessage = (UnhandledExceptionMailMessage) this.applicationContext.getBean("unhandledExceptionMailMessage");
        unhandledExceptionMailMessage.setUnhandledException(unhandledException);

        return unhandledExceptionMailMessage;
    }

    /*
     * User account mail messages, which only require the User's information.  Reuse class, differ bean configuration to specify specialized ftl templates.
     * * Coalesce AccountConfirmation, AccountConfirmed, AdminRegistration, UserUsername, UserPassword (OpenIDs?)
     * @see sgf.gateway.mail.MailMessageFactory#getAccountConfirmationMailMessage(sgf.gateway.model.security.User)
     */
    public MailMessage getAccountConfirmationMailMessage(User user) {

        AccountInfoMailMessage accountInfoMailMessage = (AccountInfoMailMessage) this.applicationContext.getBean("accountConfirmationMailMessage");
        accountInfoMailMessage.setUser(user);

        return accountInfoMailMessage;
    }

    public MailMessage getAccountConfirmedMailMessage(User user) {

        AccountInfoMailMessage accountInfoMailMessage = (AccountInfoMailMessage) this.applicationContext.getBean("accountConfirmedMailMessage");
        accountInfoMailMessage.setUser(user);

        return accountInfoMailMessage;
    }

    public MailMessage getAdminRegistrationMailMessage(User user, String portalUrl) {

        AdminRegistrationMailMessage adminRegistrationMailMessage = (AdminRegistrationMailMessage) this.applicationContext.getBean("adminRegistrationMailMessage");
        adminRegistrationMailMessage.setUser(user);
        adminRegistrationMailMessage.setPortalUrl(portalUrl);

        return adminRegistrationMailMessage;
    }

    //
    public MailMessage getUserUsernameReminderMailMessage(User user) {

        AccountInfoMailMessage accountInfoMailMessage = (AccountInfoMailMessage) this.applicationContext.getBean("userUsernameReminderMailMessage");
        accountInfoMailMessage.setUser(user);

        return accountInfoMailMessage;
    }

    public MailMessage getUserPasswordReminderMailMessage(User user, String newPassword) {

        AccountInfoMailMessage accountInfoMailMessage = (AccountInfoMailMessage) this.applicationContext.getBean("userPasswordReminderMailMessage");
        accountInfoMailMessage.setUser(user);
        accountInfoMailMessage.setPassword(newPassword);

        return accountInfoMailMessage;
    }


    public MailMessage getAdminGroupRegistrationMailMessage(User user, String groupName, String portalUrl) {

        AdminGroupRegistrationMailMessage adminGroupRegistrationMailMessage = (AdminGroupRegistrationMailMessage) this.applicationContext.getBean("adminGroupRegistrationMailMessage");
        adminGroupRegistrationMailMessage.setUser(user);
        adminGroupRegistrationMailMessage.setGroupName(groupName);
        adminGroupRegistrationMailMessage.setClickUrlParams(user, groupName);

        return adminGroupRegistrationMailMessage;
    }

    public MailMessage getAdminGroupRoleUpdateMailMessage(User user, String groupName, Collection<Membership> memberships) {

        AdminGroupRoleUpdateMailMessage adminGroupRoleUpdateMailMessage = (AdminGroupRoleUpdateMailMessage) this.applicationContext.getBean("adminGroupRoleUpdateMailMessage");
        adminGroupRoleUpdateMailMessage.setUser(user);
        adminGroupRoleUpdateMailMessage.setGroupName(groupName);
        adminGroupRoleUpdateMailMessage.setMemberships(memberships);

        return adminGroupRoleUpdateMailMessage;
    }

    public MailMessage getUserGroupUpdateMailMessage(User user, String groupName, Collection<Membership> memberships, String adminMessage, String portalUrl, List<String> dataAccessUrls) {

        UserGroupUpdateMailMessage userGroupUpdateMailMessage = (UserGroupUpdateMailMessage) this.applicationContext.getBean("userGroupUpdateMailMessage");
        userGroupUpdateMailMessage.setUser(user);
        userGroupUpdateMailMessage.setGroupName(groupName);
        userGroupUpdateMailMessage.setMemberships(memberships);
        userGroupUpdateMailMessage.setAdminMessage(adminMessage);
        userGroupUpdateMailMessage.setDataAccessUrls(dataAccessUrls);
        userGroupUpdateMailMessage.setPortalUrl(portalUrl);

        return userGroupUpdateMailMessage;
    }

    public MailMessage getUserGroupRejectionMailMessage(User user, String groupName, String adminMessage) {

        UserGroupRejectionMailMessage userGroupRejectionMailMessage = (UserGroupRejectionMailMessage) this.applicationContext.getBean("userGroupRejectionMailMessage");
        userGroupRejectionMailMessage.setUser(user);
        userGroupRejectionMailMessage.setGroupName(groupName);
        userGroupRejectionMailMessage.setAdminMessage(adminMessage);

        return userGroupRejectionMailMessage;
    }

    // Support plural "users" openids - user may have multiple "accounts" with different openids.
    public MailMessage getUserOpenidReminderMailMessage(List<User> userAccounts) {

        UserOpenidReminderMailMessage userOpenidReminderMailMessage = (UserOpenidReminderMailMessage) this.applicationContext.getBean("userOpenidReminderMailMessage");
        userOpenidReminderMailMessage.setUsers(userAccounts);

        return userOpenidReminderMailMessage;
    }

    @Override
    public MailMessage getBrokeredDatasetsAuditReportFailureMailMessage(BrokeredDatasetsAuditReport auditReport) {

        BrokeredDatasetsAuditReportFailureMailMessage brokeredDatasetsAuditFailureReportMailMessage = (BrokeredDatasetsAuditReportFailureMailMessage) this.applicationContext.getBean("brokeredDatasetsAuditReportFailureMailMessage");
        brokeredDatasetsAuditFailureReportMailMessage.setBrokeredDatasetsAuditFailureReport(auditReport);

        return brokeredDatasetsAuditFailureReportMailMessage;
    }

    @Override
    public MailMessage getBrokeredDatasetsAuditReportSuccessMailMessage() {

        MailMessage mailMessage = (MailMessage) this.applicationContext.getBean("brokeredDatasetsAuditReportSuccessMailMessage");

        return mailMessage;
    }

    public MailMessage getPublishingSuccessMailMessage(ThreddsPublishingResult publishingResult) {

        PublishingSuccessMailMessage publishingSuccessMailMessage = (PublishingSuccessMailMessage) this.applicationContext.getBean("publishingSuccessMailMessage");
        publishingSuccessMailMessage.setPublishingDetails(publishingResult);

        return publishingSuccessMailMessage;
    }

    public MailMessage getPublishingFailureMailMessage(ThreddsPublishingResult publishingResult) {

        PublishingFailureMailMessage publishingFailureMailMessage = (PublishingFailureMailMessage) this.applicationContext.getBean("publishingFailureMailMessage");
        publishingFailureMailMessage.setPublishingDetails(publishingResult);

        return publishingFailureMailMessage;
    }

    public MailMessage getFeedbackReceivedResponseMailMessage() {

        FeedbackReceivedResponseMailMessage feedbackReceivedResponseMailMessage = (FeedbackReceivedResponseMailMessage) this.applicationContext.getBean("feedbackReceivedResponseMailMessage");

        return feedbackReceivedResponseMailMessage;
    }

    public MailMessage getFeedbackReceivedMailMessage(FeedbackRequest feedbackRequest) {

        FeedbackReceivedMailMessage feedbackReceivedMailMessage = (FeedbackReceivedMailMessage) this.applicationContext.getBean("feedbackReceivedMailMessage");
        feedbackReceivedMailMessage.addFeedbackRequest(feedbackRequest);

        return feedbackReceivedMailMessage;
    }

    public void setApplicationContext(final ApplicationContext applicationContext) {

        this.applicationContext = applicationContext;
    }
}
