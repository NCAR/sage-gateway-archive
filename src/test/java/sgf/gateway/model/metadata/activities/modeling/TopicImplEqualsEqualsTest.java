package sgf.gateway.model.metadata.activities.modeling;

import org.junit.Test;
import sgf.gateway.model.ModelEqualsMethodTest;
import sgf.gateway.model.metadata.Taxonomy;
import sgf.gateway.model.metadata.TopicImpl;

public class TopicImplEqualsEqualsTest extends ModelEqualsMethodTest {

    public Object createObjectUnderTest() {

        return new TopicImpl();
    }

    @Test
    public void testName() {

        testEqualContractForAttribute("name", "walter", "hugo");
    }

    @Test
    public void testType() {

        testEqualContractForAttribute("type", Taxonomy.GCMD, Taxonomy.ISO);
    }
}
