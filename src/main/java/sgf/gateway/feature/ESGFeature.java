package sgf.gateway.feature;

import org.togglz.core.Feature;
import org.togglz.core.annotation.Label;
import org.togglz.core.context.FeatureContext;

public enum ESGFeature implements Feature {

    @Label("Place Holder")
    PLACE_HOLDER;

    public boolean isActive() {
        return FeatureContext.getFeatureManager().isActive(this);
    }
}