package sgf.gateway.validation.spring.persistence;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.Dataset;

public class DatasetExistsViaShortNameValidator implements Validator {

    private final String field;
    private final String errorCode;
    private final String defaultMessage;
    private final DatasetRepository datasetRepository;

    public DatasetExistsViaShortNameValidator(final String field, final String errorCode, final String defaultMessage, final DatasetRepository datasetRepository) {

        this.field = field;
        this.errorCode = errorCode;
        this.defaultMessage = defaultMessage;
        this.datasetRepository = datasetRepository;
    }

    @Override
    public boolean supports(Class<?> arg0) {

        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

        String shortName = (String) errors.getFieldValue(this.field);

        Dataset dataset = this.datasetRepository.getByShortName(shortName);

        if (dataset == null) {

            errors.reject(this.errorCode, this.defaultMessage);
        }
    }
}
