package sgf.gateway.service.metadata;

import org.springframework.context.ApplicationListener;
import sgf.gateway.service.mail.MailService;

import java.util.Set;

public class AuditReportMailMediator implements ApplicationListener<BrokeredDatasetsAuditReportEvent> {

    private final MailService mailService;

    private final Set<String> notificationAddresses;

    public AuditReportMailMediator(final MailService mailService, final Set<String> notificationAddresses) {

        this.mailService = mailService;
        this.notificationAddresses = notificationAddresses;
    }

    @Override
    public void onApplicationEvent(BrokeredDatasetsAuditReportEvent event) {

        BrokeredDatasetsAuditReport report = event.getBrokeredDatasetsAuditFailureReport();

        if (report.isEmpty()) {

            this.mailService.sendBrokeredDatasetsAuditReportSuccessMailMessage(this.notificationAddresses);

        } else {

            this.mailService.sendBrokeredDatasetsAuditReportFailureMailMessage(this.notificationAddresses, report);
        }

    }
}
