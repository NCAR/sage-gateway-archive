package sgf.gateway.validation.spring.type;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.net.URI;

public class URITypeValidator implements Validator {

    private final String field;
    private final String errorCode;
    private final String defaultMessage;

    public URITypeValidator(final String field, final String errorCode, final String defaultMessage) {

        this.field = field;
        this.errorCode = errorCode;
        this.defaultMessage = defaultMessage;
    }

    @Override
    public boolean supports(Class<?> arg0) {

        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

        String stringURI = (String) errors.getFieldValue(this.field);

        try {

            URI uri = new URI(stringURI);

            if (uri.getScheme() == null) {

                errors.reject(this.errorCode, this.defaultMessage);

            } else if (uri.getHost() == null) {

                errors.reject(this.errorCode, this.defaultMessage);
            }

        } catch (Exception e) {

            errors.reject(this.errorCode, this.defaultMessage);
        }
    }

}
