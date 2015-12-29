package sgf.gateway.utils.propertyeditors;

import org.springframework.util.StringUtils;
import sgf.gateway.model.metadata.DataType;

import java.beans.PropertyEditorSupport;

public class DataTypePropertyEditor extends PropertyEditorSupport {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        if (StringUtils.hasText(text)) {

            setValue(DataType.valueOf(text));
        } else {

            setValue(null);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAsText() {

        DataType dataType = (DataType) super.getValue();

        String result = "";

        if (null != dataType) {

            result = dataType.getLongName();
        }

        return result;
    }
}
