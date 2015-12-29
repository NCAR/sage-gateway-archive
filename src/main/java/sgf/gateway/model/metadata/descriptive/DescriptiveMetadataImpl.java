package sgf.gateway.model.metadata.descriptive;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.safehaus.uuid.UUID;
import sgf.gateway.audit.Auditable;
import sgf.gateway.model.AbstractPersistableChild;
import sgf.gateway.model.metadata.*;
import sgf.gateway.model.metadata.activities.observing.InstrumentKeyword;
import sgf.gateway.model.metadata.activities.observing.PlatformType;
import sgf.gateway.model.metadata.citation.ResponsibleParty;
import sgf.gateway.model.metadata.citation.ResponsiblePartyRole;
import sgf.gateway.utils.hibernate.CollectionMerger;

import java.net.URI;
import java.util.*;

@Audited
public class DescriptiveMetadataImpl extends AbstractPersistableChild implements DescriptiveMetadata, Auditable {

    @NotAudited
    private Dataset dataset;

    private Collection<RelatedLink> relatedLinksReference;

    private String language;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Collection<PlatformType> platformTypesReference;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Collection<InstrumentKeyword> instrumentKeywordsReference;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private DataProductType dataProductType;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Collection<DataType> dataTypesReference;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Collection<Location> locationsReference;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private PhysicalDomain physicalDomain;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private ResolutionType resolutionType = ResolutionType.UNKNOWN;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private DatasetProgress datasetProgress = DatasetProgress.NONE;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Collection<TimeFrequency> timeFrequenciesReference;

    private GeographicBoundingBox geographicBoundingBox;

    private TimePeriod timePeriod;

    private String modelName;

    List<ResponsibleParty> responsibleParties;

    private String credit;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Collection<CadisResolutionType> resolutionTypesReference = new ArrayList<CadisResolutionType>();

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Collection<ScienceKeyword> scienceKeywordsReference;

    private URI distributionURI;
    private String distributionURIText;

    /**
     * Instantiates a new project specific metadata.
     */
    public DescriptiveMetadataImpl() {

        super();

        initializeCommonDependencies();
    }

    /**
     * Instantiates a new gateway specific metadata.
     *
     * @param dataset the dataset
     */
    public DescriptiveMetadataImpl(Dataset dataset) {

        super(dataset);

        this.dataset = dataset;  // also the parent

        initializeCommonDependencies();
    }

    private void initializeCommonDependencies() {

        this.relatedLinksReference = new ArrayList<>();
        this.platformTypesReference = new HashSet<>();
        this.instrumentKeywordsReference = new HashSet<>();
        this.dataTypesReference = new HashSet<>();
        this.locationsReference = new HashSet<>();
        this.timeFrequenciesReference = new HashSet<>();
        this.responsibleParties = new ArrayList<>();
        this.scienceKeywordsReference = new HashSet<>();
    }

    public Dataset getDataset() {
        return this.dataset;
    }

    @Override
    public void setTimePeriod(Date begin, Date end) {

        if (null == this.timePeriod) {
            this.timePeriod = new TimePeriodImpl(this);
        }

        this.timePeriod.setBegin(begin);
        this.timePeriod.setEnd(end);
    }

    @Override
    public TimePeriod getTimePeriod() {

        return this.timePeriod;
    }

    public Collection<ScienceKeyword> getScienceKeywords() {

        return this.getScienceKeywordReference();
    }

    protected Collection<ScienceKeyword> getScienceKeywordReference() {

        return this.scienceKeywordsReference;
    }


    public void addScienceKeyword(ScienceKeyword newScienceKeyword) {

        this.getScienceKeywordReference().add(newScienceKeyword);
    }

    @Override
    public void mergeScienceKeywords(Set<ScienceKeyword> scienceKeywords) {

        CollectionMerger.mergeCollection(this.getScienceKeywordReference(), scienceKeywords);
    }

    public void removeScienceKeyword(ScienceKeyword scienceKeywordToRemove) {

        if (this.getScienceKeywordReference().contains(scienceKeywordToRemove)) {

            this.getScienceKeywordReference().remove(scienceKeywordToRemove);
        }
    }

