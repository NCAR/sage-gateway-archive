package sgf.gateway.service.security.impl.acegi;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.security.Operation;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.AuthorizationService;

import java.util.Collection;


/**
 * Superclass of @see {@link AccessDecisionVoter}s that can be used to enforce security on (ultimately) individual domain objects of type Resource. The specific
 * sub-class instances must be configured with the ConfigurationAttribute tied to the secured method invocation, the specific Resource subclass to be secured,
 * and the Operation to be authorized. The access control decision is delegated to the underlying AuthorizationService.
 */
public class AcegiAccessDecisionVoter implements AccessDecisionVoter<Object> {

    private static final Log LOG = LogFactory.getLog(AcegiAccessDecisionVoter.class);

    /**
     * The method configuration attribute that triggers the voting process
     */
    private ConfigAttribute configAttribute;

    /**
     * The underlying AuthorizationService to which the accesss control decision is delegated
     */
    private AuthorizationService authorizationService;

    /**
     * The Operation the user is seeking to perform on the secured resource
     */
    private Operation operation;

    /**
     * The specific implementation AcegiRuntimeUserServiceImpl which is used to extract the User object from the Authentication object in the security
     * context
     */
    private AcegiRuntimeUserServiceImpl acegiRuntimeUserServiceImpl;

    public AcegiAccessDecisionVoter(String configAttributeName) {
        this.configAttribute = new SecurityConfig(configAttributeName);
    }

    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {

        if (attributes.contains(configAttribute) && object instanceof Dataset) {

            Dataset dataset = (Dataset) object;

            User user = acegiRuntimeUserServiceImpl.getUser(authentication);

            return this.vote(user, dataset);

        } else {

            return AccessDecisionVoter.ACCESS_ABSTAIN;
        }

    }

    protected int vote(User user, Dataset dataset) {

        boolean authorized = authorizationService.authorize(user, dataset, operation);
        if (LOG.isDebugEnabled()) {
            LOG.debug("User=" + user.getUserName() + " resource=" + dataset.getIdentifier() + " operation=" + operation.getName() + " authorization="
                    + authorized + " Thread=" + Thread.currentThread().getId());
        }

        return (authorized ? AccessDecisionVoter.ACCESS_GRANTED : AccessDecisionVoter.ACCESS_DENIED);

    }

    public void setAcegiRuntimeUserServiceImpl(AcegiRuntimeUserServiceImpl acegiRuntimeUserServiceImpl) {
        this.acegiRuntimeUserServiceImpl = acegiRuntimeUserServiceImpl;
    }

    public ConfigAttribute getConfigAttribute() {
        return configAttribute;
    }

    public void setConfigAttribute(ConfigAttribute configAttribute) {
        this.configAttribute = configAttribute;
    }

    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        // TODO Auto-generated method stub
        return false;
    }

}
