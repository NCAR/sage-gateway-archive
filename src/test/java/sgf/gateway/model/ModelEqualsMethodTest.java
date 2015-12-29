package sgf.gateway.model;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

public abstract class ModelEqualsMethodTest {

    private Object thisObject;
    private Object otherObject;

    public abstract Object createObjectUnderTest();

    public void setup() {

        thisObject = createObjectUnderTest();
        otherObject = createObjectUnderTest();
    }

    @Test
    public void testNullOtherObject() {

        Object object = createObjectUnderTest();
        assertThat(false, is(object.equals(null)));
    }

    @Test
    public void testSameInstance() {

        Object object = createObjectUnderTest();
        assertThat(true, is(object.equals(object)));
    }

    @Test
    public void testTwoInstances() {

        setup();
        assertObjectsEqual();
    }

    public void testEqualContractForAttribute(String attributeName, Object value, Object differentValue) {

        setup();
        testEqualAttributes(attributeName, value);
        setup();
        testUnequalAttributes(attributeName, value, differentValue);
        setup();
        testNullAttributeOnThis(attributeName, value);
        setup();
        testNullAttributeOnOther(attributeName, value);
    }

    private void testEqualAttributes(String attributeName, Object value) {

        setProperty(thisObject, attributeName, value);
        setProperty(otherObject, attributeName, value);

        assertObjectsEqual();
        assertHashCodesEqual();
    }

    private void testUnequalAttributes(String attributeName, Object value, Object differentValue) {

        setProperty(thisObject, attributeName, value);
        setProperty(otherObject, attributeName, differentValue);

        assertObjectsNotEqual();
        assertHashCodesNotEqual();
    }

    private void testNullAttributeOnThis(String attributeName, Object value) {

        setProperty(otherObject, attributeName, value);

        assertObjectsNotEqual();
        assertHashCodesNotEqual();
    }

    private void testNullAttributeOnOther(String attributeName, Object value) {

        setProperty(thisObject, attributeName, value);

        assertObjectsNotEqual();
        assertHashCodesNotEqual();
    }

    private void assertObjectsEqual() {

        assertThat(thisObject.equals(otherObject), is(true));
    }

    private void assertObjectsNotEqual() {

        assertThat(thisObject.equals(otherObject), is(false));
    }

    private void assertHashCodesEqual() {

        assertThat(otherObject.hashCode(), is(thisObject.hashCode()));
    }

    private void assertHashCodesNotEqual() {

        assertThat(otherObject.hashCode(), not(thisObject.hashCode()));
    }

    private void setProperty(Object object, String attributeName, Object attributeValue) {

        try {
            BeanUtils.setProperty(object, attributeName, attributeValue);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
