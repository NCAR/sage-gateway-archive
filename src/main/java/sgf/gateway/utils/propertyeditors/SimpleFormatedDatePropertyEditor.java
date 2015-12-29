package sgf.gateway.utils.propertyeditors;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleFormatedDatePropertyEditor extends PropertyEditorSupport {

    private final String format;
    private final boolean lenient;
    private boolean allowEmpty;

    public SimpleFormatedDatePropertyEditor(String format) {

        this.format = format;
        this.lenient = false;
        this.allowEmpty = false; //TODO default to allow null values or not?
    }

    public SimpleFormatedDatePropertyEditor(String format, boolean lenient, boolean allowEmpty) {

        this.format = format;
        this.lenient = lenient;
        this.allowEmpty = allowEmpty;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(this.format);
        simpleDateFormat.setLenient(this.lenient);

        Date date;

        if (this.allowEmpty && !StringUtils.hasText(text)) {
            this.setValue(null);
        } else {

            try {

                date = simpleDateFormat.parse(text);

            } catch (ParseException e) {

                throw new IllegalArgumentException(e);
            }

            this.setValue(date);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAsText() {

        Date value = (Date) super.getValue();

        String result = "";

        if (value != null) {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(this.format);
            simpleDateFormat.setLenient(this.lenient);

            result = simpleDateFormat.format(value);
        }

        return result;
    }
}
