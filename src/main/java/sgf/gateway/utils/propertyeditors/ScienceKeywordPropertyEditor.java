package sgf.gateway.utils.propertyeditors;

import org.springframework.util.StringUtils;
import sgf.gateway.dao.metadata.MetadataRepository;
import sgf.gateway.model.metadata.ScienceKeyword;
import sgf.gateway.service.metadata.DatasetNotFoundException;

import java.beans.PropertyEditorSupport;

public class ScienceKeywordPropertyEditor extends PropertyEditorSupport {

    private final MetadataRepository metadataRepository;

    public ScienceKeywordPropertyEditor(final MetadataRepository metadataRepository) {

        this.metadataRepository = metadataRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        if (StringUtils.hasText(text)) {

            ScienceKeyword keyword = this.metadataRepository.findScienceKeywordByDisplayText(text);

            if (keyword == null) {

                throw new DatasetNotFoundException(text);
            }

            setValue(keyword);

        } else {

            setValue(null);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAsText() {

        ScienceKeyword value = (ScienceKeyword) super.getValue();

        String result = "";

        if (value != null) {

            result = value.getTopic();
        }

        return result;
    }
}
