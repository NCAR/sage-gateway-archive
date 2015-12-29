package sgf.gateway.model.metadata.activities.modeling;

import org.junit.Test;
import sgf.gateway.model.ModelEqualsMethodTest;

public class ExperimentImplEqualsTest extends ModelEqualsMethodTest {

    public Object createObjectUnderTest() {

        return new ExperimentImpl();
    }

    @Test
    public void testName() {

        testEqualContractForAttribute("name", "thisName", "otherName");
    }

    @Test
    public void testDescription() {

        testEqualContractForAttribute("description", "thisDescription", "otherDescription");
    }

    @Test
    public void testShortName() {

        testEqualContractForAttribute("shortName", "thisShortName", "otherShortName");
    }

    @Test
    public void testExperimentNumber() {

        testEqualContractForAttribute("experimentNumber", "thisExperimentNumber", "otherExperimentNumber");
    }
}
