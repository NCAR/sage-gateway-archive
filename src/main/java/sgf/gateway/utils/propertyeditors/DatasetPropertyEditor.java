package sgf.gateway.utils.propertyeditors;

import org.springframework.util.StringUtils;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.service.metadata.DatasetNotFoundException;

import java.beans.PropertyEditorSupport;

public class DatasetPropertyEditor extends PropertyEditorSupport {

    private final DatasetRepository datasetRepository;

    public DatasetPropertyEditor(final DatasetRepository datasetRepository) {

        this.datasetRepository = datasetRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        if (StringUtils.hasText(text)) {

            Dataset dataset = this.datasetRepository.getByShortName(text);

            if (dataset == null) {

                throw new DatasetNotFoundException(text);
            }

            setValue(dataset);

        } else {

            setValue(null);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAsText() {

        Dataset value = (Dataset) super.getValue();

        String result = "";

        if (value != null) {

            result = value.getShortName();
        }

        return result;
    }
}
