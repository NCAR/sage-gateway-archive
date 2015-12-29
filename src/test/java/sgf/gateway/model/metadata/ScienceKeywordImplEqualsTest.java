package sgf.gateway.model.metadata;

import org.junit.Test;
import sgf.gateway.model.ModelEqualsMethodTest;

public class ScienceKeywordImplEqualsTest extends ModelEqualsMethodTest {

    public Object createObjectUnderTest() {

        return new ScienceKeywordImpl();
    }

    @Test
    public void testCategory() {

        testEqualContractForAttribute("category", "thisCategory", "otherCategory");
    }

    @Test
    public void testTopic() {

        testEqualContractForAttribute("topic", "thisTopic", "otherTopic");
    }

    @Test
    public void testTerm() {

        testEqualContractForAttribute("term", "thisTerm", "otherTerm");
    }

    @Test
    public void testVariableLevel1() {

        testEqualContractForAttribute("variableLevel1", "thisVariableLevel1", "otherVariableLevel1");
    }

    @Test
    public void testVariableLevel2() {

        testEqualContractForAttribute("variableLevel2", "thisVariableLevel2", "otherVariableLevel2");
    }

    @Test
    public void testVariableLevel3() {

        testEqualContractForAttribute("variableLevel3", "thisVariableLevel3", "otherVariableLevel3");
    }

    @Test
    public void testVariableLevel4() {

        testEqualContractForAttribute("variableLevel4", "thisVariableLevel4", "otherVariableLevel4");
    }
}
