package sgf.gateway.model.metadata.activities.modeling;

import org.safehaus.uuid.UUID;

public enum ForcingType {

    /**
     * The OZON e_ forcing.
     */
    OZONE_FORCING,

    /**
     * The CARBO n_ scaling.
     */
    CARBON_SCALING,

    /**
     * The SOLA r_ forcing.
     */
    SOLAR_FORCING,

    /**
     * The GREE n_ hous e_ gases.
     */
    GREEN_HOUSE_GASES,

    /**
     * The VOLCAN o_ forcing.
     */
    VOLCANO_FORCING,

    /**
     * The SO x_ emissions.
     */
    SOX_EMISSIONS,

    /**
     * The WIN d_ forcing.
     */
    WIND_FORCING;


    /**
     * {@inheritDoc}
     */
    public String getName() {
        return this.name();
    }

    /**
     * {@inheritDoc}
     */
    public String getDescription() {
        return null;
    }

    public UUID getIdentifier() {
        throw new RuntimeException("UUID not available for objects of type ResolutionType");
    }


}
