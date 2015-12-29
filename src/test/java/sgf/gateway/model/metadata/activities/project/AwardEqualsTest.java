package sgf.gateway.model.metadata.activities.project;

import org.junit.Test;
import sgf.gateway.model.ModelEqualsMethodTest;

public class AwardEqualsTest extends ModelEqualsMethodTest {

    public Object createObjectUnderTest() {

        return new Award();
    }

    @Test
    public void testAwardNumber() {

        testEqualContractForAttribute("awardNumber", "thisAwardNumber", "otherAwardNumber");
    }
}
