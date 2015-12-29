package sgf.gateway.model.metadata.activities.observing;

import org.junit.Test;
import sgf.gateway.model.ModelEqualsMethodTest;

public class CampaignImplEqualsTest extends ModelEqualsMethodTest {

    public Object createObjectUnderTest() {

        return new CampaignImpl();
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
