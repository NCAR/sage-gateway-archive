package sgf.gateway.service.mail.impl.spring;

import org.springframework.util.StringUtils;
import sgf.gateway.mail.MailMessage;
import sgf.gateway.mail.MailMessageFactory;
import sgf.gateway.mail.MailServer;
import sgf.gateway.model.Gateway;
import sgf.gateway.model.security.Membership;
import sgf.gateway.model.security.User;
import sgf.gateway.model.workspace.DataTransferRequest;
import sgf.gateway.publishing.thredds.ThreddsPublishingResult;
import sgf.gateway.service.feedback.FeedbackRequest;
import sgf.gateway.service.mail.MailService;
import sgf.gateway.service.messaging.UnhandledException;
import sgf.gateway.service.metadata.BrokeredDatasetsAuditReport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;


/**
 * The Class MailServiceImpl.
 */
public class MailServiceImpl implements MailService {

    private final MailMessageFactory emailMessageFactory;

    private final MailServer emailServer;

    private final Gateway gateway;

    private final List<String> bccList;

    public MailServiceImpl(MailServer emailServer, MailMessageFactory emailMessageFactory, Gateway gateway, String bccList) {

        this.emailServer = emailServer;
        this.emailMessageFactory = emailMessageFactory;

        this.gateway = gateway;
        this.bccList = setBccEmailList(bccList);
    }

    public void sendSimpleMailMessage(Collection<User> to, String subject, String message) {

        for (User toUser : to) {

            MailMessage mailMessage = this.emailMessageFactory.getSimpleMailMessage(subject, message);

            sendMailMessageInternal(toUser, mailMessage);
        }
    }

    public void sendSimpleFromMailMessage(Collection<User> to, String from, String subject, String message) {

        for (User user : to) {

            MailMessage mailMessage = this.emailMessageFactory.getSimpleFromMailMessage(from, subject, message);

            sendMailMessageInternal(from, user.getEmail(), mailMessage);
        }
    }

    public void sendSimpleFromMailMessage(String to, String from, String subject, String message) {

        MailMessage mailMessage = this.emailMessageFactory.getSimpleFromMailMessage(from, subject, message);

        sendMailMessageInternal(from, to, mailMessage);

    }

    public void sendDataTransferCompleteMailMessage(Collection<User> to, DataTransferRequest dataTransferRequest) {

        for (User toUser : to) {

            MailMessage mailMessage = this.emailMessageFactory.getDataTransferCompleteMailMessage(dataTransferRequest);

            sendMailMessageInternal(toUser, mailMessage);
        }
    }

    // Send message to user to confirm account
    public void sendAccountConfirmationMailMessage(Collection<User> to, User newUser) {

        for (User toUser : to) {

            MailMessage mailMessage = this.emailMessageFactory.getAccountConfirmationMailMessage(newUser);

            sendMailMessageInternal(toUser, mailMessage);
        }
    }

    // Send message when user confirms new account
    public void sendAccountConfirmedMailMessage(User newUser) {

        MailMessage mailMessage = this.emailMessageFactory.getAccountConfirmedMailMessage(newUser);

        sendMailMessageInternal(newUser, mailMessage);
    }

    // Send user their username
    public void sendUserUsernameReminderMailMessage(Collection<User> to, User user) {

        for (User toUser : to) {

            MailMessage mailMessage = this.emailMessageFactory.getUserUsernameReminderMailMessage(user);

            sendMailMessageInternal(toUser, mailMessage);
        }
    }

    // Send user their password
    public void sendUserPasswordReminderMailMessage(Collection<User> to, String newPassword) {

        for (User toUser : to) {

            MailMessage mailMessage = this.emailMessageFactory.getUserPasswordReminderMailMessage(toUser, newPassword);

            sendMailMessageInternal(toUser, mailMessage, true);
        }
    }


