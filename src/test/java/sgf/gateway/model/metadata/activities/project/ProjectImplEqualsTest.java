package sgf.gateway.model.metadata.activities.project;

import org.junit.Test;
import sgf.gateway.model.ModelEqualsMethodTest;

import java.net.URI;

public class ProjectImplEqualsTest extends ModelEqualsMethodTest {

    public Object createObjectUnderTest() {

        return new ProjectImpl();
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
    public void testPersistentIdentifier() {

        testEqualContractForAttribute("persistentIdentifier", "thisPersistentIdentifier", "otherPersistentIdentifier");
    }

    @Test
    public void testProjectURI() {

        testEqualContractForAttribute("projectURI", URI.create("http://this.project.uri"), URI.create("http://other.project.uri"));
    }
}
