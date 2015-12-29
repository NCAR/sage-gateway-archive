package sgf.gateway.validation.spring.type;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleDateFormatTypeValidator implements Validator {

    private final String dateFormat;
    private final String field;
    private final String errorCode;
    private final String defaultMessage;

    public SimpleDateFormatTypeValidator(final String dateFormat, final String field, final String errorCode, final String defaultMessage) {

        this.dateFormat = dateFormat;
        this.field = field;
        this.errorCode = errorCode;
        this.defaultMessage = defaultMessage;
    }

    @Override
    public boolean supports(Class<?> clazz) {

        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

        String date = (String) errors.getFieldValue(this.field);

        try {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(this.dateFormat);
            simpleDateFormat.setLenient(false);

            Date parsedDate = simpleDateFormat.parse(date);

            // It is a bit strange, but we have to re-parse the parsedDate back to a string to see if the originally entered date equals the reparsed date.
            // Please see the SimpleDateFormatTypeValidatorTest.testInvalidDateWithLettersAtEnd() test to see the full issue.  NCH
            String parsedString = simpleDateFormat.format(parsedDate);

            if (!date.equals(parsedString)) {

                errors.reject(this.errorCode, this.defaultMessage);
            }

        } catch (ParseException e) {

            errors.reject(this.errorCode, this.defaultMessage);
        }
    }
}
