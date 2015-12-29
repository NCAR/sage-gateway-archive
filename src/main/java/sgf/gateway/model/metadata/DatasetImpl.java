package sgf.gateway.model.metadata;

import org.apache.commons.collections.collection.UnmodifiableCollection;
import org.apache.commons.collections.list.UnmodifiableList;
import org.apache.commons.collections.map.UnmodifiableMap;
import org.apache.commons.lang.StringUtils;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.util.Assert;
import sgf.gateway.audit.Auditable;
import sgf.gateway.model.AbstractPersistableEntity;
import sgf.gateway.model.metadata.activities.Activity;
import sgf.gateway.model.metadata.activities.ActivityType;
import sgf.gateway.model.metadata.activities.project.Award;
import sgf.gateway.model.metadata.descriptive.DescriptiveMetadata;
import sgf.gateway.model.metadata.descriptive.Institution;
import sgf.gateway.model.metadata.factory.SoftwarePropertiesFactory;
import sgf.gateway.model.security.Group;
import sgf.gateway.model.security.Operation;
import sgf.gateway.model.security.Permission;
import sgf.gateway.model.security.Principal;
import sgf.gateway.service.security.impl.acegi.AcegiUtils;
import sgf.gateway.utils.hibernate.CollectionMerger;

import java.io.Serializable;
import java.net.URI;
import java.util.*;

@Audited
public class DatasetImpl extends AbstractPersistableEntity implements LicensedResource, Dataset, Auditable {

    private String authoritativeIdentifier;

    private String shortName;

    private String title;

    private String description;

    private Boolean brokered = Boolean.FALSE;

    private Boolean isVisible = Boolean.TRUE;

    private String doi;

    private String projectGroup;

    @NotAudited
    private Long nestedDatasetCount;

    @NotAudited
    private Dataset parentDataset;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Dataset predecessorProject;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Dataset successorProject;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private ContainerType containerType;

    private DescriptiveMetadata descriptiveMetadata;

    private SoftwareProperties softwareProperties;

    @NotAudited
    private MetadataProfile metadataProfile;

    @NotAudited
    private License license;

    private Institution institution;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private DataCenter dataCenter;

    private List<Dataset> nestedDatasetsReference;

    private List<DatasetVersion> datasetVersionsReference;

    @NotAudited
    private Set<Permission> permissions = new HashSet<Permission>();

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Collection<Topic> topicsReference;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Collection<DataFormat> dataFormatsReference;

    @NotAudited
    private Map<ActivityType, Activity> activitiesReference;

    private List<Award> awards;


    protected DatasetImpl() {
        super(false);
    }

    public DatasetImpl(Serializable identifier, Serializable version) {
        super(identifier, version);
    }

    protected DatasetImpl(boolean createIdentifier) {
        super(createIdentifier);
    }

    /**
     * Constructor to set all immutable properties.
     * <p/>
     * FIXME - Would be better to create a builder for this so this can stay package scope (factory accessible).
     *
     * @param identifier the identifier (required)
     * @param version    the version
     * @param title      the title
     * @param shortName  the short name
     */
    public DatasetImpl(Serializable identifier, Serializable version, String title, String shortName, String authoritativeIdentifier) {

        this(identifier, version);

        this.title = title;
        this.shortName = shortName;
        this.topicsReference = new HashSet<Topic>();
        this.dataFormatsReference = new HashSet<DataFormat>();
        this.nestedDatasetsReference = new ArrayList<Dataset>();
        this.datasetVersionsReference = new ArrayList<DatasetVersion>();
        this.activitiesReference = new HashMap<ActivityType, Activity>();
        this.authoritativeIdentifier = authoritativeIdentifier;
        this.awards = new ArrayList<Award>();

        // TODO - Should be setting the dataset state?
        // ANSWER? - If this is a new dataset then yes, I think we should be
        // setting the state to unpublished. NCH 8/6/09
        // Also, the state is being set at attribute declaration above.
    }

