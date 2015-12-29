package sgf.gateway.dao.impl;

import org.safehaus.uuid.UUID;
import org.safehaus.uuid.UUIDGenerator;
import sgf.gateway.dao.IdentifierGenerator;

import java.io.Serializable;

public class TimeBasedUUIDGenerator implements IdentifierGenerator {

    public Serializable generateNewIdentifier() {

        UUID identifier = UUIDGenerator.getInstance().generateTimeBasedUUID();

        // Not completely sure of the clock resolution, sleep for 1msec
        // as that should cover the resolution gap.
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // FIXME - Force this into the Java Type until we can get it cleaned up everywhere.
        return identifier;
    }

}
