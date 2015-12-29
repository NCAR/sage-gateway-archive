package sgf.gateway.publishing.thredds.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.security.User;
import sgf.gateway.publishing.thredds.ThreddsDatasetDetails;
import sgf.gateway.publishing.thredds.ThreddsPublishingMailService;
import sgf.gateway.publishing.thredds.ThreddsPublishingResult;
import sgf.gateway.service.mail.MailService;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

public class ThreddsPublishingMailServiceImpl implements ThreddsPublishingMailService {

    private final UserRepository userRepository;
    private final DatasetRepository datasetRepository;
    private final MailService mailService;
    private final Set<String> failureNotificationAddresses;
    private final Set<String> successNotificationAddresses;

    public ThreddsPublishingMailServiceImpl(UserRepository userRepository, DatasetRepository datasetRepository, MailService mailService,
                                            Set<String> failureNotificationAddresses, Set<String> successNotificationAddresses) {

        super();
        this.userRepository = userRepository;
        this.datasetRepository = datasetRepository;
        this.mailService = mailService;
        this.failureNotificationAddresses = failureNotificationAddresses;
        this.successNotificationAddresses = successNotificationAddresses;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void sendFailMessage(ThreddsDatasetDetails details) {

        ThreddsPublishingResult result = this.createThreddsPublishingResult(details);

        Set<String> toAddresses = this.getFailureToAddresses(result);

        this.mailService.sendPublishFailureMailMessage(toAddresses, result);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void sendSuccessMessage(ThreddsDatasetDetails details) {

        ThreddsPublishingResult result = this.createThreddsPublishingResult(details);
        result = this.addDataset(details, result);

        Set<String> toAddresses = this.getSuccessToAddresses(result);

        this.mailService.sendPublishSuccessMailMessage(toAddresses, result);
    }

    private ThreddsPublishingResult createThreddsPublishingResult(ThreddsDatasetDetails details) {

        User user = this.getUser(details);
        URI authoritativeSourceURI = details.getAuthoritativeSourceURI();

        return new ThreddsPublishingResult(user, authoritativeSourceURI);
    }

    private ThreddsPublishingResult addDataset(ThreddsDatasetDetails details, ThreddsPublishingResult result) {

        Dataset dataset = this.getDataset(details);
        result.setDataset(dataset);

        return result;
    }

    private User getUser(ThreddsDatasetDetails details) {

        return this.userRepository.getUser(details.getUserID());
    }

    private Dataset getDataset(ThreddsDatasetDetails details) {

        return this.datasetRepository.get(details.getDatasetID());
    }

    private Set<String> getFailureToAddresses(ThreddsPublishingResult publishingResult) {

        Set<String> toAddresses = new HashSet<>(this.failureNotificationAddresses);

        String publisherEmail = publishingResult.getUser().getEmail();

        toAddresses.add(publisherEmail);

        return toAddresses;
    }

    private Set<String> getSuccessToAddresses(ThreddsPublishingResult publishingResult) {

        Set<String> toAddresses = new HashSet<>(this.successNotificationAddresses);

        String publisherEmail = publishingResult.getUser().getEmail();

        toAddresses.add(publisherEmail);

        return toAddresses;
    }
}
