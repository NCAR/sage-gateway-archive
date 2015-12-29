package sgf.gateway.model.metadata.activities.modeling;

import org.junit.Test;
import sgf.gateway.model.ModelEqualsMethodTest;

public class EnsembleImplEqualsTest extends ModelEqualsMethodTest {

    public Object createObjectUnderTest() {

        return new EnsembleImpl();
    }

    @Test
    public void testNames() {

        testEqualContractForAttribute("name", "thisName", "otherName");
    }

    @Test
    public void testDescription() {

        testEqualContractForAttribute("description", "thisDescription", "thatDescription");
    }
}
