package sgf.gateway.validation.type;

import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.net.URI;

public class ValidURIValidator implements ConstraintValidator<ValidURI, String> {

    @Override
    public void initialize(ValidURI validURI) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext2) {

        boolean valid = false;

        if (StringUtils.isEmpty(value)) {

            valid = true;

        } else {

            try {

                valid = this.validateUri(value);

            } catch (IllegalArgumentException e) {

            }
        }

        return valid;
    }

    private boolean validateUri(String value) {

        boolean valid = false;

        URI uri = URI.create(value);

        if (hasSchemaAndHost(uri)) {

            valid = true;
        }

        return valid;
    }

    private boolean hasSchemaAndHost(URI uri) {

        boolean test = false;

        String schema = uri.getScheme();
        String host = uri.getHost();

        if (StringUtils.isNotBlank(schema) && StringUtils.isNotBlank(host)) {

            test = true;
        }

        return test;
    }
}
