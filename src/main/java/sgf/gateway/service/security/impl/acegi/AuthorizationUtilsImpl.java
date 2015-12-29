package sgf.gateway.service.security.impl.acegi;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.security.Operation;
import sgf.gateway.service.security.AuthorizationUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Utility class that is simply used to trigger access control enforcement via AOP interception.
 */
public class AuthorizationUtilsImpl implements AuthorizationUtils {

    private final AccessDecisionVoter readDatasetVoter;

    private final AccessDecisionVoter writeDatasetVoter;

    public AuthorizationUtilsImpl(AccessDecisionVoter readDatasetVoter, AccessDecisionVoter writeDatasetVoter) {

        this.readDatasetVoter = readDatasetVoter;
        this.writeDatasetVoter = writeDatasetVoter;
    }

    public void authorizeForRead(final Dataset dataset) {

        ConfigAttribute configAttribute = new SecurityConfig("config_attribute_dataset_read");

        Collection<ConfigAttribute> configAttributes = new HashSet<ConfigAttribute>(1);
        configAttributes.add(configAttribute);

        List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<>();
        decisionVoters.add(this.readDatasetVoter);

        AffirmativeBased affirmativeBased = new AffirmativeBased(decisionVoters);

        try {

            affirmativeBased.decide(getAuthentication(), dataset, configAttributes);

        } catch (AccessDeniedException e) {

            throw new DatasetAccessDeniedException(dataset, Operation.READ, e);
        }
    }

    public void authorizeForWrite(Dataset dataset) {

        ConfigAttribute configAttribute = new SecurityConfig("config_attribute_dataset_write");
        Collection<ConfigAttribute> configAttributes = new HashSet<ConfigAttribute>(1);
        configAttributes.add(configAttribute);

        List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<>();
        decisionVoters.add(this.writeDatasetVoter);

        AffirmativeBased affirmativeBased = new AffirmativeBased(decisionVoters);

        try {

            affirmativeBased.decide(getAuthentication(), dataset, configAttributes);

        } catch (AccessDeniedException e) {

            throw new DatasetAccessDeniedException(dataset, Operation.WRITE, e);
        }
    }

    private Authentication getAuthentication() {

        SecurityContext securityContext = SecurityContextHolder.getContext();

        Authentication authentication = securityContext.getAuthentication();

        if (authentication == null) {

            throw new AuthenticationCredentialsNotFoundException("An Authentication object was not found in the SecurityContext");
        }

        return authentication;
    }
}
