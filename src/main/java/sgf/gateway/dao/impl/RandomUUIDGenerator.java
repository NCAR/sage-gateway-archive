package sgf.gateway.dao.impl;

import sgf.gateway.dao.IdentifierGenerator;

import java.io.Serializable;

public class RandomUUIDGenerator implements IdentifierGenerator {

    public Serializable generateNewIdentifier() {
        return org.safehaus.uuid.UUIDGenerator.getInstance().generateRandomBasedUUID();
    }

}
