package sgf.gateway.model.metadata.activities.observing;

import org.junit.Test;
import sgf.gateway.model.ModelEqualsMethodTest;

public class PlatformTypeImplEqualsTest extends ModelEqualsMethodTest {

    public Object createObjectUnderTest() {

        return new PlatformTypeImpl();
    }

    @Test
    public void testShortName() {

        testEqualContractForAttribute("shortName", "thisShortName", "otherShortName");
    }

    @Test
    public void testLongName() {

        testEqualContractForAttribute("longName", "thisLongName", "otherLongName");
    }
}
