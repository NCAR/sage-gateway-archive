package sgf.gateway.validation.persistence;

import org.springframework.util.StringUtils;
import sgf.gateway.dao.metadata.PlatformTypeRepository;
import sgf.gateway.model.metadata.activities.observing.PlatformType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class AssertUniquePlatformTypeValidator implements ConstraintValidator<AssertUniquePlatformType, String> {

    private final PlatformTypeRepository platformTypeRepository;

    // The PlatformTypeRepository is set via Spring's Dependency Injection.  Please see the validator-context.xml file.
    public AssertUniquePlatformTypeValidator(final PlatformTypeRepository platformTypeRepository) {

        this.platformTypeRepository = platformTypeRepository;
    }

    @Override
    public void initialize(AssertUniquePlatformType assertUniquePlatformType) {

    }

    @Override
    public boolean isValid(String shortName, ConstraintValidatorContext constraintValidatorContext) {

        boolean valid = false;

        if (StringUtils.hasText(shortName)) {

            PlatformType platformType = this.platformTypeRepository.findByName(shortName);

            if (platformType == null) {

                valid = true;
            }

        } else {

            valid = true;
        }

        return valid;
    }

}
