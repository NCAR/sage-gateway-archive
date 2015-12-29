package sgf.gateway.validation.data;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidSpatialBoundsValidator implements ConstraintValidator<ValidSpatialBounds, Object> {

    private String northernLatitudeField;
    private String southernLatitudeField;
    private String westernLongitudeField;
    private String easternLongitudeField;

    @Override
    public void initialize(ValidSpatialBounds annotation) {
        this.northernLatitudeField = annotation.northernLatitudeField();
        this.southernLatitudeField = annotation.southernLatitudeField();
        this.westernLongitudeField = annotation.westernLongitudeField();
        this.easternLongitudeField = annotation.easternLongitudeField();
    }

    @Override
    public boolean isValid(Object subject, ConstraintValidatorContext context) {

        boolean valid = true;

        String northernLatitudeAsStr = getFieldValue(subject, northernLatitudeField);
        String southernLatitudeAsStr = getFieldValue(subject, southernLatitudeField);
        String westernLongitudeAsStr = getFieldValue(subject, westernLongitudeField);
        String easternLongitudeAsStr = getFieldValue(subject, easternLongitudeField);

        // if any of the values are specified, all values must be specified to be valid
        if (StringUtils.hasText(northernLatitudeAsStr) || StringUtils.hasText(southernLatitudeAsStr) ||
                StringUtils.hasText(westernLongitudeAsStr) || StringUtils.hasText(easternLongitudeAsStr)) {

            if (!(StringUtils.hasText(northernLatitudeAsStr) && StringUtils.hasText(southernLatitudeAsStr) &&
                    StringUtils.hasText(westernLongitudeAsStr) && StringUtils.hasText(easternLongitudeAsStr))) {
                valid = false;
            }
        }

        return valid;
    }

    private String getFieldValue(Object subject, String fieldName) {

        String value;

        try {
            value = BeanUtils.getProperty(subject, fieldName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return value;
    }
}
