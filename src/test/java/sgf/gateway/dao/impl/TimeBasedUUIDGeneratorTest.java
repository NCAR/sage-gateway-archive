package sgf.gateway.dao.impl;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.safehaus.uuid.UUID;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

public class TimeBasedUUIDGeneratorTest {

    private static final int IDENTIFIERS_TO_COMPARE = 10;

    private TimeBasedUUIDGenerator timeBasedUUIDGenerator;

    @Before
    public void setUp() throws Exception {

        this.timeBasedUUIDGenerator = new TimeBasedUUIDGenerator();
    }

    @Test
    public void testGenerateNewIdentifier() {

        List<UUID> ids = new ArrayList<>(IDENTIFIERS_TO_COMPARE);

        for (int idIndex = 0; idIndex < IDENTIFIERS_TO_COMPARE; idIndex++) {
            ids.add((UUID) this.timeBasedUUIDGenerator.generateNewIdentifier());
        }

        UUID previous = ids.get(0);

        for (int idIndex = 1; idIndex < IDENTIFIERS_TO_COMPARE; idIndex++) {
            UUID current = ids.get(idIndex);

            assertThat(current.compareTo(previous), Is.is(greaterThan(0)));

            previous = current;
        }

    }

}
