package sgf.gateway.model.metadata;

import sgf.gateway.model.PersistableEntity;
import sgf.gateway.model.metadata.activities.Activity;
import sgf.gateway.model.metadata.activities.ActivityType;
import sgf.gateway.model.metadata.activities.project.Award;
import sgf.gateway.model.metadata.descriptive.DescriptiveMetadata;
import sgf.gateway.model.metadata.descriptive.Institution;
import sgf.gateway.model.metadata.factory.SoftwarePropertiesFactory;
import sgf.gateway.model.security.Operation;
import sgf.gateway.model.security.Permission;
import sgf.gateway.model.security.Principal;

import java.util.*;

public interface Dataset extends PersistableEntity {

    void delete();

    /**
     * Returns the short name for a dataset.  This value is set by a harvester when the dataset is ingested into the gateway.
     *
     * @return - The short name, this value should never be null.
     */
    String getShortName();

    void setShortName(String shortName);

    String getTitle();

    void setTitle(String title);

    /**
     * Returns the direct parent of the dataset, may be any dataset container type.
     *
     * @return - The direct parent of the dataset, should never be null
     */
    Dataset getParent();

    boolean isTopLevelDataset();

    /**
     * Returns the top of the dataset hierarchy (project container)
     */
    public Dataset getRootParentDataset();

    /**
     * Basic bean-spec compliant inspection method to determine if the dataset is in the retracted state.
     *
     * @return if the dataset is retracted.
     */
    boolean isRetracted();

    /**
     * Basic bean-spec compliant inspection method to determine if the dataset is in the published state.
     *
     * @return if the dataset is published.
     */
    boolean isPublished();

    boolean isDeleted();

    boolean isMetadataOnly();

    Activity getActivity(String activityTypeName);

    boolean isBrokered();

    public String getAuthoritativeIdentifier();

    public void setAuthoritativeIdentifier(String authoritativeIdentifier);

    /**
     * Returns the raw state value.
     *
     * @return -
     */
    PublishedState getPublishedState();

    Collection<DatasetVersion> getDatasetVersions();

    DatasetVersion getCurrentDatasetVersion();

    void addDatasetVersion(DatasetVersion datasetVersion);

    DatasetVersion getDatasetVersion(String versionIdentifier);

    /**
     * Returns ordered list of dataset parent hierarchy
     */
    List<Dataset> getParentList();

    Institution getInstitution();

    void setInstitution(Institution newInstitution);

    void orderChildDatasets(List<Dataset> childDatasets);

    /**************************************************************************
     * Place all used methods above this comment. The idea is to only move or create methods we actually use above this comment so we can weed out unused
     * methods.
     *
     * Also methods should describe the objects behavior, not just state mutators!
     **************************************************************************
     */

    /**
     * Factory method to create a new {@link SoftwareProperties} instance associated with the current {@link DatasetImpl} instance.
     *
     * @param softwarePropertiesFactory
     * @return
     */
    SoftwareProperties createSoftwareProperties(SoftwarePropertiesFactory softwarePropertiesFactory);

    /**
     * Method to return the {@link SoftwareProperties} associated with the current {@link DatasetImpl}, if any.
     *
     * @return
     */
    SoftwareProperties getSoftwareProperties();

    /**
     * Method to check whether this {@link Dataset} has software character.
     *
     * @return
     */
    boolean isSoftwareDataset();

    /**
     * Indicates if this dataset has GeophysicalProperties
     *
     * @return
     */
    boolean isGeophysicalDataset();

    boolean isVisualizable();

    // *****************************************************//

	/*
     * Need to figure out which of these methods we still need....
	 * 
	 * Are there any activity related methods not in the interface? Which ones are still used? Are we missing some, something works but is akward?
	 */

    Map<ActivityType, Activity> getActivities();

    /**
     * Method to return the activities directly associated with this dataset. Note that this method will NOT return any inherited activities.
     */
    Collection<Activity> getActivityList();

    /**
     * Gets the all the discrete activities, including inherited activities.
     * <p/>
     * Discrete values are based on ID not type, allows for nesting of types.
     *
     * @return the all activities
     */
    Collection<Activity> getAllActivities();

