package sgf.gateway.service.metadata.impl.spring;

import org.safehaus.uuid.UUID;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import sgf.gateway.dao.metadata.*;
import sgf.gateway.event.DatasetStoredEvent;
import sgf.gateway.model.metadata.*;
import sgf.gateway.model.metadata.activities.observing.InstrumentKeyword;
import sgf.gateway.model.metadata.activities.observing.PlatformType;
import sgf.gateway.model.metadata.activities.project.Award;
import sgf.gateway.model.metadata.citation.ResponsibleParty;
import sgf.gateway.model.metadata.citation.factory.ResponsiblePartyFactory;
import sgf.gateway.model.metadata.descriptive.DescriptiveMetadata;
import sgf.gateway.model.metadata.factory.DatasetFactory;
import sgf.gateway.model.metadata.factory.LinkFactory;
import sgf.gateway.model.security.Group;
import sgf.gateway.model.security.Operation;
import sgf.gateway.model.security.User;
import sgf.gateway.service.metadata.*;
import sgf.gateway.service.security.AccountService;
import sgf.gateway.service.security.RuntimeUserService;
import sgf.gateway.web.controllers.cadis.publish.command.CadisMoveDatasetCommand;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CadisDatasetServiceImpl implements CadisDatasetService, ApplicationEventPublisherAware {

    private final DatasetRepository datasetRepository;
    private final MetadataRepository metadataRepository;
    private final InstrumentKeywordRepository instrumentKeywordRepository;
    private final PlatformTypeRepository platformTypeRepository;
    private final TopicRepository topicRepository;

    private final RuntimeUserService runtimeUserService;
    private final AccountService accountService;
    private final DataCenterRepository dataCenterRepository;

    private final DatasetFactory datasetFactory;
    private final LinkFactory linkFactory;

    private final ResponsiblePartyFactory responsiblePartyFactory;

    private ApplicationEventPublisher applicationEventPublisher;

    public CadisDatasetServiceImpl(DatasetFactory datasetFactory, LinkFactory linkFactory,
                                   DatasetRepository datasetRepository, TopicRepository topicRepository, MetadataRepository metadataRepository,
                                   PlatformTypeRepository platformTypeRepository, InstrumentKeywordRepository instrumentKeywordRepository,
                                   AccountService accountService, RuntimeUserService runtimeUserService, DataCenterRepository dataCenterRepository,
                                   ResponsiblePartyFactory responsiblePartyFactory) {

        super();

        this.datasetFactory = datasetFactory;
        this.linkFactory = linkFactory;
        this.datasetRepository = datasetRepository;
        this.topicRepository = topicRepository;
        this.metadataRepository = metadataRepository;
        this.platformTypeRepository = platformTypeRepository;
        this.instrumentKeywordRepository = instrumentKeywordRepository;
        this.accountService = accountService;
        this.runtimeUserService = runtimeUserService;
        this.dataCenterRepository = dataCenterRepository;
        this.responsiblePartyFactory = responsiblePartyFactory;
    }

    // Integration entry point
    @Override
    public Dataset saveOrUpdate(DatasetDetails details) {

        Dataset dataset;

        String authoritativeIdentifier = details.getAuthoritativeIdentifier();

        if (datasetExists(authoritativeIdentifier)) {

            dataset = update(details);

        } else {

            dataset = save(details);
        }

        return dataset;
    }

    @Override
    public Dataset save(DatasetDetails details) {
        Dataset result = trySave(details);
        this.fireDatasetStoredEvent(result);
        return result;
    }

    // Called by integration
    @Transactional(propagation = Propagation.REQUIRED)
    protected Dataset trySave(DatasetDetails details) {

        Dataset parentDataset = null;

        if (details.getParentDatasetShortName() != null) {
            parentDataset = datasetRepository.getByShortName(details.getParentDatasetShortName());
        }

        String shortName = details.getShortName();
        String authoritativeIdentifier = details.getAuthoritativeIdentifier();
        Dataset dataset = datasetFactory.createCadisDataset(shortName, details.getTitle(), parentDataset, details.isBrokered(), authoritativeIdentifier);

        if (null != details.getDataCenterName()) {
            DataCenter dataCenter = this.getOverrideDataCenter(details);
            dataset.setDataCenter(dataCenter);
        }

        assignDatasetProperties(dataset, details);

        // Default Permissions
        User user = runtimeUserService.getUser();
        Group guestGroup = accountService.getGroup(Group.GROUP_GUEST);
        Group rootGroup = accountService.getGroup(Group.GROUP_ROOT);
        dataset.addPermission(guestGroup, Operation.READ);
        dataset.addPermission(rootGroup, Operation.WRITE);
        if (null != user) {
            dataset.addPermission(user, Operation.WRITE);
        }

        dataset.getCurrentDatasetVersion().publish();
        datasetRepository.add(dataset);

        return dataset;
    }

    @Override
    public Dataset update(DatasetDetails details) {
        Dataset result = tryUpdate(details);
        this.fireDatasetStoredEvent(result);
        return result;
    }

    // Called by integration
    @Transactional(propagation = Propagation.REQUIRED)
    protected Dataset tryUpdate(DatasetDetails details) {

        String shortName = details.getShortName();

        Dataset dataset = datasetRepository.getByShortName(shortName);

        dataset.setTitle(details.getTitle());
        assignDatasetProperties(dataset, details);

        return dataset;
    }

    // Called by ui
    @Override
    public Dataset createDataset(Dataset parentDataset, CadisDatasetDetails details) {

        Dataset result = tryCreate(parentDataset, details);

        this.fireDatasetStoredEvent(result);

        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Dataset moveDataset(CadisMoveDatasetCommand command) {

        Dataset datasetToMove = this.datasetRepository.getByShortName(command.getDatasetToMoveShortName());
        Dataset newParent = this.datasetRepository.getByShortName(command.getNewParentDatasetShortName());

        Dataset oldParent = datasetToMove.getParent();

        oldParent.removeChildDataset(datasetToMove);
        newParent.addChildDataset(datasetToMove);

        return datasetToMove;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Dataset moveDataset(Dataset datasetToMove, Dataset newParentDataset) {

        Dataset oldParent = datasetToMove.getParent();

        oldParent.removeChildDataset(datasetToMove);
        newParentDataset.addChildDataset(datasetToMove);

        return datasetToMove;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    protected Dataset tryCreate(Dataset parentDataset, CadisDatasetDetails details) {

        Dataset dataset = datasetFactory.createCadisDataset(details.getShortName(), details.getTitle(), parentDataset, false);

        assignDatasetProperties(dataset, details);

        // Default Permissions
        User user = runtimeUserService.getUser();

        Group guestGroup = accountService.getGroup(Group.GROUP_GUEST);
        Group rootGroup = accountService.getGroup(Group.GROUP_ROOT);

        dataset.addPermission(guestGroup, Operation.READ);
        dataset.addPermission(rootGroup, Operation.WRITE);
        dataset.addPermission(user, Operation.WRITE);

        dataset.getCurrentDatasetVersion().publish();

        datasetRepository.add(dataset);

        return dataset;
    }

    public Dataset updateDataset(String datasetShortName, CadisDatasetDetails createCadisDatasetRequest) {

        Dataset result = tryUpdate(datasetShortName, createCadisDatasetRequest);

        this.fireDatasetStoredEvent(result);

        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    protected Dataset tryUpdate(String datasetIdentifier, CadisDatasetDetails createCadisDatasetRequest) {

        Dataset dataset = datasetRepository.getByShortName(datasetIdentifier);

        dataset.setTitle(createCadisDatasetRequest.getTitle());
        dataset.setShortName(createCadisDatasetRequest.getShortName());

        assignDatasetProperties(dataset, createCadisDatasetRequest);

        return dataset;
    }

    protected void assignDatasetProperties(Dataset dataset, CadisDatasetDetails details) {

        dataset.setDescription(details.getDescription());

        HashSet<Topic> newTopics = new HashSet<>();
        for (UUID uuid : details.getIsoTopicIds()) {

            Topic topic = topicRepository.get(uuid);
            newTopics.add(topic);
        }
        dataset.mergeTopics(Taxonomy.ISO, newTopics);

        HashSet<DataFormat> newDataFormats = new HashSet<>();
        for (UUID uuid : details.getDistributionDataFormatIds()) {

            DataFormat dataFormat = metadataRepository.getDataFormat(uuid);
            newDataFormats.add(dataFormat);
        }
        dataset.mergeDataFormats(newDataFormats);

        // Work with the descriptive metadata
        DescriptiveMetadata descriptiveMetadata = dataset.getDescriptiveMetadata();

        descriptiveMetadata.setDatasetProgress(details.getDatasetProgress());

        HashSet<ScienceKeyword> scienceKeywords = new HashSet<>();
        for (UUID uuid : details.getScienceKeywordIds()) {

            ScienceKeyword scienceKeyword = metadataRepository.getScienceKeyword(uuid);
            scienceKeywords.add(scienceKeyword);
        }
        descriptiveMetadata.mergeScienceKeywords(scienceKeywords);


        HashSet<InstrumentKeyword> instrumentKeywords = new HashSet<>();
        if (details.getInstrumentKeywordIds() != null) {

            for (UUID uuid : details.getInstrumentKeywordIds()) {

                InstrumentKeyword instrumentKeyword = instrumentKeywordRepository.get(uuid);
                instrumentKeywords.add(instrumentKeyword);
            }
        }
        descriptiveMetadata.mergeInstrumentKeywords(instrumentKeywords);

        HashSet<Location> locations = new HashSet<>();
        for (UUID uuid : details.getLocationIds()) {

            Location location = datasetRepository.getLocation(uuid);
            locations.add(location);
        }
        descriptiveMetadata.mergeLocations(locations);

        HashSet<PlatformType> platformTypes = new HashSet<>();
        for (UUID uuid : details.getPlatformTypeIds()) {

            PlatformType platformType = platformTypeRepository.get(uuid);
            platformTypes.add(platformType);
        }
        descriptiveMetadata.mergePlatformTypes(platformTypes);

        HashSet<TimeFrequency> timeFrequencies = new HashSet<>();
        if (details.getTimeFrequencyIds() != null) {

            for (UUID uuid : details.getTimeFrequencyIds()) {

                TimeFrequency timeFrequency = metadataRepository.getTimeFrequency(uuid);
                timeFrequencies.add(timeFrequency);
            }
        }
        descriptiveMetadata.mergeTimeFrequencies(timeFrequencies);

        HashSet<DataType> dataTypes = new HashSet<>();
        if (details.getSpatialDataTypes() != null) {

            for (DataType dataType : details.getSpatialDataTypes()) {

                dataTypes.add(dataType);
            }
        }
        descriptiveMetadata.mergeDataTypes(dataTypes);

        ArrayList<CadisResolutionType> cadisResolutionTypes = new ArrayList<>();
        if (details.getResolutionTypes() != null) {

            for (CadisResolutionType resolutionType : details.getResolutionTypes()) {

                cadisResolutionTypes.add(resolutionType);
            }
        }
        descriptiveMetadata.mergeResolutionTypes(cadisResolutionTypes);

        descriptiveMetadata.setLanguage(details.getLanguage());

        // Following uncle bob we should have georectangle...
        dataset.getDescriptiveMetadata().setGeographicBoundingBox(Double.valueOf(details.getWesternLongitude()),
                Double.valueOf(details.getEasternLongitude()), Double.valueOf(details.getSouthernLatitude()),
                Double.valueOf(details.getNorthernLatitude()));

        if (details.getStartDate() != null && StringUtils.hasText(details.getStartDate())
                && details.getEndDate() != null && StringUtils.hasText(details.getEndDate())) {

            try {

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                dataset.getDescriptiveMetadata().setTimePeriod(dateFormat.parse(details.getStartDate()),
                        dateFormat.parse(details.getEndDate()));

            } catch (ParseException e) {

                throw new RuntimeException(e);
            }
        }
    }

    protected void assignDatasetProperties(Dataset dataset, DatasetDetails details) {

        dataset.setDescription(details.getDescription());

        dataset.getCurrentDatasetVersion().setAuthoritativeSourceURI(details.getAuthoritativeSourceURI());
        dataset.getCurrentDatasetVersion().setAuthoritativeSourceDateCreated(details.getAuthoritativeSourceDateCreated());
        dataset.getCurrentDatasetVersion().setAuthoritativeSourceDateModified(details.getAuthoritativeSourceDateModified());

        // Work with the descriptive metadata
        DescriptiveMetadata descriptiveMetadata = dataset.getDescriptiveMetadata();

        setContacts(descriptiveMetadata, details);

        // Following uncle bob we should have georectangle...
        descriptiveMetadata.setGeographicBoundingBox(details.getWesternLongitude(),
                details.getEasternLongitude(), details.getSouthernLatitude(),
                details.getNorthernLatitude());

        setDates(descriptiveMetadata, details);

        dataset.setDOI(details.getDoi());

        setDistributionLink(descriptiveMetadata, details);

        // NOTE just setting the first link in details for now. This code could become more logic intensive
        // depending on the use case.
        setDataAccessLink(descriptiveMetadata, details);
    }

    private void setDates(DescriptiveMetadata descriptiveMetadata, DatasetDetails details) {
        if (details.getStartDate() != null && details.getEndDate() != null) {
            try {
                descriptiveMetadata.setTimePeriod(details.getStartDate(), details.getEndDate());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void setContacts(DescriptiveMetadata descriptiveMetadata, DatasetDetails details) {

        descriptiveMetadata.mergeResponsibleParties(details.getContacts());
    }

    private void setDistributionLink(DescriptiveMetadata descriptiveMetadata, DatasetDetails details) {

        if (details.getDistributionURI() != null) {
            descriptiveMetadata.setDistributionURI(details.getDistributionURI());

            if (StringUtils.hasText(details.getDistributionText())) {
                descriptiveMetadata.setDistributionURIText(details.getDistributionText());
            } else {
                descriptiveMetadata.setDistributionURIText(details.getDistributionURI().toString());
            }
        }
    }

    private void setDataAccessLink(DescriptiveMetadata descriptiveMetadata, DatasetDetails details) {
        if (details.getDataAccessURI() != null && StringUtils.hasText(details.getDataAccessText())) {

            ArrayList<RelatedLink> accessLinks = new ArrayList<>();
            RelatedLink dataAccessLink = linkFactory.createLink(details.getDataAccessText(), details.getDataAccessURI());
            accessLinks.add(dataAccessLink);
            descriptiveMetadata.mergeRelatedLinks(accessLinks);
        } else {

            descriptiveMetadata.removeAllRelatedLinks();
        }
    }

    private DataCenter getOverrideDataCenter(DatasetDetails details) {

        String dataCenterName = details.getDataCenterName();

        return this.dataCenterRepository.getByName(dataCenterName);
    }

    private Boolean datasetExists(String authoritativeIdentifier) {

        Boolean exists = false;
        Dataset dataset = findDataset(authoritativeIdentifier);

        if (dataset != null) {
            exists = true;
        }

        return exists;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private Dataset findDataset(String authoritativeIdentifier) {

        return datasetRepository.findByAuthoritativeIdentifier(authoritativeIdentifier);
    }

    @Override
    public void addResponsibleParty(ResponsiblePartyDetails details) {

        Dataset dataset = this.tryAddResponsibleParty(details);

        this.fireDatasetStoredEvent(dataset);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Dataset tryAddResponsibleParty(ResponsiblePartyDetails details) {

        ResponsibleParty responsibleParty = this.responsiblePartyFactory.createResponsibleParty(details.getRole());
        responsibleParty.setIndividualName(details.getIndividualName());
        responsibleParty.setEmail(details.getEmail());

        Dataset dataset = this.datasetRepository.get(details.getDatasetIdentifier());

        dataset.getDescriptiveMetadata().addResponsibleParty(responsibleParty);

        return dataset;
    }

    @Override
    public void updateResponsibleParty(ResponsiblePartyDetails details) {

        Dataset dataset = this.tryUpdateResponsibleParty(details);

        this.fireDatasetStoredEvent(dataset);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Dataset tryUpdateResponsibleParty(ResponsiblePartyDetails details) {

        Dataset dataset = this.datasetRepository.get(details.getDatasetIdentifier());

        ResponsibleParty responsibleParty = dataset.getDescriptiveMetadata().getResponsibleParty(details.getResponsiblePartyIdentifier());

        responsibleParty.setRole(details.getRole());
        responsibleParty.setIndividualName(details.getIndividualName());
        responsibleParty.setEmail(details.getEmail());

        return dataset;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void reorderResponsibleParties(Dataset dataset, List<ResponsibleParty> parties) {

        DescriptiveMetadata metadata = dataset.getDescriptiveMetadata();

        metadata.orderResponsibleParties(parties);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void orderChildDatasets(Dataset dataset, List<Dataset> childDatasets) {

        dataset.orderChildDatasets(childDatasets);
    }


    @Override
    public void deleteResponsibleParty(DeleteResponsiblePartyDetails details) {

        Dataset dataset = this.tryDeleteResponsibleParty(details);

        this.fireDatasetStoredEvent(dataset);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Dataset tryDeleteResponsibleParty(DeleteResponsiblePartyDetails details) {

        Dataset dataset = this.datasetRepository.get(details.getDatasetIdentifier());

        dataset.getDescriptiveMetadata().removeResponsibleParty(details.getResponsiblePartyIdentifier());

        return dataset;
    }

    @Override
    public void addAwardToDataset(UUID identifier, String awardNumber) {

        Dataset dataset = this.tryAddAwardToDataset(identifier, awardNumber);

        this.fireDatasetStoredEvent(dataset);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private Dataset tryAddAwardToDataset(UUID identifier, String awardNumber) {

        Dataset dataset = datasetRepository.get(identifier);
        dataset.addAward(awardNumber);

        return dataset;
    }

    @Override
    public void deleteAwardFromDataset(UUID identifier, String awardNumber) {

        Dataset dataset = this.tryDeleteAwardFromDataset(identifier, awardNumber);

        this.fireDatasetStoredEvent(dataset);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private Dataset tryDeleteAwardFromDataset(UUID identifier, String awardNumber) {

        Dataset dataset = datasetRepository.get(identifier);

        Award award = dataset.getAward(awardNumber);

        dataset.removeAward(award);

        return dataset;
    }

    protected void fireDatasetStoredEvent(Dataset dataset) {
        this.applicationEventPublisher.publishEvent(new DatasetStoredEvent(this, dataset.getShortName()));
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

}
