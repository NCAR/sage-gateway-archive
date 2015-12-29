package sgf.gateway.validation.data;

import org.apache.commons.beanutils.PropertyUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class ValidTemporalBoundsValidatorDates implements ConstraintValidator<ValidTemporalBoundsDates, Object> {

    private String startDateField;
    private String endDateField;

    @Override
    public void initialize(ValidTemporalBoundsDates annotation) {

        this.startDateField = annotation.startDateField();
        this.endDateField = annotation.endDateField();
    }

    @Override
    public boolean isValid(Object subject, ConstraintValidatorContext constraintValidatorContext) {

        Date startDateValue = getFieldValue(subject, startDateField);
        Date endDateValue = getFieldValue(subject, endDateField);

        boolean valid = true;

        if ((null != startDateValue) && (null != endDateValue)) {

            valid = startDateValue.before(endDateValue);
        }

        return valid;
    }

    private Date getFieldValue(Object subject, String fieldName) {

        Date ovalue;

        try {

            ovalue = (Date) PropertyUtils.getProperty(subject, fieldName);

        } catch (Exception e) {

            throw new RuntimeException(e);
        }

        return ovalue;
    }

}
