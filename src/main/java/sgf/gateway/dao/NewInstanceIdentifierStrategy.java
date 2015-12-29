package sgf.gateway.dao;

import sgf.gateway.model.Identifier;

public interface NewInstanceIdentifierStrategy {

    Identifier generateNewIdentifier(final Class objectClass);

}
