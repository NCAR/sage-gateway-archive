package sgf.gateway.dao;

import java.io.Serializable;

public interface IdentifierGenerator {

    Serializable generateNewIdentifier();
}
