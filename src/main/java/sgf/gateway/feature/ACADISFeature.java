package sgf.gateway.feature;

import org.togglz.core.Feature;
import org.togglz.core.annotation.Label;
import org.togglz.core.context.FeatureContext;

public enum ACADISFeature implements Feature {

    @Label("ADE Dataset Harvest")
    ADE_DATASET_HARVEST;

    public boolean isActive() {
        return FeatureContext.getFeatureManager().isActive(this);
    }
}