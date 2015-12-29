package sgf.gateway.validation.data;


import org.springframework.util.StringUtils;
import sgf.gateway.web.controllers.cadis.publish.command.CadisDatasetCommand;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DatasetShortNameCharactersValidator implements ConstraintValidator<DatasetShortNameCharacters, Object> {

    // Short name may contain the letters a-z, A-Z, numbers, and the characters: $ - _ . ! * ' ( ) ,
    // We are not allowing plus ("+") either.
    public static final String REGEX = "^[a-zA-Z0-9_\\.(),\\!\\$\\'\\*\\-]+$";

    @Override
    public void initialize(DatasetShortNameCharacters arg0) {

    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {

        boolean valid = false;

        CadisDatasetCommand cadisDatasetCommand = (CadisDatasetCommand) object;

        String datasetShortName = cadisDatasetCommand.getShortName();

        if (StringUtils.hasText(datasetShortName)) {

            if (datasetShortName.matches(REGEX)) {

                valid = true;
            }

        }

        return valid;
    }

}