    public void removeAllScienceKeywords() {

        this.scienceKeywordsReference = new HashSet<>();
    }

    @Override
    public void setGeographicBoundingBox(Double westBoundLongitude, Double eastBoundLongitude,
                                         Double southBoundLatitude, Double northBoundLatitude) {

        if (null == this.geographicBoundingBox) {
            this.geographicBoundingBox = new GeographicBoundingBoxImpl(this);
        }

        this.geographicBoundingBox.setWestBoundLongitude(westBoundLongitude);
        this.geographicBoundingBox.setEastBoundLongitude(eastBoundLongitude);
        this.geographicBoundingBox.setSouthBoundLatitude(southBoundLatitude);
        this.geographicBoundingBox.setNorthBoundLatitude(northBoundLatitude);
    }

    @Override
    public GeographicBoundingBox getGeographicBoundingBox() {

        return this.geographicBoundingBox;
    }

    public void addInstrumentKeyword(InstrumentKeyword instrument) {

        // Make sure we don't have another copy of this, the storage type
        // may allow for multiple instances of the same type.
        if (getInstrumentKeywords().contains(instrument)) {
            throw new IllegalStateException("Dataset already contains the specified instrument keyword.");
        }

        this.instrumentKeywordsReference.add(instrument);
    }

    /**
     * {@inheritDoc}
     */
    public void addDataType(DataType dataType) {

        // Make suer we don't have another copy of this, the storage type
        // may allow for multiple instances of the same type.
        if (this.dataTypesReference.contains(dataType)) {
            throw new IllegalStateException("Dataset already contains the specified data type.");
        }

        this.dataTypesReference.add(dataType);
    }

    @Override
    public void mergeDataTypes(Set<DataType> dataTypes) {

        CollectionMerger.mergeCollection(this.getDataTypesReference(), dataTypes);
    }

    /**
     * {@inheritDoc}
     */
    public void addLocation(Location newLocation) {

        // Make sure we don't have another copy of this, the storage type
        // may allow for multiple instances of the same type.
        if (getLocations().contains(newLocation)) {
            throw new IllegalStateException("Dataset already contains the specified location.");
        }

        this.locationsReference.add(newLocation);

    }

    @Override
    public void mergeLocations(Set<Location> locations) {

        CollectionMerger.mergeCollection(this.getLocationsReference(), locations);
    }

    /**
     * {@inheritDoc}
     */
    public void addPlatformType(PlatformType platform) {

        // Make sure we don't have another copy of this, the storage type
        // may allow for multiple instances of the same type.
        if (getPlatformTypes().contains(platform)) {
            throw new IllegalStateException("Dataset already contains the specified platform type.");
        }

        this.platformTypesReference.add(platform);
    }

    @Override
    public void mergePlatformTypes(Set<PlatformType> platformTypes) {

        CollectionMerger.mergeCollection(this.getPlatformTypesReference(), platformTypes);
    }

    /**
     * {@inheritDoc}
     */
    public void addTimeFrequency(TimeFrequency timeFrequency) {
        this.timeFrequenciesReference.add(timeFrequency);
    }

    @Override
    public void mergeTimeFrequencies(Set<TimeFrequency> timeFrequencies) {

        CollectionMerger.mergeCollection(this.getTimeFrequenciesReference(), timeFrequencies);
    }

    /**
     * {@inheritDoc}
     */
    public DataProductType getDataProductType() {

        return this.dataProductType;
    }

    /**
     * {@inheritDoc}
     */
    public Collection<DataType> getDataTypes() {

        return this.dataTypesReference;
    }

    private Collection<DataType> getDataTypesReference() {
        return this.dataTypesReference;
    }

    public Collection<InstrumentKeyword> getInstrumentKeywords() {

        return this.instrumentKeywordsReference;
    }

    private Collection<InstrumentKeyword> getInstrumentKeywordsReference() {

        return this.instrumentKeywordsReference;
    }

    /**
     * {@inheritDoc}
     */
    public String getLanguage() {

        return this.language;
    }

    /**
     * {@inheritDoc}
     */
    public Collection<Location> getLocations() {

        return this.locationsReference;
    }

