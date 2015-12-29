package sgf.gateway.utils.propertyeditors;

import org.apache.commons.lang.StringUtils;
import sgf.gateway.model.security.GroupDataType;

import java.beans.PropertyEditorSupport;

public class GroupDataTypePropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        if (StringUtils.isNotBlank(text)) {

            setValue(GroupDataType.valueOf(text));

        } else {

            setValue(null);
        }
    }
}
