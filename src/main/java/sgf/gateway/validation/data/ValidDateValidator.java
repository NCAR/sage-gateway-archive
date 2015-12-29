package sgf.gateway.validation.data;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ValidDateValidator implements ConstraintValidator<ValidDate, String> {

    private String format;

    @Override
    public void initialize(ValidDate annotation) {
        this.format = annotation.format();
    }

    @Override
    public boolean isValid(String inputDate, ConstraintValidatorContext constraintValidatorContext) {

        boolean valid = true;

        if (StringUtils.hasText(inputDate)) {

            SimpleDateFormat dateFormat = new SimpleDateFormat(format);

            dateFormat.setLenient(false);

            try {

                dateFormat.parse(inputDate);

            } catch (ParseException e) {

                valid = false;
            }
        }

        return valid;
    }
}
