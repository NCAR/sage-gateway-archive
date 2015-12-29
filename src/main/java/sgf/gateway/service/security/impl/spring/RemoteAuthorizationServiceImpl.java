package sgf.gateway.service.security.impl.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.Cacheable;
import sgf.gateway.dao.metadata.LogicalFileRepository;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.security.Operation;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.AccountService;
import sgf.gateway.service.security.AuthorizationService;
import sgf.gateway.service.security.RemoteAuthorizationService;

import java.net.URI;

public class RemoteAuthorizationServiceImpl implements RemoteAuthorizationService {

    private static final Log LOG = LogFactory.getLog(RemoteAuthorizationServiceImpl.class);

    private final AuthorizationService authorizationService;
    private final AccountService accountService;
    private final UserRepository userRepository;
    private final LogicalFileRepository logicalFileRepository;

    public RemoteAuthorizationServiceImpl(AuthorizationService authorizationService, AccountService accountService, UserRepository userRepository,
                                          LogicalFileRepository logicalFileRepository) {

        this.authorizationService = authorizationService;
        this.accountService = accountService;
        this.userRepository = userRepository;
        this.logicalFileRepository = logicalFileRepository;
    }

    @Cacheable(value = "remoteAuthorizationCache", key = "#openid.toString() + #resourceURL.substring(0, #resourceURL.lastIndexOf('/', #resourceURL.length())) + #operationName")
    public boolean authorize(final String openid, final String resourceURL, final String operationName) {

        boolean authorize = false; // deny authorization by default

        User user = this.getUser(openid);

        URI uri = URI.create(resourceURL);

        LogicalFile logicalFile = this.logicalFileRepository.findLogicalFileByAccessPointURL(uri);

        Operation operation = Operation.getOperationByName(operationName);

        if (user == null) {
            throw new IllegalArgumentException("User " + openid + " not found");
        }
        if (logicalFile == null) {
            throw new IllegalArgumentException("Resource " + resourceURL + " not found");
        }
        if (operation == null) {
            throw new IllegalArgumentException("Operation " + operationName + " not found");
        }

        // execute authorization invocation
        // TODO remove
        if ((user != null) && (logicalFile != null) && (operation != null)) {

            authorize = authorizationService.authorize(user, logicalFile.getDataset(), operation);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("openid: " + openid + " resourceURL: " + resourceURL + " operation: " + operationName + " authorization: " + authorize);
        }

        return authorize;
    }

    User getUser(String openId) {

        User user;

        user = this.userRepository.findUserByOpenid(openId);

        if (user == null) {

            user = this.accountService.registerRemoteUser(openId);
        }

        return user;
    }

}