    /**
     * Gets the checks for associated activities, include both direct and inherited associations.
     *
     * @return the checks for associated activities
     */
    boolean getHasAssociatedActivities();

    /**
     * Associate an activity directly to the current dataset. Note: if an activity of the same type is already present this will overwrite the existing value.
     */
    void associateActivity(Activity activity);

    /**
     * Method to remove an activity directly associated with the current dataset (i.e. no hierarchy is traversed).
     */
    void removeActivity(Activity activity);

    void removeAllActivities();

    /**
     * Method to return the Dataset associated Activity of a given type. NOTE: this method will traverse the Dataset hierarchy bottom to top and return the
     * first Activity of the requested type that is found.
     *
     * @param activityType the activity type
     * @return the activity
     */
    Activity getActivity(ActivityType activityType);

    /**
     * Gets the data format.
     *
     * @return the data format
     */
    Collection<DataFormat> getDataFormats();

    /**
     * Adds the data format
     *
     * @param newDataFormat the new data format.
     */
    void addDataFormat(DataFormat newDataFormat);

    void mergeDataFormats(HashSet<DataFormat> newDataFormats);

    /**
     * Removes the data format
     *
     * @param dataFormatToRemove the data format to remove.
     */
    void removeDataFormat(DataFormat dataFormatToRemove);

    /**
     * Clears the data formats collection.
     */
    void removeAllDataFormats();

    Collection<Topic> getTopics();

    Collection<Topic> getIsoTopics();

    void mergeTopics(Taxonomy taxonomy, HashSet<Topic> newTopics);

    void removeAllTopics();

    /**
     * Method to return all Topics associated with this dataset, including topics that are logically inherited from higher up in the dataset hierachy.
     *
     * @return the all topics
     */
    Collection<Topic> getInheritedTopics();

    void addTopic(Topic newTopic);

    void removeTopic(Topic topicToRemove);

    DescriptiveMetadata getDescriptiveMetadata();

    /**
     * Method that looks up the parent dataset's principals, if none are defined for this file.
     */
    Set<Principal> getPrincipalsForOperation(Operation operation);

    License getLicense();

    void setLicense(License license);

    /**
     * Method to recursively add a permission to this dataset and all it's recursively nested datasets.
     *
     * @param principal the principal
     * @param operation the operation
     */
    void recursivelyAddPermissionToAllDatasets(Principal principal, Operation operation);

    /**
     * Associate a new child dataset to the container.
     * <p/>
     * The implementor of this is responsible for maintaining the bi-directional relationship properties.
     * <p/>
     * Once associated the life span of the child may be controlled by that of the parent. If the parent is deleted, the child should be deleted as well. A
     * child may be deleted outside the life span of the parent. It may also be moved to a different parent.
     *
     * @param dataset - The child dataset to associate.
     * @throws - {@link IllegalStateException} If the dataset is already associated with the container.
     */
    void addChildDataset(Dataset dataset);

    void removeChildDataset(Dataset dataset);

    Integer getIndexOf(Dataset nestedDataset);

    Collection<Dataset> getDirectlyNestedDatasets();

    Long getNestedDatasetCount();

    MetadataProfile getMetadataProfile();

    Date getDateUpdated();

    Date getDateCreated();

    DataCenter getDataCenter();

    void setDataCenter(DataCenter dataCenter);

    String getDOI();

    void setDOI(String doi);

    ContainerType getContainerType();

    String getDescription();

    void setDescription(String description);

    /**
     * Determines if this resource requires authorization for read access
     *
     * @return true if this resource requires authorization for read and "is not free"
     */
    boolean isReadRestricted();

    void addPermission(Principal principal, Operation operation);

    Set<Permission> getPermissions();

    void setPrincipalsForOperation(Set<Principal> principals, Operation operation);

    boolean isDownloadable();

    int getLogicalFileCount();

    Collection<Award> getAwards();

    Award getAward(String awardNumber);

    void addAward(String awardNumber);

    void removeAward(Award award);

    String getProjectGroup();

    void setProjectGroup(String projectGroup);

    Dataset getPredecessorProject();

    void setPredecessorProject(Dataset predecessorProject);

    Dataset getSuccessorProject();

    void setSuccessorProject(Dataset successorProject);

    Boolean isContainerType(ContainerType containerType);
}
