package sgf.gateway.validation.spring.data;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class GreaterThanOrEqualDoubleComparisonValidator implements Validator {

    private final String doubleField1;
    private final String doubleField2;
    private final String errorCode;
    private final String defaultMessage;

    public GreaterThanOrEqualDoubleComparisonValidator(final String doubleField1, final String doubleField2, final String errorCode, final String defaultMessage) {

        this.doubleField1 = doubleField1;
        this.doubleField2 = doubleField2;
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

        Double value1 = (Double) errors.getFieldValue(this.doubleField1);
        Double value2 = (Double) errors.getFieldValue(this.doubleField2);

        if (value1 < value2) {

            errors.reject(this.errorCode, this.defaultMessage);
        }
    }
}
