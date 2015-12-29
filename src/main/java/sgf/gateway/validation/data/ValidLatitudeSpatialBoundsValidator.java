package sgf.gateway.validation.data;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidLatitudeSpatialBoundsValidator implements ConstraintValidator<ValidLatitudeSpatialBounds, Object> {

    private String northernLatitudeField;
    private String southernLatitudeField;

    @Override
    public void initialize(ValidLatitudeSpatialBounds annotation) {

        this.northernLatitudeField = annotation.northernLatitudeField();
        this.southernLatitudeField = annotation.southernLatitudeField();
    }

    @Override
    public boolean isValid(Object subject, ConstraintValidatorContext context) {

        boolean valid = true;

        String northernLatitudeAsStr = getFieldValue(subject, northernLatitudeField);
        String southernLatitudeAsStr = getFieldValue(subject, southernLatitudeField);

        if (StringUtils.hasText(northernLatitudeAsStr) && StringUtils.hasText(southernLatitudeAsStr)) {

            try {

                double northernLatitude = Double.parseDouble(northernLatitudeAsStr);
                double southernLatitude = Double.parseDouble(southernLatitudeAsStr);

                if (southernLatitude > northernLatitude) {

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
