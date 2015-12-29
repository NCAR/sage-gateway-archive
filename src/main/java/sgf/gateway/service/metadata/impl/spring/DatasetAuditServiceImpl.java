package sgf.gateway.service.metadata.impl.spring;

import org.safehaus.uuid.UUID;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.Gateway;
import sgf.gateway.model.metadata.ContainerType;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.service.metadata.BrokeredDatasetsAuditFailureReportEntry;
import sgf.gateway.service.metadata.BrokeredDatasetsAuditReport;
import sgf.gateway.service.metadata.BrokeredDatasetsAuditReportEvent;
import sgf.gateway.service.metadata.DatasetAuditService;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

public class DatasetAuditServiceImpl implements DatasetAuditService, ApplicationEventPublisherAware {

    private final Gateway gateway;

    private final DatasetRepository datasetRepository;

    private final RestTemplate restTemplate;

    private boolean enabled = false;

    private ApplicationEventPublisher applicationEventPublisher;

    public DatasetAuditServiceImpl(final Gateway gateway, final DatasetRepository datasetRepository, final RestTemplate restTemplate) {

        this.gateway = gateway;
        this.datasetRepository = datasetRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public void auditBrokeredDatasets() {

        if (this.enabled) {

            BrokeredDatasetsAuditReport report = this.tryAuditBrokeredDatasets();

            this.createAndPublishAuditReportEvent(report);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    BrokeredDatasetsAuditReport tryAuditBrokeredDatasets() {

        Collection<UUID> brokeredDatasetIdentifiers = this.datasetRepository.findAllIdentifiersForBrokeredDatasets();

        BrokeredDatasetsAuditReport report = this.auditBrokeredDatasets(brokeredDatasetIdentifiers);

        return report;
    }

    BrokeredDatasetsAuditReport auditBrokeredDatasets(Collection<UUID> brokeredDatasetIdentifiers) {

        BrokeredDatasetsAuditReport report = new LocalBrokeredDatasetsAuditFailureReport();

        for (UUID identifier : brokeredDatasetIdentifiers) {

            Dataset brokeredDataset = this.datasetRepository.get(identifier);

            this.checkAuthoritativeSource(brokeredDataset, report);
        }

        return report;
    }

    void checkAuthoritativeSource(Dataset brokeredDataset, BrokeredDatasetsAuditReport report) {

        URI authoritativeSourceURI = brokeredDataset.getCurrentDatasetVersion().getAuthoritativeSourceURI();

        URI datasetURI = this.createLocalDatasetURI(brokeredDataset);

        try {

            ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(authoritativeSourceURI, String.class);

            if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {

                LocalBrokeredDatasetsAuditFailureReportEntry entry = new LocalBrokeredDatasetsAuditFailureReportEntry(datasetURI, authoritativeSourceURI, "Status Code: " + responseEntity.getStatusCode().value() + " " + responseEntity.getStatusCode().getReasonPhrase());

                report.add(entry);
            }

        } catch (Throwable e) {

            LocalBrokeredDatasetsAuditFailureReportEntry entry = new LocalBrokeredDatasetsAuditFailureReportEntry(datasetURI, authoritativeSourceURI, "Exception: " + e.getMessage());

            report.add(entry);
        }
    }

    URI createLocalDatasetURI(Dataset brokeredDataset) {

        String path = this.getLocalDatasetURIPath(brokeredDataset);

        URI localDatasetURI = URI.create(this.gateway.getBaseURL() + path);

        return localDatasetURI;
    }

    String getLocalDatasetURIPath(Dataset brokeredDataset) {

        String pathContainerTypePart;

        if (brokeredDataset.isContainerType(ContainerType.PROJECT)) {
            pathContainerTypePart = "project";
        } else {
            pathContainerTypePart = "dataset";
        }

        return pathContainerTypePart + "/" + brokeredDataset.getShortName() + ".html";
    }

    void createAndPublishAuditReportEvent(BrokeredDatasetsAuditReport report) {

        BrokeredDatasetsAuditReportEvent event = new BrokeredDatasetsAuditReportEvent(this, report);

        this.applicationEventPublisher.publishEvent(event);
    }

    public void setEnabled(final boolean enabled) {

        this.enabled = enabled;
    }


    private class LocalBrokeredDatasetsAuditFailureReport extends ArrayList<BrokeredDatasetsAuditFailureReportEntry> implements BrokeredDatasetsAuditReport {

        private static final long serialVersionUID = 1L;
    }

    private class LocalBrokeredDatasetsAuditFailureReportEntry implements BrokeredDatasetsAuditFailureReportEntry {

        private final URI datasetURI;
        private final URI authoritativeSourceURI;
        private final String errorMessage;

        public LocalBrokeredDatasetsAuditFailureReportEntry(final URI datasetURI, final URI authoritativeSourceURI, final String errorMessage) {

            this.datasetURI = datasetURI;
            this.authoritativeSourceURI = authoritativeSourceURI;
            this.errorMessage = errorMessage;
        }

        @Override
        public URI getBrokeredDatasetURI() {

            return this.datasetURI;
        }

        @Override
        public URI getBrokeredDatasetsAuthoritativeSourceURI() {

            return this.authoritativeSourceURI;
        }

        @Override
        public String getErrorMessage() {

            return this.errorMessage;
        }

    }

    @Override
    public void setApplicationEventPublisher(final ApplicationEventPublisher applicationEventPublisher) {

        this.applicationEventPublisher = applicationEventPublisher;
    }
}
