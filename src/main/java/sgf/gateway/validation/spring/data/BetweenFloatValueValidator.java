package sgf.gateway.validation.spring.data;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BetweenFloatValueValidator implements Validator {

    private final String field;
    private final String errorCode;
    private final String defaultMessage;
    private final float minimumValue;
    private final float maximumValue;

    public BetweenFloatValueValidator(final String field, final String errorCode, final String defaultMessage, final float minimumValue, final float maximumValue) {

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

    @Deprecated
    @Override
    public void validate(Object target, Errors errors) {

        Float value = Float.valueOf((Float) errors.getFieldValue(this.field));

        if (value < this.minimumValue || value > this.maximumValue) {

            errors.reject(this.errorCode, this.defaultMessage);
        }
    }
}