    private Collection<Location> getLocationsReference() {

        return this.locationsReference;
    }

    /**
     * {@inheritDoc}
     */
    public PhysicalDomain getPhysicalDomain() {

        return this.physicalDomain;
    }

    /**
     * {@inheritDoc}
     */
    public Collection<PlatformType> getPlatformTypes() {

        return this.platformTypesReference;
    }

    private Collection<PlatformType> getPlatformTypesReference() {

        return this.platformTypesReference;
    }

    /**
     * {@inheritDoc}
     */
    public Collection<RelatedLink> getRelatedLinks() {

        return getRelatedLinksReference();
    }

    private Collection<RelatedLink> getRelatedLinksReference() {

        return this.relatedLinksReference;
    }

    /**
     * {@inheritDoc}
     */
    public void addRelatedLink(RelatedLink link) {

        this.relatedLinksReference.add(link);
    }

    /**
     * {@inheritDoc}
     */
    public void setRelatedLinks(Collection<RelatedLink> links) {

        this.relatedLinksReference = links;
    }

    @Override
    public void mergeRelatedLinks(List<RelatedLink> relatedLinks) {

        CollectionMerger.mergeList((List<RelatedLink>) this.getRelatedLinksReference(), relatedLinks);
    }

    /**
     * {@inheritDoc}
     */
    public void removeRelatedLink(RelatedLink link) {

        this.getRelatedLinksReference().remove(link);
    }

    /**
     * {@inheritDoc}
     */
    public void removeAllRelatedLinks() {

        getRelatedLinksReference().clear();
    }

