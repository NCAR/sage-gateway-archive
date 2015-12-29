package sgf.gateway.model;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.safehaus.uuid.UUID;

import java.io.Serializable;

import static org.junit.Assert.assertThat;

public class AbstractPersistentObjectTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testEquals() {

        UUID id = org.safehaus.uuid.UUIDGenerator.getInstance().generateRandomBasedUUID();

        AbtractPersistableEntityWrapper a = new AbtractPersistableEntityWrapper(id, new Integer(0));

        AbtractPersistableEntityWrapper b = new AbtractPersistableEntityWrapper(id, new Integer(0));

        assertThat(a.equals(b), Is.is(true));
    }

    @Test
    public void testHashCode() {

        UUID id = org.safehaus.uuid.UUIDGenerator.getInstance().generateRandomBasedUUID();

        AbtractPersistableEntityWrapper a = new AbtractPersistableEntityWrapper(id, new Integer(0));

        AbtractPersistableEntityWrapper b = new AbtractPersistableEntityWrapper(id, new Integer(0));

        assertThat(a.hashCode(), Is.is(b.hashCode()));
    }

    private class AbtractPersistableEntityWrapper extends AbstractPersistableEntity {

        private AbtractPersistableEntityWrapper(Serializable id, Serializable version) {

            super(id, version);
        }
    }
}
