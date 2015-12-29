package sgf.gateway.validation.persistence;

import org.springframework.util.StringUtils;
import sgf.gateway.dao.metadata.ProjectRepository;
import sgf.gateway.model.metadata.activities.project.Project;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ActivityProjectNameUniqueValidator implements ConstraintValidator<ActivityProjectNameUnique, String> {

    private final ProjectRepository projectRepository;

    public ActivityProjectNameUniqueValidator(ProjectRepository projectRepository) {

        this.projectRepository = projectRepository;
    }

    @Override
    public void initialize(ActivityProjectNameUnique constraintAnnotation) {

    }

    @Override
    public boolean isValid(String projectName, ConstraintValidatorContext context) {

        boolean value = false;

        if (StringUtils.hasText(projectName)) {

            Project project = this.projectRepository.findProjectByName(projectName);

            if (project == null) {

                value = true;
            }
        }

        return value;
    }
}
