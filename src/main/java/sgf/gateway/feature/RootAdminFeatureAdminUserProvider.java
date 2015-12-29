package sgf.gateway.feature;

import org.togglz.core.user.FeatureUser;
import org.togglz.core.user.SimpleFeatureUser;
import org.togglz.core.user.UserProvider;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.RuntimeUserService;

public class RootAdminFeatureAdminUserProvider implements UserProvider {

    private final RuntimeUserService userService;

    public RootAdminFeatureAdminUserProvider(RuntimeUserService userService) {
        this.userService = userService;
    }

    @Override
    public FeatureUser getCurrentUser() {

        FeatureUser featureUser = null;

        User user = userService.getUser();

        if (user != null) {
            featureUser = new SimpleFeatureUser(user.getUserName(), isUserRootAdmin(user));
        }

        return featureUser;
    }

    private Boolean isUserRootAdmin(User user) {
        return user.getUserName().equals("rootAdmin");
    }
}
