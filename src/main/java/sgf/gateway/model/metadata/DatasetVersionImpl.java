package sgf.gateway.model.metadata;

import org.apache.commons.collections.collection.UnmodifiableCollection;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.safehaus.uuid.UUID;
import sgf.gateway.audit.Auditable;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.metadata.inventory.Variable;
import sgf.gateway.model.security.User;

import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Audited
public class DatasetVersionImpl implements DatasetVersion, Auditable {

    private UUID identifier;

    @NotAudited
    private Integer version;

    private String versionIdentifier;

    @NotAudited
    private Dataset dataset;

    @NotAudited
    private Date dateCreated;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private User publisher;

    private String comment;

    private String label;

    private URI authoritativeSourceURI;

    private Date authoritativeSourceDateCreated;

    private Date authoritativeSourceDateModified;

    private Set<LogicalFile> logicalFilesReference;

    @NotAudited
    private Integer logicalFileCount;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Set<Variable> variablesReference;

    @NotAudited
    private DatasetVersionState datasetVersionState;

    /**
     * Instantiates a new dataset version impl.
     * <p/>
     * For use by Hibernate only.
     */
    DatasetVersionImpl() {

    }

    public DatasetVersionImpl(UUID identifier, Integer version, String versionIdentifier, Dataset dataset, User publisher, String comment, String label) {

        this.identifier = identifier;
        this.version = version;
        this.versionIdentifier = versionIdentifier;
        this.dataset = dataset;
        this.publisher = publisher;
        this.comment = comment;
        this.label = label;

        this.logicalFilesReference = new HashSet<LogicalFile>();
        this.variablesReference = new HashSet<Variable>();
        this.datasetVersionState = new DatasetVersionStatePrepublishedImpl(this);
    }

    public UUID getIdentifier() {

        return this.identifier;
    }

    public String getVersionIdentifier() {

        return this.versionIdentifier;
    }

    public Dataset getDataset() {
        return this.dataset;
    }

    /*
     * (non-Javadoc)
     *
     * @see sgf.gateway.model.metadata.DatasetVersion#getComment()
     */
    public String getComment() {
        return this.comment;
    }

    /*
     * (non-Javadoc)
     *
     * @see sgf.gateway.model.metadata.DatasetVersion#getDateCreated()
     */
    public Date getDateCreated() {
        return this.dateCreated;
    }

    // FIXME this should be a calculated value much like getLogicalFileCount below.
    public long getLogicalFileSize() {

        long totalFileSize = 0L;

        Collection<LogicalFile> logicalFiles = this.getLogicalFiles();

        for (LogicalFile logicalFile : logicalFiles) {

            totalFileSize += logicalFile.getSize();
        }

        return totalFileSize;
    }

    /**
     * {@inheritDoc}
     * <p/>
     * This field may be updated by the persistence tool as an optimization. The value may be calculated rather than loading the whole (possibly very big)
     * collection.
     * <p/>
     * If the internal state is null, we should return the collection size. If it's not null we should return the state value.
     */
    public Integer getLogicalFileCount() {

        Integer result;

        if (null == this.logicalFileCount) {

            result = this.getLogicalFilesReference().size();

        } else {

            result = this.logicalFileCount;
        }

        return result;
    }

    /**
     * Gets the logical files reference.
     *
     * @return the logical files reference
     */
    Set<LogicalFile> getLogicalFilesReference() {
        return logicalFilesReference;
    }

    /*
     * (non-Javadoc)
     *
     * @see sgf.gateway.model.metadata.DatasetVersion#getLogicalFiles()
     */
    public Collection<LogicalFile> getLogicalFiles() {

        return UnmodifiableCollection.decorate(this.getLogicalFilesReference());
    }

    /*
     * (non-Javadoc)
     *
     * @see sgf.gateway.model.metadata.DatasetVersion#addLogicalFile(sgf.gateway.model.metadata.inventory.LogicalFile)
     */
    public void addLogicalFile(LogicalFile logicalFile) {

        this.datasetVersionState.addLogicalFile(logicalFile);

        this.logicalFileCount = this.getLogicalFilesReference().size();
    }

    /*
     * (non-Javadoc)
     *
     * @see sgf.gateway.model.metadata.DatasetVersion#addVariable(sgf.gateway.model.metadata.inventory.Variable)
     */
    public void removeLogicalFile(LogicalFile logicalFile) {

        this.datasetVersionState.removeLogicalFile(logicalFile);
    }

    public void removeAllLogicalFiles() {

        this.logicalFilesReference.clear();
    }

    public LogicalFile getLogicalFileByFileName(String fileName) {

        LogicalFile logicalFile = null;

        Collection<LogicalFile> logicalFiles = this.getLogicalFiles();

        for (LogicalFile file : logicalFiles) {

            if (file.getName().equals(fileName)) {
                logicalFile = file;
                break;
            }
        }

        return logicalFile;
    }

    /*
     * (non-Javadoc)
     *
     * @see sgf.gateway.model.metadata.DatasetVersion#getPublisher()
     */
    public User getPublisher() {

        return this.publisher;
    }

    // public Variables getVariables() {
    // return this.variables;
    // }

    /**
     * Gets the variables reference.
     *
     * @return the variables reference
     */
    Set<Variable> getVariablesReference() {

        return this.variablesReference;
    }

    /*
     * (non-Javadoc)
     *
     * @see sgf.gateway.model.metadata.DatasetVersion#getVariables()
     */
    public Collection<Variable> getVariables() {

        return UnmodifiableCollection.decorate(this.getVariablesReference());
    }

