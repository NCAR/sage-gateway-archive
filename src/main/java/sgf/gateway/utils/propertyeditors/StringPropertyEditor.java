package sgf.gateway.utils.propertyeditors;

import org.apache.commons.lang.StringUtils;
import sgf.gateway.utils.text.MicrosoftCharacterCleaner;

import java.beans.PropertyEditorSupport;

public class StringPropertyEditor extends PropertyEditorSupport {

    private final MicrosoftCharacterCleaner msCharacterCleaner;

    public StringPropertyEditor(MicrosoftCharacterCleaner cleaner) {

        this.msCharacterCleaner = cleaner;
    }

    @Override
    public void setAsText(String text) {

        if (StringUtils.isNotBlank(text)) {

            String value = this.msCharacterCleaner.clean(text.trim());

            this.setValue(value);

        } else {
            this.setValue(null);
        }
    }

    @Override
    public String getAsText() {

        Object value = getValue();

        String string = "";

        if (value != null) {

            string = value.toString();
        }

        return string;
    }
}
