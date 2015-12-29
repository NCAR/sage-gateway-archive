package sgf.gateway.validation.data;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidDecimalRangeValidator implements ConstraintValidator<ValidDecimalRange, String> {

    private double lower;
    private double upper;
    private RangeEndpointType lowerEndpointType;
    private RangeEndpointType upperEndpointType;

    @Override
    public void initialize(ValidDecimalRange annotation) {

        this.lower = transform(annotation.lower());
        this.upper = transform(annotation.upper());

        this.lowerEndpointType = annotation.lowerEndpointType();
        this.upperEndpointType = annotation.upperEndpointType();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        boolean valid = true;

        if (StringUtils.hasText(value)) {

            valid = isWithinRange(value);
        }

        return valid;
    }

    private boolean isWithinRange(String subjectAsString) {

        boolean valid = false;
        double subject = transform(subjectAsString);

        if (lowerEndpointType == RangeEndpointType.INCLUSIVE && upperEndpointType == RangeEndpointType.INCLUSIVE) {

            valid = (lower <= subject && subject <= upper);

        } else if (lowerEndpointType == RangeEndpointType.EXCLUSIVE && upperEndpointType == RangeEndpointType.EXCLUSIVE) {

            valid = (lower < subject && subject < upper);

        } else if (lowerEndpointType == RangeEndpointType.INCLUSIVE && upperEndpointType == RangeEndpointType.EXCLUSIVE) {

            valid = (lower <= subject && subject < upper);

        } else if (lowerEndpointType == RangeEndpointType.EXCLUSIVE && upperEndpointType == RangeEndpointType.INCLUSIVE) {

            valid = (lower < subject && subject <= upper);
        }

        return valid;
    }

    private double transform(String value) {

        return Double.parseDouble(value);
    }
}
