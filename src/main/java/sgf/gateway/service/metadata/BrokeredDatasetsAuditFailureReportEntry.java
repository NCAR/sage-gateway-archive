package sgf.gateway.service.metadata;

import java.net.URI;

public interface BrokeredDatasetsAuditFailureReportEntry {

    URI getBrokeredDatasetURI();

    URI getBrokeredDatasetsAuthoritativeSourceURI();

    String getErrorMessage();
}
