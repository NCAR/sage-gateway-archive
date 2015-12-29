package sgf.gateway.validation.spring;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;

public class DefaultValidator extends AbstractValidator {

    private List<Validator> requiredValidators;

    private List<Validator> typeValidators;

    private List<Validator> dataValidators;

    private List<Validator> persistentDataValidators;

    public DefaultValidator(final Class supportsClass) {

        super(supportsClass);

        this.requiredValidators = new ArrayList<>();
        this.typeValidators = new ArrayList<>();
        this.dataValidators = new ArrayList<>();
        this.persistentDataValidators = new ArrayList<>();
    }

    @Override
    public void validateRequired(Object target, Errors errors) {

        for (Validator validator : this.requiredValidators) {

            validator.validate(target, errors);
        }
    }

    @Override
    public void validateTypes(Object target, Errors errors) {

        for (Validator validator : this.typeValidators) {

            validator.validate(target, errors);
        }
    }

    @Override
    public void validateData(Object target, Errors errors) {

        for (Validator validator : this.dataValidators) {

            validator.validate(target, errors);
        }
    }

    @Override
    public void validatePersistentData(Object target, Errors errors) {

        for (Validator validator : this.persistentDataValidators) {

            validator.validate(target, errors);
        }
    }

    public void setRequiredValidators(List<Validator> requiredValidators) {

        this.requiredValidators = requiredValidators;
    }

    public void setTypeValidators(List<Validator> typeValidators) {

        this.typeValidators = typeValidators;
    }

    public void setDataValidators(List<Validator> dataValidators) {

        this.dataValidators = dataValidators;
    }

    public void setPersistentDataValidators(List<Validator> dataValidators) {

        this.persistentDataValidators = dataValidators;
    }
}
