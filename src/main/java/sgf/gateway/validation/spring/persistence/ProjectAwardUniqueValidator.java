package sgf.gateway.validation.spring.persistence;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.activities.project.Award;
import sgf.gateway.web.controllers.project.command.NewAwardCommand;

import java.util.Collection;

public class ProjectAwardUniqueValidator implements Validator {

    private final String awardField;
    private final String errorCode;
    private final String defaultMessage;

    public ProjectAwardUniqueValidator(String awardField, String errorCode, String defaultMessage) {

        this.awardField = awardField;
        this.errorCode = errorCode;
        this.defaultMessage = defaultMessage;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return NewAwardCommand.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object command, Errors errors) {

        NewAwardCommand awardCommand = (NewAwardCommand) command;
        Dataset dataset = awardCommand.getDataset();

        String awardNumber = (String) errors.getFieldValue(this.awardField);

        Collection<Award> awards = dataset.getAwards();

        for (Award award : awards) {
            if (awardNumber.equalsIgnoreCase(award.getAwardNumber())) {
                errors.reject(this.errorCode, this.defaultMessage);
            }
        }
    }

}
