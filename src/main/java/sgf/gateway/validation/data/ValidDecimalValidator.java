package sgf.gateway.validation.data;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidDecimalValidator implements ConstraintValidator<ValidDecimal, String> {

    @Override
    public void initialize(ValidDecimal annotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        boolean valid = true;

        if (StringUtils.hasText(value)) {

            try {
                Double.parseDouble(value);

            } catch (NumberFormatException e) {

                valid = false;
            }
        }

        return valid;
    }
}
