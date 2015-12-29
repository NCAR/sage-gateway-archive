package sgf.gateway.model.metadata;

import org.junit.Test;
import sgf.gateway.model.ModelEqualsMethodTest;

public class LocationImplEqualsTest extends ModelEqualsMethodTest {

    public Object createObjectUnderTest() {

        return new LocationImpl();
    }

    @Test
    public void testName() {

        testEqualContractForAttribute("name", "thisName", "otherName");
    }

    @Test
    public void testDescription() {

        testEqualContractForAttribute("description", "thisDescription", "otherDescription");
    }

}
