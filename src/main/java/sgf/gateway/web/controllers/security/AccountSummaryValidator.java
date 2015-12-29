package sgf.gateway.web.controllers.security;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class AccountSummaryValidator implements Validator {

    public boolean supports(Class clazz) {
        return AccountSummaryCommand.class.isAssignableFrom(clazz);
    }

    public void validate(Object command, Errors errors) {

        AccountSummaryCommand accountSummaryCommand = (AccountSummaryCommand) command;

        //First Name is a required field.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "field.required", "First Name is a required field");

        //Last Name is a required field.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "field.required", "Last Name is a required field");

        //Email is a required field.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "field.required", "Email is a required field");
    }
}
