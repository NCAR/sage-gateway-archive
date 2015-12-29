package sgf.gateway.validation.persistence;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.safehaus.uuid.UUID;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.Dataset;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;

public class AssertUniqueShortNameValidator implements ConstraintValidator<AssertUniqueShortName, Object> {

    private final DatasetRepository datasetRepository;

    private String shortNameField;
    private String identifierField;

    public AssertUniqueShortNameValidator(DatasetRepository datasetRepository) {

        this.datasetRepository = datasetRepository;
    }

    @Override
    public void initialize(AssertUniqueShortName annotation) {

        this.shortNameField = annotation.shortNameField();
        this.identifierField = annotation.identifierField();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {

        boolean valid = false;

        try {

            String datasetShortName = BeanUtils.getProperty(object, this.shortNameField);

            Dataset dataset = this.datasetRepository.getByShortNameIgnoreCase(datasetShortName);

            if (dataset == null) {

                valid = true;

            } else {

                String identifierAsString = BeanUtils.getProperty(object, this.identifierField);

                if (!StringUtils.isBlank(identifierAsString)) {

                    UUID identifier = UUID.valueOf(identifierAsString);

                    if (identifier.equals(dataset.getIdentifier())) {

                        valid = true;
                    }
                }
            }

        } catch (IllegalAccessException e) {

            throw new RuntimeException(e);

        } catch (InvocationTargetException e) {

            throw new RuntimeException(e);

        } catch (NoSuchMethodException e) {

            throw new RuntimeException(e);
        }

        return valid;
    }
}
