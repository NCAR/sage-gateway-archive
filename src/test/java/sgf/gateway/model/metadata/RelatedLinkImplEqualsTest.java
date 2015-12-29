package sgf.gateway.model.metadata;

import org.junit.Test;
import sgf.gateway.model.ModelEqualsMethodTest;

import java.net.URI;

public class RelatedLinkImplEqualsTest extends ModelEqualsMethodTest {

    public Object createObjectUnderTest() {

        return new RelatedLinkImpl();
    }

    @Test
    public void testText() {

        testEqualContractForAttribute("text", "thisText", "otherText");
    }

    @Test
    public void testLink() {

        testEqualContractForAttribute("uri", URI.create("http://this.link.com"), URI.create("http://other.link.com"));
    }
}
