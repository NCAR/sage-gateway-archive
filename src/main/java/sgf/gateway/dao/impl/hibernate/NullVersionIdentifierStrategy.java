package sgf.gateway.dao.impl.hibernate;

import org.safehaus.uuid.UUID;
import sgf.gateway.dao.IdentifierGenerator;
import sgf.gateway.dao.NewInstanceIdentifierStrategy;
import sgf.gateway.model.Identifier;

public class NullVersionIdentifierStrategy implements NewInstanceIdentifierStrategy {

    public static final Long EXPECTED_FIRST_VERSION_VALUE = 0L;

    private IdentifierGenerator identifierGenerator;

    public NullVersionIdentifierStrategy(IdentifierGenerator identifierGenerator) {
        super();
        this.identifierGenerator = identifierGenerator;
    }

    public Identifier generateNewIdentifier(Class objectClass) {

        UUID entityIdentifier = (UUID) this.identifierGenerator.generateNewIdentifier();

        // New instances should have a null version field to inidicate unsaved-values.
        Integer version = null;

        VersionedUUIDIdentifier result = new VersionedUUIDIdentifier(entityIdentifier, version);

        return result;
    }
}
