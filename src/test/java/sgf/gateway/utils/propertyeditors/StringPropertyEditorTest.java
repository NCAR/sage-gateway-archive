package sgf.gateway.utils.propertyeditors;

import org.junit.Before;
import org.junit.Test;
import sgf.gateway.utils.text.MicrosoftCharacterCleaner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class StringPropertyEditorTest {

    private StringPropertyEditor editor;

    @Before
    public void setup() {

        editor = new StringPropertyEditor(new FakeMicrosoftCharacterCleaner());
    }

    public class FakeMicrosoftCharacterCleaner extends MicrosoftCharacterCleaner {

        public String clean(String text) {

            return text;
        }
    }

    @Test
    public void nullTextReturnsNullValue() {

        editor.setAsText(null);

        assertThat(editor.getValue(), equalTo(null));
    }

    @Test
    public void blankTextReturnsNullValue() {

        assertBlankStringIsNull("");
        assertBlankStringIsNull(" ");
        assertBlankStringIsNull("   ");
    }

    public void assertBlankStringIsNull(String blank) {

        editor.setAsText(blank);

        assertThat(editor.getValue(), equalTo(null));
    }

    @Test
    public void stringTextIsValue() {

        editor.setAsText("value");

        assertThat((String) editor.getValue(), equalTo("value"));
    }

    @Test
    public void stringTextIsTrimmed() {

        assertStringIsTrimmed("trimmed ");
        assertStringIsTrimmed(" trimmed");
        assertStringIsTrimmed(" trimmed ");
        assertStringIsTrimmed("  trimmed  ");
    }

    public void assertStringIsTrimmed(String string) {

        editor.setAsText(string);

        assertThat((String) editor.getValue(), equalTo((string.trim())));
    }

    @Test
    public void microsoftCharacterCleanerIsCalledForNonNullText() {

        MockMicrosoftCharacterCleaner cleaner = new MockMicrosoftCharacterCleaner();

        StringPropertyEditor localEditor = new StringPropertyEditor(cleaner);

        localEditor.setAsText("value");

        assertThat(cleaner.isCleanCalled(), equalTo(true));
    }

    @Test
    public void microsoftCharacterCleanerIsNotCalledForNullText() {

        MockMicrosoftCharacterCleaner cleaner = new MockMicrosoftCharacterCleaner();

        StringPropertyEditor localEditor = new StringPropertyEditor(cleaner);

        localEditor.setAsText(null);

        assertThat(cleaner.isCleanCalled(), equalTo(false));
    }

    @Test
    public void microsoftCharacterCleanerIsNotCalledForBlankText() {

        MockMicrosoftCharacterCleaner cleaner = new MockMicrosoftCharacterCleaner();

        StringPropertyEditor localEditor = new StringPropertyEditor(cleaner);

        localEditor.setAsText("");

        assertThat(cleaner.isCleanCalled(), equalTo(false));
    }

    public class MockMicrosoftCharacterCleaner extends MicrosoftCharacterCleaner {

        private boolean called = false;

        public String clean(String text) {

            this.called = true;

            return super.clean(text);
        }

        public boolean isCleanCalled() {

            return this.called;
        }
    }

    @Test
    public void nullValueReturnsBlankText() {

        editor.setValue(null);

        assertThat(editor.getAsText(), equalTo(""));
    }

    @Test
    public void stringValueReturnsValueText() {

        editor.setValue("value");

        assertThat(editor.getAsText(), equalTo("value"));
    }
}
