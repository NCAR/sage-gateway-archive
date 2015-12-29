package sgf.gateway.validation.spring.data;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class GreaterThanFloatComparisonValidator implements Validator {

    private final String floatField1;
    private final String floatField2;
    private final String errorCode;
    private final String defaultMessage;

    public GreaterThanFloatComparisonValidator(final String floatField1, final String floatField2, final String errorCode, final String defaultMessage) {

        this.floatField1 = floatField1;
        this.floatField2 = floatField2;
        this.errorCode = errorCode;
        this.defaultMessage = defaultMessage;
    }

    @Override
    public boolean supports(Class<?> arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

        Float value1 = Float.valueOf((Float) errors.getFieldValue(this.floatField1));
        Float value2 = Float.valueOf((Float) errors.getFieldValue(this.floatField2));

        if (value1 <= value2) {

            errors.reject(this.errorCode, this.defaultMessage);
        }
    }
}
