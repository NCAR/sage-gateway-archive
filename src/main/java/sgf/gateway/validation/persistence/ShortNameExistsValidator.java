package sgf.gateway.validation.persistence;

import org.springframework.util.StringUtils;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.Dataset;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ShortNameExistsValidator implements ConstraintValidator<ShortNameExists, String> {

    private final DatasetRepository datasetRepository;

    public ShortNameExistsValidator(DatasetRepository datasetRepository) {

        this.datasetRepository = datasetRepository;
    }

    @Override
    public void initialize(ShortNameExists shortNameExists) {

    }

    @Override
    public boolean isValid(String datasetShortName, ConstraintValidatorContext constraintValidatorContext) {

        boolean valid = true;

        if (StringUtils.hasText(datasetShortName)) {

            Dataset dataset = this.datasetRepository.getByShortNameIgnoreCase(datasetShortName);

            if (dataset == null) {

                valid = false;
            }
        }

        return valid;
    }

}
