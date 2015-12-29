package sgf.gateway.model.metadata.activities.observing.factory;

import sgf.gateway.model.metadata.activities.observing.PlatformType;

public interface PlatformTypeFactory {

    /**
     * Creates a new PlatformType object.
     *
     * @param shortName the short name
     * @return the platform type
     */
    PlatformType create(String shortName);
}
