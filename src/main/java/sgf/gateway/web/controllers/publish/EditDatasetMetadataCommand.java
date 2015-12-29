package sgf.gateway.web.controllers.publish;

import sgf.gateway.model.metadata.PublishedState;
import sgf.gateway.model.metadata.activities.project.Project;
import sgf.gateway.model.security.Group;

import java.util.List;
import java.util.Set;

/**
 * Command bean for editing the metadata of an existing dataset.
 */
public class EditDatasetMetadataCommand {

    //private DatasetImpl dataset;
    /**
     * UUID as a String
     */
    private String identifier;

    private String shortName;

    /**
     * Ordered set of all access control groups in the system.
     */
    private Set<Group> allGroups;

    /**
     * Access control groups authorized for READ operation.
     */
    private String[] readGroups = new String[]{Group.GROUP_NOBODY};

    /**
     * Access control groups authorized for WRITE operation.
     */
    private String[] writeGroups = new String[]{Group.GROUP_NOBODY};

    /**
     * List of all possible Projects to be associated with the Dataset.
     */
    private List<Project> allProjects;

    /**
     * Specific project to be associated with the Dataset.
     */
    private String projectId = "";

    private String title;
    private String description;
    private String doi;

    private PublishedState datasetState;

    public String getIdentifier() {

        return this.identifier;
    }

    public void setIdentifier(String identifier) {

        this.identifier = identifier;
    }

    public String getShortName() {

        return this.shortName;
    }

    public void setShortName(String shortNameIdentifier) {

        this.shortName = shortNameIdentifier;
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

    public String getDoi() {

        return doi;
    }

    public void setDoi(String doi) {

        this.doi = doi;
    }

    public String[] getReadGroups() {
        return readGroups;
    }

    public void setReadGroups(String[] readGroups) {
        this.readGroups = readGroups;
    }

    public String[] getWriteGroups() {
        return writeGroups;
    }

    public void setWriteGroups(String[] writeGroups) {
        this.writeGroups = writeGroups;
    }

    public Set<Group> getAllGroups() {
        return allGroups;
    }

    public void setAllGroups(Set<Group> allGroups) {
        this.allGroups = allGroups;
    }

    public List<Project> getAllProjects() {
        return allProjects;
    }

    public void setAllProjects(List<Project> allProjects) {
        this.allProjects = allProjects;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public PublishedState getDatasetState() {

        return this.datasetState;
    }

    public void setDatasetState(PublishedState datasetState) {

        this.datasetState = datasetState;
    }

}
