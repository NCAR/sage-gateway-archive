package sgf.gateway.validation.spring.persistence;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import sgf.gateway.dao.metadata.PlatformTypeRepository;
import sgf.gateway.model.metadata.activities.observing.PlatformType;
import sgf.gateway.web.controllers.observing.PlatformTypeCommand;

public class PlatformTypeUniqueValidator implements Validator {

    private final String shortName;
    private final String errorCode;
    private final String defaultMessage;

    private final PlatformTypeRepository platformTypeRepository;

    public PlatformTypeUniqueValidator(String shortName, String errorCode, String defaultMessage, PlatformTypeRepository platformTypeRepository) {

        this.shortName = shortName;
        this.errorCode = errorCode;
        this.defaultMessage = defaultMessage;
        this.platformTypeRepository = platformTypeRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return PlatformTypeCommand.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        String platformTypeName = (String) errors.getFieldValue(this.shortName);

        PlatformType platform = this.platformTypeRepository.findByName(platformTypeName);

        if (platform != null) {

            errors.reject(errorCode, defaultMessage);
        }
    }

}
