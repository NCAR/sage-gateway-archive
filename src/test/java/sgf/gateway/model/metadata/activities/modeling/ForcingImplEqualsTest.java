package sgf.gateway.model.metadata.activities.modeling;

import org.junit.Test;
import sgf.gateway.model.ModelEqualsMethodTest;

public class ForcingImplEqualsTest extends ModelEqualsMethodTest {

    public Object createObjectUnderTest() {

        return new ForcingImpl();
    }

    @Test
    public void testName() {

        testEqualContractForAttribute("name", "jane", "julie");
    }

    @Test
    public void testDescription() {

        testEqualContractForAttribute("description", "good", "bad");
    }

    @Test
    public void testValue() {

        testEqualContractForAttribute("value", "green", "red");
    }

    @Test
    public void testForcingType() {

        testEqualContractForAttribute("type", ForcingType.CARBON_SCALING, ForcingType.OZONE_FORCING);
    }
}