    // Send admin new user message
    public void sendAdminRegistrationMailMessage(Collection<User> to, User newUser, String portalUrl) {

        for (User adminUser : to) {

            MailMessage mailMessage = this.emailMessageFactory.getAdminRegistrationMailMessage(newUser, portalUrl);

            sendMailMessageInternal(adminUser, mailMessage);
        }
    }

    // Notify Admin
    public void sendAdminGroupRegistrationMailMessage(Collection<User> to, User newUser, String groupName, String portalUrl) {

        for (User user : to) {

            MailMessage mailMessage = this.emailMessageFactory.getAdminGroupRegistrationMailMessage(newUser, groupName, portalUrl);

            sendMailMessageInternal(user, mailMessage);
        }
    }

    // Notify Admin user when user requests group
    public void sendAdminGroupRoleUpdateMailMessage(Collection<User> to, User newUser, String groupName, Collection<Membership> memberships) {

        for (User adminUser : to) {

            MailMessage mailMessage = this.emailMessageFactory.getAdminGroupRoleUpdateMailMessage(newUser, groupName, memberships);

            sendMailMessageInternal(adminUser, mailMessage);
        }
    }

    // Notify User when their group is approved.
    public void sendUserGroupUpdateMailMessage(Collection<User> to, User newUser, String groupName, Collection<Membership> memberships, String adminMessage, String portalUrl, List<String> dataAccessUrls) {

        for (User user : to) {

            MailMessage mailMessage = this.emailMessageFactory.getUserGroupUpdateMailMessage(newUser, groupName, memberships, adminMessage, portalUrl, dataAccessUrls);

            sendMailMessageInternal(user, mailMessage);
        }
    }

    // Notify User when their group is rejected.
    public void sendUserGroupRejectionMailMessage(Collection<User> to, User newUser, String groupName, String adminMessage) {

        for (User user : to) {

            MailMessage mailMessage = this.emailMessageFactory.getUserGroupRejectionMailMessage(newUser, groupName, adminMessage);

            sendMailMessageInternal(user, mailMessage);
        }
    }

    // Send user their openid(s)
    public void sendUserOpenidReminderMailMessage(User to, List<User> userAccounts) {

        MailMessage mailMessage = this.emailMessageFactory.getUserOpenidReminderMailMessage(userAccounts);

        sendMailMessageInternal(to, mailMessage);
    }

    private List<String> setBccEmailList(final String list) {

        List<String> administratorEmailList = new ArrayList<String>();

        String emailArray[] = list.split(",");

        for (String email : emailArray) {

            if (StringUtils.hasText(email.trim())) {

                administratorEmailList.add(email.trim());
            }

        }

        return administratorEmailList;
    }


    public void sendUnhandledExceptionMailMessage(Set<String> to, UnhandledException unhandledException) {

        // FIXME - Catch the right type of exceptions here... When a message fails because of an address issue we should continue with the loop of sending the emails.
        // FIXME - Where does the behavior belong for sending an email address to each individual user?
        for (String user : to) {

            MailMessage mailMessage = this.emailMessageFactory.getUnhandledExceptionMailMessage(unhandledException);

            mailMessage.addTo(user);
            addBccList(mailMessage, bccList);
            mailMessage.setFrom(this.gateway.getAdministratorEmailException(), this.gateway.getAdministratorPersonal());

            this.emailServer.send(mailMessage);
        }
    }

    public void sendBrokeredDatasetsAuditReportFailureMailMessage(Set<String> toSet, BrokeredDatasetsAuditReport auditReport) {

        for (String user : toSet) {

            MailMessage mailMessage = this.emailMessageFactory.getBrokeredDatasetsAuditReportFailureMailMessage(auditReport);

            mailMessage.addTo(user);
            addBccList(mailMessage, bccList);
            mailMessage.setFrom(this.gateway.getAdministratorEmail(), this.gateway.getAdministratorPersonal());

            this.emailServer.send(mailMessage);
        }
    }