    public DatasetImpl(Serializable identifier, Serializable version, String title, String shortName, MetadataProfile metadataProfile,
                       String authoritativeIdentifier) {

        this(identifier, version, title, shortName, authoritativeIdentifier);
        this.metadataProfile = metadataProfile;
    }

    /**
     * {@inheritDoc}
     */
    public String getShortName() {

        return this.shortName;
    }


    /**
     * This method is only being using for the DIFMetadataHandler.
     * The logic in this class will need to be refactored in order to avoid use of a setter for short name.
     *
     * @param shortName
     */
    @Deprecated
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    protected Dataset getParentDataset() {

        return parentDataset;
    }

    public Dataset getParent() {

        Dataset parent = this.getParentDataset();

        return parent;
    }

    public Integer getIndexOf(Dataset nestedDataset) {

        return this.getNestedDatasetsReference().indexOf(nestedDataset);
    }

    public boolean isTopLevelDataset() {

        boolean result = false;

        if (this.getParentDataset() == null) {

            result = true;
        }

        return result;
    }

    @Override
    public Dataset getRootParentDataset() {

        Dataset rootDataset = null;

        if (this.isTopLevelDataset() == true) {

            return this;
        } else {

            List<Dataset> parentList = this.getParentList();

            for (Dataset parentDataset : parentList) {

                if (parentDataset.isTopLevelDataset() == true) {

                    rootDataset = parentDataset;
                    break;
                }
            }
        }

        return rootDataset;
    }


    protected List<DatasetVersion> getDatasetVersionsReference() {

        return this.datasetVersionsReference;
    }

    public void addDatasetVersion(DatasetVersion datasetVersion) {

        this.getDatasetVersionsReference().add(datasetVersion);
    }

    public Collection<DatasetVersion> getDatasetVersions() {

        return UnmodifiableCollection.decorate(this.getDatasetVersionsReference());
    }

    public DatasetVersion getCurrentDatasetVersion() {

        DatasetVersion result = null;

        List<DatasetVersion> datasetVersionList = new ArrayList<DatasetVersion>(this.getDatasetVersionsReference());

        if (!datasetVersionList.isEmpty()) {

            int size = datasetVersionList.size();

            result = datasetVersionList.get(size - 1);
        }

        return result;
    }

    public DatasetVersion getDatasetVersion(String datasetVersionIdentifier) {

        DatasetVersion datasetVersion = null;

        for (DatasetVersion version : this.getDatasetVersionsReference()) {

            if (datasetVersionIdentifier.equals(version.getVersionIdentifier())) {

                datasetVersion = version;

                break;
            }
        }

        return datasetVersion;
    }

    /**
     * Retrieves the internal dataset list. Proxy safe.
     *
     * @return Internal list of datasets.
     */
    protected List<Dataset> getNestedDatasetsReference() {

        return this.nestedDatasetsReference;
    }

    public Collection<Dataset> getDirectlyNestedDatasets() {

        // FIXME - Why doesn't this work?
        return UnmodifiableList.decorate(this.getNestedDatasetsReference());
    }

    public Long getNestedDatasetCount() {

        Long result;

        if (null == this.nestedDatasetCount) {

            result = new Long(this.getNestedDatasetsReference().size());

        } else {

            result = this.nestedDatasetCount;
        }

        return result;
    }

    /**
     * {@inheritDoc}
     * <p/>
     * Handles all the logic for ensuring that the bidirectional parent child relationship is properly maintained.
     * <p/>
     * TODO - Need to test the alternate cases.
     */
    public final void addChildDataset(Dataset dataset) {

        // Make sure we don't have another copy of this, the storage type
        // may allow for multiple instances of the same type.
        if (this.getNestedDatasetsReference().contains(dataset)) {

            throw new IllegalStateException("Dataset already contains the specified child dataset");
        }

        this.getNestedDatasetsReference().add(dataset);
        ((DatasetImpl) dataset).setParentDataset(this);
    }

    void setParentDataset(Dataset parentDataset) {

        this.parentDataset = parentDataset;
    }

