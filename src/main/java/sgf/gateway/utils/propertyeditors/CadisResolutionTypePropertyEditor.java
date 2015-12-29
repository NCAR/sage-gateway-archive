package sgf.gateway.utils.propertyeditors;

import sgf.gateway.model.metadata.CadisResolutionType;

import java.beans.PropertyEditorSupport;

public class CadisResolutionTypePropertyEditor extends PropertyEditorSupport {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        if (0 == text.length()) {

            setValue(null);

        } else {

            setValue(CadisResolutionType.valueOf(text));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAsText() {

        CadisResolutionType cadisResolutionType = (CadisResolutionType) super.getValue();

        String result = "";

        if (null != cadisResolutionType) {

            result = cadisResolutionType.name();
        }

        return result;
    }
}
