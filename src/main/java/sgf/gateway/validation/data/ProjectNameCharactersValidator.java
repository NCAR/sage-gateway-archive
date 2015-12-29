package sgf.gateway.validation.data;

import org.springframework.util.StringUtils;
import sgf.gateway.web.controllers.project.command.ProjectCommand;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ProjectNameCharactersValidator implements ConstraintValidator<ProjectNameCharacters, Object> {

    // Project may contain alphanumeric, underbar, dot, dash, space, question mark, ()
    public static final String REGEX = "^[a-zA-Z0-9_\\.() ?\\-]+$";

    @Override
    public void initialize(ProjectNameCharacters annotation) {

    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {

        boolean valid = false;

        ProjectCommand projectCommand = (ProjectCommand) object;

        String title = projectCommand.getTitle();

        if (StringUtils.hasText(title)) {

            if (title.matches(REGEX)) {

                valid = true;
            }
        }

        return valid;
    }

}
