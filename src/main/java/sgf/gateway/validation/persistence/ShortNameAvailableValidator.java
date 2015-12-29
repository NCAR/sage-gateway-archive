package sgf.gateway.validation.persistence;

import org.springframework.util.StringUtils;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.Dataset;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ShortNameAvailableValidator implements ConstraintValidator<ShortNameAvailable, String> {

    private final DatasetRepository datasetRepository;

    public ShortNameAvailableValidator(DatasetRepository datasetRepository) {

        this.datasetRepository = datasetRepository;
    }

    @Override
    public void initialize(ShortNameAvailable constraintAnnotation) {

    }

    @Override
    public boolean isValid(String datasetShortName, ConstraintValidatorContext context) {

        boolean valid = true;

        if (StringUtils.hasText(datasetShortName)) {

            Dataset dataset = this.datasetRepository.getByShortNameIgnoreCase(datasetShortName);

            if (dataset != null) {

                valid = false;
            }
        }

        return valid;
    }
}
