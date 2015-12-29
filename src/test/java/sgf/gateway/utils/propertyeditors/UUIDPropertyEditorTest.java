package sgf.gateway.utils.propertyeditors;

import org.junit.Before;
import org.junit.Test;
import org.safehaus.uuid.UUID;
import sgf.gateway.service.metadata.ObjectNotFoundException;

import java.beans.PropertyEditor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class UUIDPropertyEditorTest {

    private static final String NON_HEX_VALUES_UUID = "Invalid UUID";

    private static final String VALID_UUID = "2c5e2e70-cfef-11e3-9c1a-0800200c9a66";

    private UUIDPropertyEditor uuidPropertyEditor;

    @Before
    public void setup() {

        this.uuidPropertyEditor = new UUIDPropertyEditor();
    }

    @Test
    public void setAsTextWithNull() {

        this.uuidPropertyEditor.setAsText(null);

        assertNull(this.uuidPropertyEditor);
    }

    private void assertNull(PropertyEditor propertyEditor) {

        assertThat(null, is(propertyEditor.getValue()));
    }

    @Test
    public void setAsTextWithValidUUID() {

        this.uuidPropertyEditor.setAsText(VALID_UUID);

        assertThat(UUID.valueOf(VALID_UUID), is(this.uuidPropertyEditor.getValue()));
    }

    @Test
    public void setAsTextWithInvalidUUID() {

        assertObjectNotFoundException(NON_HEX_VALUES_UUID);
        assertObjectNotFoundException("2c5e2e70-cfef-11e3-9c1a-0800200c9a6g");
    }

    private void assertObjectNotFoundException(String text) {

        boolean exceptionOccured = false;

        try {

            this.uuidPropertyEditor.setAsText(text);

        } catch (ObjectNotFoundException e) {

            exceptionOccured = true;
        }

        assertThat("The following text did not cause a ObjectNotFoundException: " + text, true, is(exceptionOccured));
    }

    @Test
    public void setAsTextWithValidUUIDThensetAsTextWithNull() {

        this.uuidPropertyEditor.setAsText(VALID_UUID);
        this.uuidPropertyEditor.setAsText(null);

        assertNull(this.uuidPropertyEditor);
    }

    @Test
    public void getAsTextReturnsEmptyStringWithNull() {

        this.uuidPropertyEditor.setAsText(null);

        assertThat(this.uuidPropertyEditor.getAsText(), is(""));
    }
}
