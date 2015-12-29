package sgf.gateway.utils.propertyeditors;

import sgf.gateway.model.metadata.DatasetProgress;

import java.beans.PropertyEditorSupport;

public class DatasetProgressPropertyEditor extends PropertyEditorSupport {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        if (0 == text.length()) {

            setValue(null);

        } else {

            setValue(DatasetProgress.valueOf(text));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAsText() {

        DatasetProgress datasetProgress = (DatasetProgress) super.getValue();

        String result = "";

        if (null != datasetProgress) {

            result = datasetProgress.name();
        }

        return result;
    }
}
