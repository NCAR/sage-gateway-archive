package sgf.gateway.utils.propertyeditors;

import org.junit.Before;
import org.junit.Test;
import sgf.gateway.model.metadata.DataType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;


public class DataTypePropertyEditorTest {

    private DataTypePropertyEditor propertyEditor;

    @Before
    public void setup() {

        this.propertyEditor = new DataTypePropertyEditor();
    }

    @Test
    public void testSetAsTextWithNull() {

        propertyEditor.setAsText(null);
        assertThat(propertyEditor.getValue(), is(nullValue()));
    }

    @Test
    public void testSetAsTextEmptyString() {

        propertyEditor.setAsText("");
        assertThat(propertyEditor.getValue(), is(nullValue()));
    }

    @Test
    public void testSetAsTextValidDataType() {

        propertyEditor.setAsText("STATION");
        DataType returnedType = (DataType) propertyEditor.getValue();
        assertThat(returnedType, is(DataType.STATION));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetAsTextInvalidDataType() {

        propertyEditor.setAsText("Station");
    }

    @Test
    public void testGetAsText() {

        propertyEditor.setValue(DataType.IMAGE);
        assertThat(propertyEditor.getAsText(), is("Image"));
    }

    @Test
    public void testSetAsTextThenGetAsText() {

        propertyEditor.setAsText("IMAGE");
        assertThat(propertyEditor.getAsText(), is("Image"));
    }

}
