package sgf.gateway.service.metadata;

import org.springframework.context.ApplicationEvent;

public class BrokeredDatasetsAuditReportEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    private final BrokeredDatasetsAuditReport report;

    public BrokeredDatasetsAuditReportEvent(Object source, BrokeredDatasetsAuditReport report) {

        super(source);
        this.report = report;
    }

    public BrokeredDatasetsAuditReport getBrokeredDatasetsAuditFailureReport() {

        return this.report;
    }
}
