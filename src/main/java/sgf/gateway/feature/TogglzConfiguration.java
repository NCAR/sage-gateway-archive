package sgf.gateway.feature;

import org.togglz.core.Feature;
import org.togglz.core.manager.TogglzConfig;
import org.togglz.core.repository.StateRepository;
import org.togglz.core.user.UserProvider;

public class TogglzConfiguration implements TogglzConfig {

    private final StateRepository featureRepository;
    private final UserProvider userProvider;
    private final Class<? extends Feature> featureClass;

    public TogglzConfiguration(StateRepository featureRepository, UserProvider userProvider, Class<? extends Feature> featureClass) {
        super();
        this.featureRepository = featureRepository;
        this.userProvider = userProvider;
        this.featureClass = featureClass;
    }

    @Override
    public Class<? extends Feature> getFeatureClass() {
        return this.featureClass;
    }

    @Override
    public StateRepository getStateRepository() {
        return this.featureRepository;
    }

    @Override
    public UserProvider getUserProvider() {
        return this.userProvider;
    }

}