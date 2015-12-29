package sgf.gateway.integration.thredds;

import java.net.URI;
import java.util.List;

public class ThreddsDatasetChildrenPayload {

    private List<URI> threddsDatasetURIs;
    private String parentDatasetShortName;
    private String dataCenterName;

    public List<URI> getThreddsDatasetURIs() {
        return this.threddsDatasetURIs;
    }

    public void setThreddsDatasetURIs(List<URI> threddsDatasetURIs) {
        this.threddsDatasetURIs = threddsDatasetURIs;
    }

    public String getParentDatasetShortName() {
        return this.parentDatasetShortName;
    }

    public void setParentDatasetShortName(String shortName) {
        this.parentDatasetShortName = shortName;
    }

    public String getDataCenterName() {
        return dataCenterName;
    }

    public void setDataCenterName(String dataCenterName) {
        this.dataCenterName = dataCenterName;
    }
}
