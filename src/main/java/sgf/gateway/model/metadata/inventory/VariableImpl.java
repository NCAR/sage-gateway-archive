package sgf.gateway.model.metadata.inventory;

import org.apache.commons.collections.set.UnmodifiableSet;
import org.safehaus.uuid.UUID;
import org.springframework.util.Assert;
import sgf.gateway.model.AbstractPersistableIdentifiableChild;
import sgf.gateway.model.metadata.DatasetImpl;
import sgf.gateway.model.metadata.DatasetVersion;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Default implementation of {@link Variable}. <br>
 * <br>
 * Note: This class has the semantics of a child object of {@link GeophysicalProperties}, it's life cycle is defined by it's parent. However since we don't have
 * the possibility of a full unique constraint across the object it does have it's own identifier. This class should not be saved directly, ultimately only the
 * {@link DatasetImpl} should be saved to ensure proper versioning and updates.
 */
public class VariableImpl extends AbstractPersistableIdentifiableChild implements Variable {

    /**
     * The name.
     */
    private String name;

    /**
     * The description.
     */
    private String description;

    /**
     * The units.
     */
    private Unit units;

    /**
     * The standard names.
     */
    private Set<StandardName> standardNamesReference = new HashSet<>();

    /**
     * Default constructor, instantiation of objects from the persistence mechanism.
     */
    protected VariableImpl() {

        super();
    }

    /**
     * General constructor for creating new instances.
     *
     * @param identifier    the identifier
     * @param name          the name
     * @param standardNames the standard names
     */
    public VariableImpl(Serializable identifier, DatasetVersion datasetVersion, String name, Set<StandardName> standardNames) {

        super(identifier, datasetVersion);

        this.name = name;

        if (null != standardNames) {
            this.getStandardNamesReference().addAll(standardNames);
        }
    }

    /**
     * {@inheritDoc}
     */
    public String getName() {

        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    public void setName(String name) {

        Assert.hasText(name, "Variable name must have a non-whitespace value");

        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {

        return this.description;
    }

    /**
     * {@inheritDoc}
     */
    public void setDescription(String description) {

        this.description = description;
    }

    /**
     * {@inheritDoc}
     */
    public Unit getUnits() {

        return this.units;
    }

    /**
     * {@inheritDoc}
     */
    public void setUnits(Unit units) {

        this.units = units;
    }

    /**
     * Getter for the StandardNames member variable.
     * <p/>
     * Note: Should be called rather than direct member access for proxy compatibility.
     *
     * @return The member variable reference.
     */
    protected final Set<StandardName> getStandardNamesReference() {
        return this.standardNamesReference;
    }

    /**
     * {@inheritDoc}
     */
    public Set<StandardName> getStandardNames() {

        return UnmodifiableSet.decorate(this.getStandardNamesReference());
    }

    /**
     * {@inheritDoc}
     */
    public void addStandardName(StandardName standardName) {

        this.getStandardNamesReference().add(standardName);
    }

    @Override
    public UUID getIdentifier() {

        return (UUID) this.getInternalIdentifier();
    }
}
