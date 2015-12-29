package sgf.gateway.utils.propertyeditors;

import org.junit.Before;
import org.junit.Test;
import sgf.gateway.model.security.GroupDataType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class GroupDataTypePropertyEditorTest {

    private GroupDataTypePropertyEditor propertyEditor;

    @Before
    public void setup() {

        this.propertyEditor = new GroupDataTypePropertyEditor();
    }

    @Test
    public void whenSetTextWithNullThenReturnNullObject() {

        propertyEditor.setAsText(null);
        assertThat(propertyEditor.getValue(), is(nullValue()));
    }

    @Test
    public void whenSetTextWithEmptyStringThenReturnNullObject() {

        propertyEditor.setAsText("");
        assertThat(propertyEditor.getValue(), is(nullValue()));
    }

    @Test
    public void whenSetTextWithOnlyWhitespaceStringThenReturnNullObject() {

        propertyEditor.setAsText(" ");
        assertThat(propertyEditor.getValue(), is(nullValue()));
    }

    @Test
    public void whenSetTextWithValidGroupDataTypeThenReturnGroupDataType() {

        propertyEditor.setAsText("SHORT_TEXT");

        GroupDataType returned = (GroupDataType) propertyEditor.getValue();
        assertThat(returned, is(GroupDataType.SHORT_TEXT));
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwExceptionWhenSetTextIsIllegalType() {

        propertyEditor.setAsText("Short Text");
    }

    @Test
    public void whenSetValueThenReturnText() {

        propertyEditor.setValue(GroupDataType.SHORT_TEXT);

        assertThat(propertyEditor.getAsText(), is("SHORT_TEXT"));
    }
}
