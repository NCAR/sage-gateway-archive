package sgf.gateway.validation.data;


import org.springframework.util.StringUtils;
import sgf.gateway.web.controllers.project.command.ProjectCommand;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ProjectShortNameCharactersValidator implements ConstraintValidator<ProjectShortNameCharacters, Object> {

    // Short name may contain the letters a-z, A-Z, numbers, and the characters: $ - _ . ! * ' ( ) ,
    // We are not allowing plus ("+") either.
    public static final String REGEX = "^[a-zA-Z0-9_\\.(),\\!\\$\\'\\*\\-]+$";

    @Override
    public void initialize(ProjectShortNameCharacters arg0) {

    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {

        boolean valid = false;

        ProjectCommand projectCommand = (ProjectCommand) object;

        String shortName = projectCommand.getShortName();

        if (StringUtils.hasText(shortName)) {

            if (shortName.matches(REGEX)) {

                valid = true;
            }

        }

        return valid;
    }

}
