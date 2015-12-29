package sgf.gateway.validation.spring;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public abstract class AbstractValidator implements Validator {

    private final Class supportsClass;

    public AbstractValidator(Class supportsClass) {

        this.supportsClass = supportsClass;
    }

    public boolean supports(Class clazz) {

        boolean value = this.supportsClass.equals(clazz);

        return value;
    }

    public void validate(Object target, Errors errors) {

        if (!errors.hasErrors()) {
            this.validateRequired(target, errors);
        }

        if (!errors.hasErrors()) {
            this.validateTypes(target, errors);
        }

        if (!errors.hasErrors()) {
            this.validateData(target, errors);
        }

        if (!errors.hasErrors()) {
            this.validatePersistentData(target, errors);
        }
    }

    /**
     * Validate that all the required fields have some sort of value in them.
     *
     * @param target
     * @param errors
     */
    public abstract void validateRequired(Object target, Errors errors);

    public abstract void validateTypes(Object target, Errors errors);

    public abstract void validateData(Object target, Errors errors);

    public abstract void validatePersistentData(Object target, Errors errors);
}
