package sgf.gateway.mail.impl;

import freemarker.template.Configuration;
import sgf.gateway.model.Gateway;
import sgf.gateway.service.metadata.BrokeredDatasetsAuditReport;

public class BrokeredDatasetsAuditReportFailureMailMessage extends FreeMarkerCommonMailMessage {

    public BrokeredDatasetsAuditReportFailureMailMessage(Configuration configuration, Gateway gateway, String commonSubjectTemplate, String commonPlainTextMessageTemplate, final String subjectTemplate, final String plainTextMessageTemplate) {

        super(configuration, gateway, commonSubjectTemplate, commonPlainTextMessageTemplate, subjectTemplate, plainTextMessageTemplate);
    }

    public void setBrokeredDatasetsAuditFailureReport(BrokeredDatasetsAuditReport brokeredDatasetsAuditFailureReport) {

        super.addObject("brokeredDatasetsAuditFailureReport", brokeredDatasetsAuditFailureReport);
    }
}