    /*
     * (non-Javadoc)
     *
     * @see sgf.gateway.model.metadata.DatasetVersion#addVariable(sgf.gateway.model.metadata.inventory.Variable)
     */
    public void addVariable(Variable variable) {

        this.datasetVersionState.addVariable(variable);
    }

    public void removeVariable(Variable variable) {

        this.datasetVersionState.removeVariable(variable);
    }


    /**
     * Find variable with the given name
     *
     * @param name the name of the variable
     * @return the variable, null if not found.
     */
    public Variable findVariable(String name) {

        return findLocalVariable(name);
    }

    /**
     * Find the variable by name from the local variables list.
     *
     * @param name the variable name to find.
     * @return the variable if found, null if not found.
     */
    protected Variable findLocalVariable(final String name) {

        Variable result = null;

        for (Variable variable : this.getVariablesReference()) {

            if (variable.getName().equalsIgnoreCase(name)) {

                result = variable;

                break;
            }
        }

        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see sgf.gateway.model.metadata.DatasetVersion#getLabel()
     */
    public String getLabel() {

        return this.label;
    }

    public URI getAuthoritativeSourceURI() {

        return this.authoritativeSourceURI;
    }

    public void setAuthoritativeSourceURI(URI authoritativeSourceURI) {

        this.authoritativeSourceURI = authoritativeSourceURI;
    }

    public Date getAuthoritativeSourceDateCreated() {
        return authoritativeSourceDateCreated;
    }

    public void setAuthoritativeSourceDateCreated(Date authoritativeSourceDateCreated) {
        this.authoritativeSourceDateCreated = authoritativeSourceDateCreated;
    }

    public Date getAuthoritativeSourceDateModified() {
        return authoritativeSourceDateModified;
    }

    public void setAuthoritativeSourceDateModified(Date authoritativeSourceDateModified) {
        this.authoritativeSourceDateModified = authoritativeSourceDateModified;
    }

    /**
     * {@inheritDoc}
     * <p/>
     * TODO: Change method name so it follows javabean standards.
     */
    public boolean hasLogicalFiles() {

        return (0 != getLogicalFileCount());
    }

    /**
     * NOT to be called directly. Only to be called by DatasetVersionState.
     * <p/>
     * Sets the dataset version state.
     *
     * @param datasetVersionState the new dataset version state
     */
    void setDatasetVersionState(DatasetVersionState datasetVersionState) {

        this.datasetVersionState = datasetVersionState;
    }

    // TODO review this code and see if it is proper or not.
    // TODO State should not be changed by this method. However, we cannot do just a null check because hibernate will re-set this method during a save. Its all
    // very complicated.

    /**
     * NOT to be called directly. Only to be called by hibernate.
     * <p/>
     * Sets the published state.
     *
     * @param publishedState the new published state
     */
    void setPublishedState(PublishedState publishedState) {

        if (PublishedState.PRE_PUBLISHED == publishedState) {

            this.datasetVersionState = new DatasetVersionStatePrepublishedImpl(this);

        } else if (PublishedState.PUBLISHED == publishedState) {

            this.datasetVersionState = new DatasetVersionStatePublishedImpl(this);

        } else if (PublishedState.RETRACTED == publishedState) {

            this.datasetVersionState = new DatasetVersionStateRetractedImpl(this);

        } else if (PublishedState.DELETED == publishedState) {

            this.datasetVersionState = new DatasetVersionStateDeletedImpl(this);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see sgf.gateway.model.metadata.DatasetVersion#getPublishedState()
     */
    public PublishedState getPublishedState() {

        return this.datasetVersionState.getPublishedState();
    }

    /*
     * (non-Javadoc)
     *
     * @see sgf.gateway.model.metadata.DatasetVersion#isPrepublished()
     */
    public boolean isPrepublished() {

        return this.datasetVersionState.isPrepublished();
    }

    /*
     * (non-Javadoc)
     *
     * @see sgf.gateway.model.metadata.DatasetVersion#isPublished()
     */
    public boolean isPublished() {

        return this.datasetVersionState.isPublished();
    }

    // TODO review if this method should take a principle or something to determine if the person has privileges to perform this action.
    /*
	 * (non-Javadoc)
	 * 
	 * @see sgf.gateway.model.metadata.DatasetVersion#publish()
	 */
    public void publish() {

        this.datasetVersionState.changeState(PublishedState.PUBLISHED);
    }

    /*
     * (non-Javadoc)
     *
     * @see sgf.gateway.model.metadata.DatasetVersion#isRetracted()
     */
    public boolean isRetracted() {

        return this.datasetVersionState.isRetracted();
    }

    // TODO review if this method should take a principle or something to determine if the person has privileges to perform this action.
	/*
	 * (non-Javadoc)
	 * 
	 * @see sgf.gateway.model.metadata.DatasetVersion#retract()
	 */
    public void retract() {

        this.datasetVersionState.changeState(PublishedState.RETRACTED);
    }

    /*
     * (non-Javadoc)
     *
     * @see sgf.gateway.model.metadata.DatasetVersion#isDeleted()
     */
    public boolean isDeleted() {

        return this.datasetVersionState.isDeleted();
    }

    // TODO review if this method should take a principle or something to determine if the person has privileges to perform this action.
	/*
	 * (non-Javadoc)
	 * 
	 * @see sgf.gateway.model.metadata.DatasetVersion#delete()
	 */
    public void delete() {
        this.dataset.delete();
        this.datasetVersionState.changeState(PublishedState.DELETED);
    }

}
