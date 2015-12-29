package sgf.gateway.validation.spring.data;

import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import sgf.gateway.model.security.User;

import java.util.List;

public class ValidOpenidValidator implements Validator {

    private final String field;
    private final String errorCode;
    private final String defaultMessage;

    private final ConsumerManager consumerManager;

    public ValidOpenidValidator(final String field, final String errorCode, final String defaultMessage) throws ConsumerException {

        this.field = field;
        this.errorCode = errorCode;
        this.defaultMessage = defaultMessage;

        this.consumerManager = new ConsumerManager();
    }

    @Override
    public boolean supports(Class<?> arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

        String openid = (String) errors.getFieldValue(this.field);

        try {

            List<DiscoveryInformation> discoveries = this.consumerManager.discover(openid);

            for (DiscoveryInformation discoveryInformation : discoveries) {

                Identifier identifier = discoveryInformation.getClaimedIdentifier();
                openid = identifier.getIdentifier();
                break;
            }

            User user = (User) target;

            user.setOpenid(openid);

        } catch (DiscoveryException e) {

            errors.reject(errorCode, defaultMessage);
        }
    }
}