    public RelatedLink getRelatedLink(UUID relatedLinkIdentifier) {

        RelatedLink result = null;

        for (RelatedLink link : this.getRelatedLinks()) {

            if (link.getIdentifier().equals(relatedLinkIdentifier)) {

                result = link;
                break;
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    public ResolutionType getResolutionType() {

        return this.resolutionType;
    }

    /**
     * {@inheritDoc}
     */
    public DatasetProgress getDatasetProgress() {

        return this.datasetProgress;
    }

    /**
     * {@inheritDoc}
     */
    public Collection<TimeFrequency> getTimeFrequencies() {

        return this.timeFrequenciesReference;
    }

    private Collection<TimeFrequency> getTimeFrequenciesReference() {

        return this.timeFrequenciesReference;
    }

    public void removeAllInstrumentKeywords() {

        getInstrumentKeywordsReference().clear();
    }

    /**
     * {@inheritDoc}
     */
    public void removeAllDataTypes() {

        this.dataTypesReference.clear();
    }


    /**
     * {@inheritDoc}
     */
    public void removeAllLocations() {

        getLocationsReference().clear();
    }

    /**
     * {@inheritDoc}
     */
    public void removeAllPlatformTypes() {

        getPlatformTypesReference().clear();
    }

    /**
     * {@inheritDoc}
     */
    public void removeAllTimeFrequencies() {

        this.timeFrequenciesReference.clear();
    }

    public void removeInstrumentKeyword(InstrumentKeyword instrument) {

        this.getInstrumentKeywordsReference().remove(instrument);
    }

    @Override
    public void mergeInstrumentKeywords(Set<InstrumentKeyword> instrumentKeywords) {

        CollectionMerger.mergeCollection(this.getInstrumentKeywordsReference(), instrumentKeywords);
    }

    /**
     * {@inheritDoc}
     */
    public void removeDataType(DataType dataType) {

        this.dataTypesReference.remove(dataType);
    }

    /**
     * {@inheritDoc}
     */
    public void removeLocation(Location location) {

        this.getLocationsReference().remove(location);
    }

    /**
     * {@inheritDoc}
     */
    public void removePlatformType(PlatformType platform) {

        this.getPlatformTypesReference().remove(platform);
    }

    /**
     * {@inheritDoc}
     */
    public void removeTimeFrequency(TimeFrequency timeFrequency) {

        this.timeFrequenciesReference.remove(timeFrequency);
    }

    /**
     * {@inheritDoc}
     */
    public void setDataProductType(DataProductType dataProductType) {

        this.dataProductType = dataProductType;
    }

    /**
     * {@inheritDoc}
     */
    public void setLanguage(String language) {

        this.language = language;
    }

    /**
     * {@inheritDoc}
     */
    public void setPhysicalDomain(PhysicalDomain physicalDomain) {

        this.physicalDomain = physicalDomain;
    }

    /**
     * {@inheritDoc}
     */
    public void setResolutionType(ResolutionType resolutionType) {

        this.resolutionType = resolutionType;
    }

    /**
     * {@inheritDoc}
     */
    public void setDatasetProgress(DatasetProgress datasetProgress) {

        this.datasetProgress = datasetProgress;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    @Override
    public void addResponsibleParty(ResponsibleParty contact) {

        this.responsibleParties.add(contact);
    }

    @Override
    public void mergeResponsibleParties(List<ResponsibleParty> responsibleParties) {

        CollectionMerger.mergeList(this.getResponsibleParties(), responsibleParties);
    }

    @Override
    public void orderResponsibleParties(final List<ResponsibleParty> responsibleParties) {

        Collections.sort(this.getResponsibleParties(), new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return Integer.toString(responsibleParties.indexOf(o1)).compareTo(Integer.toString(responsibleParties.indexOf(o2)));
            }
        });
    }

    @Override
    public List<ResponsibleParty> getResponsibleParties() {
        return responsibleParties;
    }

    @Override
    public ResponsibleParty getResponsibleParty(UUID responsiblePartyId) {

        ResponsibleParty result = null;

        for (ResponsibleParty responsibleParty : this.getResponsibleParties()) {

            if (responsibleParty.getIdentifier().equals(responsiblePartyId)) {

                result = responsibleParty;
                break;
            }
        }

        return result;
    }

    @Override
    public Collection<ResponsibleParty> getResponsiblePartiesByRole(ResponsiblePartyRole role) {

        Collection<ResponsibleParty> result = new ArrayList<>();

        for (ResponsibleParty responsibleParty : this.getResponsibleParties()) {

            if (responsibleParty.getRole() == role) {
                result.add(responsibleParty);
            }
        }

        return result;
    }

    @Override
    public void removeResponsibleParty(UUID responsiblePartyId) {

        ResponsibleParty responsibleParty = this.getResponsibleParty(responsiblePartyId);

        this.responsibleParties.remove(responsibleParty);
    }

    @Override
    public void removeResponsiblePartiesByRole(ResponsiblePartyRole role) {

        for (ResponsibleParty responsibleParty : this.responsibleParties) {

            if (responsibleParty.getRole() == role) {
                responsibleParties.remove(responsibleParty);
            }
        }
    }

    public void removeAllResponsibleParties() {
        this.responsibleParties.clear();
    }

    public List<ResponsibleParty> getPointOfContacts() {

        ResponsiblePartyRole pointOfContactRole = ResponsiblePartyRole.pointOfContact;

        Collection<ResponsibleParty> pointOfContacts = getResponsiblePartiesByRole(pointOfContactRole);

        return (List<ResponsibleParty>) pointOfContacts;
    }

    public void setCredit(String credit) {

        this.credit = credit;
    }

    public String getCredit() {

        return this.credit;
    }

    @Override
    public void setDistributionURI(URI distributionURI) {
        this.distributionURI = distributionURI;
    }

    @Override
    public URI getDistributionURI() {
        return this.distributionURI;
    }

    @Override
    public String getDistributionURIText() {
        return distributionURIText;
    }

    @Override
    public void setDistributionURIText(String distributionURIText) {
        this.distributionURIText = distributionURIText;
    }


    public void addResolutionType(CadisResolutionType resolutionType) {

        if (!this.resolutionTypesReference.contains(resolutionType)) {
            this.resolutionTypesReference.add(resolutionType);
        }
    }

    public Collection<CadisResolutionType> getResolutionTypes() {

        return this.resolutionTypesReference;
    }

    @Override
    public void mergeResolutionTypes(List<CadisResolutionType> resolutionTypes) {

        CollectionMerger.mergeList((List<CadisResolutionType>) this.getResolutionTypes(), resolutionTypes);
    }

    public void removeAllResolutionTypes() {

        this.resolutionTypesReference.clear();
    }
}
