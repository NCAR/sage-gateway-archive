package sgf.gateway.model.metadata.activities.observing;

import org.junit.Test;
import sgf.gateway.model.ModelEqualsMethodTest;

public class InstrumentKeywordImplEqualsTest extends ModelEqualsMethodTest {

    public Object createObjectUnderTest() {

        return new InstrumentKeywordImpl();
    }

    @Test
    public void testCategory() {

        testEqualContractForAttribute("category", "thisCategory", "otherCategory");
    }

    @Test
    public void testInstrumentClass() {

        testEqualContractForAttribute("instrumentClass", "thisInstrumentClass", "otherInstrumentClass");
    }

    @Test
    public void testType() {

        testEqualContractForAttribute("type", "thisType", "otherType");
    }

    @Test
    public void testSubtype() {

        testEqualContractForAttribute("subtype", "thisSubtype", "otherSubtype");
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
