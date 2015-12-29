package sgf.gateway.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.safehaus.uuid.EthernetAddress;
import org.safehaus.uuid.UUID;
import org.safehaus.uuid.UUIDGenerator;
import sgf.gateway.dao.IdentifierGenerator;

import java.io.Serializable;

public class MACBasedUUIDGenerator implements IdentifierGenerator {

    private static final Log LOG = LogFactory.getLog(MACBasedUUIDGenerator.class);

    private EthernetAddress ethernetAddress;

    public MACBasedUUIDGenerator(final String ethernetAddress) {
        super();
        this.ethernetAddress = new EthernetAddress(ethernetAddress);
    }

    public Serializable generateNewIdentifier() {

        UUID identifier = UUIDGenerator.getInstance().generateTimeBasedUUID(this.ethernetAddress);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Generated new identifier for MAC: " + this.ethernetAddress.toString() + " value: " + identifier);
        }

        // FIXME - Force this into the Java Type until we can get it cleaned up everywhere.
        return identifier;
    }

}
