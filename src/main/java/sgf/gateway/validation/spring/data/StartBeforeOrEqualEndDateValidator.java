package sgf.gateway.validation.spring.data;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StartBeforeOrEqualEndDateValidator implements Validator {

    private final String dateField1;
    private final String dateField2;
    private final String dateFormat;
    private final String errorCode;
    private final String defaultMessage;

    public StartBeforeOrEqualEndDateValidator(String datefield1, String datefield2, String dateFormat, String errorCode, String defaultMessage) {

        this.dateField1 = datefield1;
        this.dateField2 = datefield2;
        this.dateFormat = dateFormat;
        this.errorCode = errorCode;
        this.defaultMessage = defaultMessage;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

        String start = (String) errors.getFieldValue(this.dateField1);
        String end = (String) errors.getFieldValue(this.dateField2);

        // No need to compare if 1 is empty
        if (StringUtils.hasText(start) && StringUtils.hasText(end)) {
            try {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(this.dateFormat);
                simpleDateFormat.setLenient(false);

                Date startDate = simpleDateFormat.parse(start);
                Date endDate = simpleDateFormat.parse(end);

                boolean test = startDate.before(endDate) || startDate.equals(endDate);

                if (!test) {

                    errors.reject(this.errorCode, this.defaultMessage);
                }
            } catch (ParseException e) {

                errors.reject(this.errorCode, this.defaultMessage);
            }
        }
    }
}