    public final void removeChildDataset(Dataset dataset) {

        List<Dataset> childDatasets = this.getNestedDatasetsReference();

        childDatasets.remove(dataset);
    }

    @Override
    public void orderChildDatasets(final List<Dataset> childDatasets) {

        Collections.sort(this.getNestedDatasetsReference(), new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return Integer.toString(childDatasets.indexOf(o1)).compareTo(Integer.toString(childDatasets.indexOf(o2)));
            }
        });
    }

    //
    // STATE METHODS
    // STATE METHODS
    // STATE METHODS
    //

    /**
     * Returns the raw state value.
     * <p/>
     * In the future this may delegate to a real state implementation.
     *
     * @return the dataset state
     */
    public PublishedState getPublishedState() {

        return this.getCurrentDatasetVersion().getPublishedState();
    }

    public void delete() {

        // Remove this instance from the parent
        if (!isTopLevelDataset()) {

            this.getParent().removeChildDataset(this);
        }
    }

    public boolean isRetracted() {

        return this.getCurrentDatasetVersion().isRetracted();
    }

    public boolean isDeleted() {

        return this.getCurrentDatasetVersion().isDeleted();
    }

    public boolean isPublished() {

        return this.getCurrentDatasetVersion().isPublished();
    }

    public boolean isMetadataOnly() {

        boolean metadataOnly = false;

        if (this.getDirectlyNestedDatasets().isEmpty() && this.getCurrentDatasetVersion().getLogicalFiles().isEmpty()) {

            metadataOnly = true;
        }

        return metadataOnly;
    }

    public boolean isBrokered() {

        return this.brokered;
    }

    public void setBrokered(Boolean brokered) {

        this.brokered = brokered;
    }

    public String getAuthoritativeIdentifier() {

        return authoritativeIdentifier;
    }

    public void setAuthoritativeIdentifier(String authoritativeIdentifier) {

        this.authoritativeIdentifier = authoritativeIdentifier;
    }

    @Override
    public Institution getInstitution() {

        return this.institution;
    }

    @Override
    public void setInstitution(Institution newInstitution) {

        this.institution = newInstitution;
    }

    public Boolean isVisible() {

        return isVisible;
    }

    public void setVisible(Boolean isVisible) {

        this.isVisible = isVisible;
    }


    //
    // LOGICAL FILE METHODS
    // LOGICAL FILE METHODS
    // LOGICAL FILE METHODS
    //

    protected Collection<DataFormat> getDataFormatsReference() {

        return this.dataFormatsReference;
    }

    public Collection<DataFormat> getDataFormats() {

        return this.getDataFormatsReference();
    }

    public void addDataFormat(DataFormat newDataFormat) {

        this.getDataFormatsReference().add(newDataFormat);
    }

    @Override
    public void mergeDataFormats(HashSet<DataFormat> newDataFormats) {

        CollectionMerger.mergeCollection(this.getDataFormats(), newDataFormats);
    }

    public void removeDataFormat(DataFormat dataFormatToRemove) {

        if (this.getDataFormatsReference().contains(dataFormatToRemove)) {
            this.getDataFormatsReference().remove(dataFormatToRemove);
        }
    }

    public void removeAllDataFormats() {

        this.getDataFormatsReference().clear();
    }

    //
    // METADATA METHODS
    // METADATA METHODS
    // METADATA METHODS
    //

    public MetadataProfile getMetadataProfile() {

        MetadataProfile theProfile = null;

        if (this.metadataProfile != null) {

            theProfile = this.metadataProfile;

        } else if (this.getParentDataset() != null) {

            theProfile = this.getParentDataset().getMetadataProfile();

        }

        return theProfile;
    }

    public DescriptiveMetadata getDescriptiveMetadata() {

        return descriptiveMetadata;
    }

    public void initializeDescriptiveMetadata(DescriptiveMetadata descriptiveMetadata) {

        // TODO: This method only seems to be called from the
        // DatasetFactoryImpl. Should we maybe consider changing having
        // DescriptiveMetadata
        // as part of the constructor?
        Assert.notNull(descriptiveMetadata, "DescriptiveMetadata cannot be null.");

        if (null != this.getDescriptiveMetadata()) {

            throw new IllegalStateException("Cannot overwrite existing DescriptiveMetadata instance");
        }

        this.descriptiveMetadata = descriptiveMetadata;
    }

    @Override
    public List<Dataset> getParentList() {

        List<Dataset> parentList = new ArrayList<Dataset>();

        this.addParentToList(parentList, this);

        return parentList;
    }

    private void addParentToList(List<Dataset> parentList, Dataset dataset) {

        if (dataset.isTopLevelDataset() == false) {

            Dataset datasetParent = dataset.getParent();

            parentList.add(0, datasetParent);

            this.addParentToList(parentList, datasetParent);
        }
    }

    //
    // ACTIVITY METHODS
    // ACTIVITY METHODS
    // ACTIVITY METHODS
    //

    protected Map<ActivityType, Activity> getActivitiesReference() {

        return this.activitiesReference;
    }

    public void associateActivity(Activity activity) {

        this.getActivitiesReference().put(activity.getActivityType(), activity);
    }

    public void removeActivity(Activity activity) {

        if (this.getActivitiesReference().containsValue(activity)) {

            this.getActivitiesReference().remove(activity.getActivityType());
        }
    }

    public void removeAllActivities() {

        this.getActivitiesReference().clear();
    }

    public Activity getActivity(ActivityType activityType) {

        Activity activity = this.getActivitiesReference().get(activityType);

        if ((activity == null) && (this.getParentDataset() != null)) {
            activity = this.getParentDataset().getActivity(activityType); // recursion
        }

        return activity;
    }

    public Activity getActivity(String activityTypeName) {

        return this.getActivity(ActivityType.valueOf(activityTypeName.toUpperCase()));
    }

    public Map<ActivityType, Activity> getActivities() {

        return UnmodifiableMap.decorate(this.getActivitiesReference());
    }

    public Collection<Activity> getActivityList() {

        return UnmodifiableCollection.decorate(this.getActivitiesReference().values());
    }

    public Collection<Activity> getAllActivities() {

        Set<Activity> completeList = new HashSet<Activity>();

        completeList.addAll(getActivityList());

        // If were not a top level dataset, include the parents
        if (!this.isTopLevelDataset()) {

            completeList.addAll(this.getParentDataset().getAllActivities());
        }

        return UnmodifiableCollection.decorate(completeList);
    }

    protected int getInheritedActivityListCount() {

        int result = 0;

        if (!this.isTopLevelDataset()) {
            // FIXME - See if we really need this....
            result = this.getParentDataset().getAllActivities().size();
        }

        return result;
    }

    public boolean getHasAssociatedActivities() {

        int totalCount = getActivityList().size() + getInheritedActivityListCount();

        return (totalCount > 0);
    }

    protected Collection<Topic> getTopicsReference() {

        return this.topicsReference;
    }

    public Collection<Topic> getTopics() {

        return UnmodifiableCollection.decorate(this.getTopicsReference());
    }

    public Collection<Topic> getIsoTopics() {

        Collection<Topic> isoTopics = new HashSet<Topic>();

        Collection<Topic> topics = this.getTopicsReference();

        for (Topic topic : topics) {

            if (Taxonomy.ISO == topic.getType()) {

                isoTopics.add(topic);
            }

        }

        return UnmodifiableCollection.decorate(isoTopics);
    }

    @Override
    public void mergeTopics(Taxonomy taxonomy, HashSet<Topic> newTopics) {

        CollectionMerger.mergeTopicCollection(taxonomy, this.getTopicsReference(), newTopics);
    }


    @Deprecated
    public Collection<Topic> getInheritedTopics() {

        Collection<Topic> allTopics = new HashSet<Topic>(this.getTopicsReference());

        if (this.getParentDataset() != null) {

            allTopics.addAll(this.getParentDataset().getInheritedTopics());
        }

        return UnmodifiableCollection.decorate(allTopics);
    }

    public void addTopic(Topic newTopic) {

        this.getTopicsReference().add(newTopic);
    }

    public void removeTopic(Topic topicToRemove) {

        if (this.getTopicsReference().contains(topicToRemove)) {

            this.getTopicsReference().remove(topicToRemove);
            topicToRemove.removeBackLink(this);
        }
    }

    public void removeAllTopics() {

        this.topicsReference = new HashSet<Topic>();
    }


    /**
     * Method to test whether a Dataset has any associated geophysical properties.
     */
    public boolean isGeophysicalDataset() {

        return !this.isSoftwareDataset();
    }

    // FIXME: This method seem inappropriate, why whould dataset need to call
    // the create method on the softwarPropertiesFactory?
    // There should be a method called setSoftwareProperties(...) that can only
    // be called once or something like that.
    // There is a method similar to this called initalizeDescriptiveMetadata.
    // Maybe we should consider naming it more like that method.
    public SoftwareProperties createSoftwareProperties(SoftwarePropertiesFactory softwarePropertiesFactory) {

        if (this.getSoftwareProperties() == null) {

            this.softwareProperties = softwarePropertiesFactory.create(this);
        }

        return this.softwareProperties;
    }

    public SoftwareProperties getSoftwareProperties() {

        return this.softwareProperties;
    }

    /**
     * Method to test whether a Dataset has any associated software properties.
     */
    public boolean isSoftwareDataset() {

        return this.getSoftwareProperties() != null;
    }

    public boolean isVisualizable() {

        return false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DataCenter getDataCenter() {
        return dataCenter;
    }

    public void setDataCenter(DataCenter dataCenter) {
        this.dataCenter = dataCenter;
    }

    public String getDOI() {
        return this.doi;
    }

    public void setDOI(String doi) {
        this.doi = doi;
    }

    public ContainerType getContainerType() {
        return this.containerType;
    }

    public void setContainerType(ContainerType type) {
        this.containerType = type;
    }

    public License getLicense() {
        return this.license;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    @Override
    public String getProjectGroup() {
        return projectGroup;
    }

    public void setProjectGroup(String projectGroup) {
        this.projectGroup = projectGroup;
    }

    //
    // SECURITY METHODS
    // SECURITY METHODS
    // SECURITY METHODS
    //

    /**
     * Method to return the Principals that are allowed to perform an Operation on the given Dataset. Note that this method is recursive.
     *
     * @param operation the operation
     * @return the principals for operation
     */
    @Override
    public Set<Principal> getPrincipalsForOperation(Operation operation) {

        Set<Principal> principals = new HashSet<Principal>();

        for (Permission permission : this.permissions) {
            if (permission.getOperation() == operation) {
                principals.add(permission.getPrincipal());
            }
        }

        if (principals.isEmpty() && (this.getParentDataset() != null)) {
            principals = this.getParentDataset().getPrincipalsForOperation(operation);
        }

        return principals;
    }

    public Set<Principal> getPrincipalsForRead() {
        return this.getPrincipalsForOperation(Operation.READ);
    }

    public void recursivelyAddPermissionToAllDatasets(Principal principal, Operation operation) {
        this.addPermission(principal, operation);
        for (Dataset nested : this.getDirectlyNestedDatasets()) {
            nested.recursivelyAddPermissionToAllDatasets(principal, operation);
        }
    }

    public boolean isReadRestricted() {

        boolean restricted = true;

        // retrieve the set of Principals that are allowed to execute the given Operation on this Dataset

        Set<Principal> principals = this.getPrincipalsForOperation(Operation.READ);

        // check for Guest authorization

        if (principals.size() == 0) {

            // no permissions - does this mean "free"
            // TODO: currently no principals means "no access"
            // restricted = false;
        }
        for (Principal p : principals) {

            if (p.getName().equals(Group.GROUP_GUEST)) {

                restricted = false;

                break;
            }
        }

        return restricted;
    }

    /**
     * Method to reset the {@link Principal}s that can perform the given {@link Operation} on this {@link DatasetImpl}. This method will remove any previous
     * {@link Principal} that is not contained in the given set.
     *
     * @param principals
     * @param operation
     */
    public void setPrincipalsForOperation(Set<Principal> principals, Operation operation) {

        // remove obsolete permissions
        Set<Permission> obsolete = new HashSet<Permission>();
        for (Permission permission : this.getPermissions()) {
            if (permission.getOperation().equals(operation) && !principals.contains(permission.getPrincipal())) {
                obsolete.add(permission);
            }
        }
        this.getPermissions().removeAll(obsolete);

        // add new permissions
        for (Principal principal : principals) {
            this.addPermission(principal, operation);
        }
    }

    @Override
    public int getLogicalFileCount() {
        return getCurrentDatasetVersion().getLogicalFiles().size();
    }

    @Override
    public boolean isDownloadable() {

        boolean hasLogicalFiles = getLogicalFileCount() > 0;
        boolean hasDistributionURI = false;

        URI distributionURI = this.descriptiveMetadata.getDistributionURI();

        if (distributionURI != null) {
            hasDistributionURI = !StringUtils.isBlank(distributionURI.toString());
        }
        return (hasLogicalFiles || hasDistributionURI);
    }

    public Set<Permission> getPermissions() {
        return this.permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public void addPermission(Principal principal, Operation operation) {

        boolean foundPermission = false;

        for (Permission permission : this.permissions) {

            if (permission.getPrincipal().equals(principal) && permission.getOperation().equals(operation)) {
                foundPermission = true;
            }
        }

        if (!foundPermission) {
            this.permissions.add(new Permission(this, principal, operation));
        }
    }

    /**
     * Method to return the Groups that are allowed to perform an Operation on the given Resource. Note that this method may is recursive IF the implementation
     * of the getPrincipalsForOperation() for the current object is recursive.
     *
     * @param operation the operation
     * @return the principals for operation
     */
    public Set<Group> getGroupsForOperation(Operation operation) {

        Set<Group> groups = new HashSet<Group>();

        // Note: when applied to Dataset object, principals are looked up the parent hierarchy
        // (note the 'this.' invocation).
        Set<Principal> principals = this.getPrincipalsForOperation(operation);

        for (Principal principal : principals) {
            if (principal instanceof Group) {
                groups.add((Group) principal);
            }
        }

        return groups;
    }

    public Set<Group> getReadGroups() {
        return getGroupsForOperation(Operation.READ);
    }

    public Set<Group> getWriteGroups() {
        return getGroupsForOperation(Operation.WRITE);
    }

    public String getReadGrantingAuthorities() {
        return AcegiUtils.getGrantingAuthorities(this, Operation.READ);
    }

    public String getWriteGrantingAuthorities() {
        String grantees = AcegiUtils.getGrantingAuthorities(this, Operation.WRITE);
        return grantees;
    }

    public Collection<Award> getAwards() {

        return UnmodifiableCollection.decorate(awards);
    }

    public void addAward(String awardNumber) {

        this.awards.add(new Award(awardNumber));
    }

    public Award getAward(String awardNumber) {

        Award award = null;

        for (Award selectedAward : this.awards) {

            if (selectedAward.getAwardNumber().equalsIgnoreCase(awardNumber)) {

                award = selectedAward;
                break;
            }
        }

        return award;
    }

    public void removeAward(Award award) {

        this.awards.remove(award);
    }

    @Override
    public Dataset getPredecessorProject() {
        return predecessorProject;
    }

    @Override
    public void setPredecessorProject(Dataset predecessorProject) {
        this.predecessorProject = predecessorProject;
    }

    @Override
    public Dataset getSuccessorProject() {
        return successorProject;
    }

    @Override
    public void setSuccessorProject(Dataset successorProject) {
        this.successorProject = successorProject;
    }

    @Override
    public Boolean isContainerType(ContainerType containerType) {
        return this.containerType.equals(containerType);
    }
}
