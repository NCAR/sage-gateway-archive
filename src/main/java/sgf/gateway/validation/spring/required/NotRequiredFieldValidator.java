package sgf.gateway.validation.spring.required;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class NotRequiredFieldValidator implements Validator {

    private final String field;
    private final Validator shieldedValidator;

    public NotRequiredFieldValidator(final String field, final Validator shieldedValidator) {
        super();
        this.field = field;
        this.shieldedValidator = shieldedValidator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

        String value = (String) errors.getFieldValue(this.field);

        if (StringUtils.hasText(value)) {
            this.shieldedValidator.validate(target, errors);
        }
    }
}
