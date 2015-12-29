package sgf.gateway.validation.data;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidLongitudeSpatialBoundsValidator implements ConstraintValidator<ValidLongitudeSpatialBounds, Object> {

    private String westernLongitudeField;
    private String easternLongitudeField;

    @Override
    public void initialize(ValidLongitudeSpatialBounds annotation) {

        this.westernLongitudeField = annotation.westernLongitudeField();
        this.easternLongitudeField = annotation.easternLongitudeField();
    }

    @Override
    public boolean isValid(Object subject, ConstraintValidatorContext context) {

        boolean valid = true;

        String westernLongitudeAsStr = getFieldValue(subject, westernLongitudeField);
        String easternLongitudeAsStr = getFieldValue(subject, easternLongitudeField);

        if (StringUtils.hasText(westernLongitudeAsStr) && StringUtils.hasText(easternLongitudeAsStr)) {

            try {

                double westernLongitude = Double.parseDouble(westernLongitudeAsStr);
                double easternLongitude = Double.parseDouble(easternLongitudeAsStr);

                if (westernLongitude > easternLongitude) {

                    valid = false;
                }

            } catch (NumberFormatException e) {

                // Nothing to do.
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
