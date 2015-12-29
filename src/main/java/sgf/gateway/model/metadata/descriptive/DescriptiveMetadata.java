package sgf.gateway.model.metadata.descriptive;

import org.safehaus.uuid.UUID;
import sgf.gateway.model.metadata.*;
import sgf.gateway.model.metadata.activities.observing.InstrumentKeyword;
import sgf.gateway.model.metadata.activities.observing.PlatformType;
import sgf.gateway.model.metadata.citation.ResponsibleParty;
import sgf.gateway.model.metadata.citation.ResponsiblePartyRole;

import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface DescriptiveMetadata {

    Dataset getDataset();

    Collection<ScienceKeyword> getScienceKeywords();

    void addScienceKeyword(ScienceKeyword newScienceKeyword);

    void mergeScienceKeywords(Set<ScienceKeyword> scienceKeywords);

    void removeScienceKeyword(ScienceKeyword scienceKeywordToRemove);

    void removeAllScienceKeywords();

    void addInstrumentKeyword(InstrumentKeyword instrument);

    void removeInstrumentKeyword(InstrumentKeyword instrument);

    void mergeInstrumentKeywords(Set<InstrumentKeyword> instrumentKeywords);

    /**
     * Adds the data type to the data type list if it doesn't already exist in the list.
     */
    void addDataType(DataType dataType);

    void mergeDataTypes(Set<DataType> dataTypes);

    void addLocation(Location newLocation);

    void mergeLocations(Set<Location> locations);

    /**
     * Adds the platform to the platform type list if it doesn't already exist in the list.
     */
    void addPlatformType(PlatformType platform);

    void mergePlatformTypes(Set<PlatformType> platformTypes);

    /**
     * Adds the time frequency to the time frequencies list if it doesn't already exist in the list.
     */
    void addTimeFrequency(TimeFrequency timeFrequency);

    void mergeTimeFrequencies(Set<TimeFrequency> timeFrequencies);

    void addResolutionType(CadisResolutionType resolutionType);

    Collection<CadisResolutionType> getResolutionTypes();

    void mergeResolutionTypes(List<CadisResolutionType> resolutionTypes);

    void removeAllResolutionTypes();

    DataProductType getDataProductType();

    Collection<InstrumentKeyword> getInstrumentKeywords();

    Collection<DataType> getDataTypes();

    String getLanguage();

    Collection<Location> getLocations();

    PhysicalDomain getPhysicalDomain();

    Collection<PlatformType> getPlatformTypes();

    ResolutionType getResolutionType();

    DatasetProgress getDatasetProgress();

    Collection<TimeFrequency> getTimeFrequencies();

    List<ResponsibleParty> getResponsibleParties();

    Collection<ResponsibleParty> getResponsiblePartiesByRole(ResponsiblePartyRole role);

    ResponsibleParty getResponsibleParty(UUID responsiblePartyIdentifier);

    //void setResponsibleParties(Collection<ResponsibleParty> contacts);

    void addResponsibleParty(ResponsibleParty contact);

    void removeResponsibleParty(UUID responsiblePartyIdentifier);

    void removeAllResponsibleParties();

    void removeResponsiblePartiesByRole(ResponsiblePartyRole role);

    void mergeResponsibleParties(List<ResponsibleParty> responsiblePartyList);

    void orderResponsibleParties(List<ResponsibleParty> responsibleParties);

    List<ResponsibleParty> getPointOfContacts();

    void removeAllInstrumentKeywords();

    void removeAllDataTypes();

    void removeDataType(DataType dataType);

    void removeAllRelatedLinks();

    void removeAllLocations();

    void removeAllPlatformTypes();

    void removeAllTimeFrequencies();

    void removeLocation(Location location);

    void removePlatformType(PlatformType platform);

    void removeTimeFrequency(TimeFrequency timeFrequency);

    RelatedLink getRelatedLink(UUID relatedLinkIdentifier);

    Collection<RelatedLink> getRelatedLinks();

    void addRelatedLink(RelatedLink link);

    void removeRelatedLink(RelatedLink link);

    void setRelatedLinks(Collection<RelatedLink> links);

    void mergeRelatedLinks(List<RelatedLink> accessLinks);

    void setDataProductType(DataProductType dataProductType);

    void setLanguage(String language);

    void setPhysicalDomain(PhysicalDomain physicalDomain);

    void setResolutionType(ResolutionType resolutionType);

    void setDatasetProgress(DatasetProgress datasetProgress);

    void setGeographicBoundingBox(Double westBoundLongitude, Double eastBoundLongitude, Double southBoundLatitude, Double northBoundLatitude);

    GeographicBoundingBox getGeographicBoundingBox();

    void setTimePeriod(Date begin, Date end);

    TimePeriod getTimePeriod();

    String getModelName();

    void setModelName(String modelName);

    void setCredit(String credit);

    String getCredit();

    URI getDistributionURI();

    void setDistributionURI(URI distributionURI);

    String getDistributionURIText();

    void setDistributionURIText(String distributionURIText);
}
