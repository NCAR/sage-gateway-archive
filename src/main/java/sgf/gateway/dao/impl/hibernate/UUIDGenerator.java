package sgf.gateway.dao.impl.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import sgf.gateway.dao.impl.TimeBasedUUIDGenerator;

import java.io.Serializable;

public class UUIDGenerator implements IdentifierGenerator {

    private TimeBasedUUIDGenerator timeBasedUUIDGenerator;

    public UUIDGenerator() {
        super();

        this.timeBasedUUIDGenerator = new TimeBasedUUIDGenerator();
    }


    @Override
    public Serializable generate(SessionImplementor session, Object object) throws HibernateException {

        return timeBasedUUIDGenerator.generateNewIdentifier();
    }

}
