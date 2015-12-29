package sgf.gateway.model.metadata.activities.modeling;

import sgf.gateway.model.metadata.activities.Activity;

public interface Experiment extends Activity {

    /**
     * gets the CMIP5 short name.
     *
     * @return the short name
     */
    public String getShortName();

    /**
     * gets the CMIP5 experiment number, typically a string like "1.2"...
     *
     * @return
     */
    public String getExperimentNumber();

    /**
     * sets the CMIP5 short name.
     *
     * @param shortName the short name
     */
    public void setShortName(String shortName);

    /**
     * sets the CMIP5 experiment number.
     *
     * @param experimentName the experiment name
     */
    public void setExperimentNumber(String experimentName);
}
