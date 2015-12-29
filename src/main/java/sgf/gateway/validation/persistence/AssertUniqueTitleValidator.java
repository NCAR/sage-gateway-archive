package sgf.gateway.validation.persistence;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.NonUniqueResultException;
import org.safehaus.uuid.UUID;
import org.springframework.util.StringUtils;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.Dataset;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.util.List;


public class AssertUniqueTitleValidator implements ConstraintValidator<AssertUniqueTitle, Object> {

    private final DatasetRepository datasetRepository;

    private String nameField;
    private String identifierField;

    public AssertUniqueTitleValidator(final DatasetRepository datasetRepository) {

        this.datasetRepository = datasetRepository;
    }

    @Override
    public void initialize(AssertUniqueTitle annotation) {

        this.nameField = annotation.nameField();
        this.identifierField = annotation.identifierField();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {

        boolean valid = false;

        try {

            String projectName = BeanUtils.getProperty(object, this.nameField);

            List<Dataset> datasets = this.datasetRepository.getByTitleIgnoreCase(projectName);

            if (datasets.isEmpty()) {

                valid = true;

            } else {

                if (datasets.size() == 1) {

                    String identifierAsString = BeanUtils.getProperty(object, this.identifierField);

                    if (identifierAsString != null && StringUtils.hasText(identifierAsString)) {

                        UUID identifier = UUID.valueOf(identifierAsString);

                        if (identifier.equals(datasets.get(0).getIdentifier())) { // edit

                            valid = true;
                        }
                    }
                }
            }

        } catch (IllegalAccessException e) {

            throw new RuntimeException(e);

        } catch (InvocationTargetException e) {

            throw new RuntimeException(e);

        } catch (NoSuchMethodException e) {

            throw new RuntimeException(e);

        } catch (NonUniqueResultException e) {

            throw new RuntimeException(e);
        }

        return valid;
    }
}
