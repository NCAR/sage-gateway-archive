package sgf.gateway.validation.spring.data;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BetweenDoubleValueValidator implements Validator {

    private final String field;
    private final String errorCode;
    private final String defaultMessage;
    private final double minimumValue;
    private final double maximumValue;

    public BetweenDoubleValueValidator(final String field, final String errorCode, final String defaultMessage, final double minimumValue, final double maximumValue) {

        this.field = field;
        this.errorCode = errorCode;
        this.defaultMessage = defaultMessage;
        this.minimumValue = minimumValue;
        this.maximumValue = maximumValue;
    }

    @Override
    public boolean supports(Class<?> arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

        Double value = (Double) errors.getFieldValue(this.field);

        if (value < this.minimumValue || value > this.maximumValue) {

            errors.reject(this.errorCode, this.defaultMessage);
        }
    }
}