    public void sendBrokeredDatasetsAuditReportSuccessMailMessage(Set<String> toSet) {

        for (String user : toSet) {

            MailMessage mailMessage = this.emailMessageFactory.getBrokeredDatasetsAuditReportSuccessMailMessage();

            mailMessage.addTo(user);
            addBccList(mailMessage, bccList);
            mailMessage.setFrom(this.gateway.getAdministratorEmail(), this.gateway.getAdministratorPersonal());

            this.emailServer.send(mailMessage);
        }
    }

    public void sendPublishSuccessMailMessage(Set<String> toSet, ThreddsPublishingResult publishingResult) {

        for (String userAddress : toSet) {

            MailMessage mailMessage = this.emailMessageFactory.getPublishingSuccessMailMessage(publishingResult);
            mailMessage.addTo(userAddress);

            mailMessage.setFrom(this.gateway.getAdministratorEmail(), this.gateway.getAdministratorPersonal());

            this.emailServer.send(mailMessage);
        }
    }

    public void sendPublishFailureMailMessage(Set<String> toSet, ThreddsPublishingResult publishingResult) {

        for (String userAddress : toSet) {

            MailMessage mailMessage = this.emailMessageFactory.getPublishingFailureMailMessage(publishingResult);
            mailMessage.addTo(userAddress);

            mailMessage.setFrom(this.gateway.getAdministratorEmail(), this.gateway.getAdministratorPersonal());

            this.emailServer.send(mailMessage);
        }
    }

    @Override
    public void sendFeedbackRecievedResponseMailMessage(FeedbackRequest feedbackRequest) {

        MailMessage mailMessage = this.emailMessageFactory.getFeedbackReceivedResponseMailMessage();
        mailMessage.addTo(feedbackRequest.getEmail());

        mailMessage.setFrom(this.gateway.getAdministratorEmail(), this.gateway.getAdministratorPersonal());

        this.emailServer.send(mailMessage);
    }

    @Override
    public void sendFeedbackRecievedMailMessage(FeedbackRequest feedbackRequest) {

        MailMessage mailMessage = this.emailMessageFactory.getFeedbackReceivedMailMessage(feedbackRequest);
        mailMessage.addTo(this.gateway.getAdministratorEmail(), this.gateway.getAdministratorPersonal());

        mailMessage.setFrom(this.gateway.getAdministratorEmail(), this.gateway.getAdministratorPersonal());

        this.emailServer.send(mailMessage);
    }

    private void sendMailMessageInternal(User user, MailMessage mailMessage, boolean sensitiveContent) {

        // FIXME - Catch the right type of exceptions here... When a message fails because of an address issue we should continue with the loop of sending the emails.
        // FIXME - Where does the behavior belong for sending an email address to each individual user?
        mailMessage.addTo(user);

        if (!sensitiveContent) {

            addBccList(mailMessage, bccList);
        }

        mailMessage.setFrom(this.gateway.getAdministratorEmail(), this.gateway.getAdministratorPersonal());

        this.emailServer.send(mailMessage);
    }

    private void sendMailMessageInternal(User user, MailMessage mailMessage) {

        this.sendMailMessageInternal(user, mailMessage, false);
    }

    private void sendMailMessageInternal(String fromAddress, String toAddress, MailMessage mailMessage, boolean sensitiveContent) {

        // FIXME - Catch the right type of exceptions here... When a message fails because of an address issue we should continue with the loop of sending the emails.
        // FIXME - Where does the behavior belong for sending an email address to each individual user?
        mailMessage.addTo(toAddress);

        if (!sensitiveContent) {

            addBccList(mailMessage, bccList);
        }

        mailMessage.setFrom(fromAddress);

        this.emailServer.send(mailMessage);
    }

    private void sendMailMessageInternal(String fromAddress, String toAddress, MailMessage mailMessage) {

        this.sendMailMessageInternal(fromAddress, toAddress, mailMessage, false);
    }

    private void addBccList(MailMessage mailMessage, List<String> bccList) {

        for (String bbcEmail : bccList) {

            mailMessage.addBcc(bbcEmail);
        }
    }
}
