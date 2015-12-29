package sgf.gateway.validation.data;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidTemporalBoundsValidator implements ConstraintValidator<ValidTemporalBounds, Object> {

    private String startDateField;
    private String endDateField;
    private String format;

    @Override
    public void initialize(ValidTemporalBounds annotation) {

        this.startDateField = annotation.startDateField();
        this.endDateField = annotation.endDateField();
        this.format = annotation.format();
    }

    @Override
    public boolean isValid(Object subject, ConstraintValidatorContext constraintValidatorContext) {

        String startDateValue = getFieldValue(subject, startDateField);
        String endDateValue = getFieldValue(subject, endDateField);

        boolean valid = true;

        if (StringUtils.hasText(startDateValue) && StringUtils.hasText(endDateValue)) {

            try {

                Date startDate = transformDate(startDateValue);
                Date endDate = transformDate(endDateValue);

                valid = startDate.before(endDate) || startDate.equals(endDate);

            } catch (ParseException e) {

                // nothing to do.
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

    private Date transformDate(String inputDate) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        Date outputDate = dateFormat.parse(inputDate);

        return outputDate;
    }
}
