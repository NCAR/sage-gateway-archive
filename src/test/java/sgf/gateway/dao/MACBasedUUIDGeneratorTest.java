package sgf.gateway.dao;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.safehaus.uuid.UUID;
import sgf.gateway.dao.impl.MACBasedUUIDGenerator;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertThat;

public class MACBasedUUIDGeneratorTest {

    private static final int IDS_UNIQUE_TEST_COUNT = 20;

    private MACBasedUUIDGenerator macBasedUUIDGenerator;

    @Before
    public void setUp() throws Exception {
        this.macBasedUUIDGenerator = new MACBasedUUIDGenerator("00:C0:F0:3D:5B:7C");
    }

    @Test
    public void testGenerateNewIdentifiersAreUnique() {

        Set<UUID> newIds = new HashSet<UUID>(IDS_UNIQUE_TEST_COUNT);

        for (int idIndex = 0; idIndex < IDS_UNIQUE_TEST_COUNT; idIndex++) {

            newIds.add((UUID) this.macBasedUUIDGenerator.generateNewIdentifier());

        }

        assertThat(newIds.size(), Is.is(IDS_UNIQUE_TEST_COUNT));

    }

}
