package sgf.gateway.web.controllers.cadis.publish;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import sgf.gateway.dao.metadata.*;
import sgf.gateway.model.metadata.*;
import sgf.gateway.model.metadata.activities.observing.InstrumentKeyword;
import sgf.gateway.model.metadata.activities.observing.PlatformType;
import sgf.gateway.model.metadata.citation.ResponsibleParty;
import sgf.gateway.model.metadata.citation.ResponsiblePartyRole;
import sgf.gateway.service.security.AuthorizationUtils;

import java.util.*;

public class AbstractCadisDatasetController {

    private final DatasetRepository datasetRepository;
    private final MetadataRepository metadataRepository;
    private final InstrumentKeywordRepository instrumentKeywordRepository;
    private final PlatformTypeRepository platformTypeRepository;
    private final TopicRepository topicRepository;
    protected final AuthorizationUtils authorizationUtils;

    public AbstractCadisDatasetController(DatasetRepository datasetRepository,
                                          MetadataRepository metadataRepository,
                                          InstrumentKeywordRepository instrumentKeywordRepository,
                                          PlatformTypeRepository platformTypeRepository,
                                          TopicRepository topicRepository, AuthorizationUtils authorizationUtils) {

        super();
        this.datasetRepository = datasetRepository;
        this.metadataRepository = metadataRepository;
        this.instrumentKeywordRepository = instrumentKeywordRepository;
        this.platformTypeRepository = platformTypeRepository;
        this.topicRepository = topicRepository;
        this.authorizationUtils = authorizationUtils;
    }

    @ModelAttribute("locations")
    public List<Location> getLocations() {

        List<Location> locations = new ArrayList<>(this.datasetRepository.getAllLocations());

        Collections.sort(locations, new LocationComparator());

        return locations;
    }

    @ModelAttribute("platformTypes")
    List<PlatformType> getPlatformTypes() {

        return platformTypeRepository.getAll();
    }

    @ModelAttribute("instrumentKeywords")
    List<InstrumentKeyword> getInstrumentKeywords() {

        return instrumentKeywordRepository.getAll();
    }


    @ModelAttribute(value = "scienceKeywords")
    public List<ScienceKeyword> getScienceKeywords() {

        List<ScienceKeyword> scienceKeywords = new ArrayList<>(this.metadataRepository.getScienceKeywords());

        return scienceKeywords;
    }

    @ModelAttribute(value = "isoTopics")
    public List<Topic> getIsoTopics() {

        List<Topic> isoTopics = new ArrayList<>(this.topicRepository.findByTaxonomy(Taxonomy.ISO));

        Collections.sort(isoTopics, new TopicComparator());

        return isoTopics;
    }

    @ModelAttribute(value = "metadataContactsForDataset")
    public List<ResponsibleParty> getMetadataContactsForDataset(@PathVariable(value = "dataset") Dataset parentDataset) {

        Collection<ResponsibleParty> contacts = parentDataset.getDescriptiveMetadata().getResponsiblePartiesByRole(ResponsiblePartyRole.pointOfContact);

        List<ResponsibleParty> contactList = new ArrayList<>(contacts);

        return contactList;
    }

    @ModelAttribute(value = "distributionFormats")
    public List<DataFormat> getDistributionFormats() {

        List<DataFormat> dataFormats = this.metadataRepository.findDataFormats();

        return dataFormats;
    }

    @ModelAttribute(value = "frequencies")
    public List<TimeFrequency> getFrequencies() {

        List<TimeFrequency> frequencies = this.metadataRepository.findTimeFrequencies();

        return frequencies;
    }

    @ModelAttribute(value = "spatialTypes")
    public List<DataType> getSpatialTypes() {

        // Get from DataType enum
        List<DataType> dataTypesList = new ArrayList<>();

        Collections.addAll(dataTypesList, DataType.values());

        return dataTypesList;
    }

    @ModelAttribute(value = "resolutions")
    public List<CadisResolutionType> getResolutions() {

        // CadisResolutionType enum
        List<CadisResolutionType> cadisResolutionTypesList = new ArrayList<>();

        Collections.addAll(cadisResolutionTypesList, CadisResolutionType.values());

        return cadisResolutionTypesList;

    }

    @ModelAttribute(value = "datasetProgress")
    public List<DatasetProgress> getDatasetProgress() {

        DatasetProgress[] states = DatasetProgress.values();

        List<DatasetProgress> datasetProgressList = Arrays.asList(states);

        return datasetProgressList;
    }

}