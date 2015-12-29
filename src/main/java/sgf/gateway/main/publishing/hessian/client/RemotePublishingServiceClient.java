package sgf.gateway.main.publishing.hessian.client;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sgf.gateway.model.metadata.PublishedState;
import sgf.gateway.service.publishing.api.RemotePublishingService;

/**
 * An example of a remote publishing client.
 */
public class RemotePublishingServiceClient {

    private RemotePublishingService remotePublishingService;

    // private final ClassPathResource catalogResource = new ClassPathResource("sgf.gateway/service/publishing/impl/spring/thredds_catalogs/one.dataset.thredds.xml");
    // private final String uri = "http://datagrid.ucar.edu/metadata/cgd/ccsm/thredds_new/ucar.cgd.ccsm.b30.004/ucar.cgd.ccsm.b30.004.thredds";
    private String uri = "http://tds.ucar.edu/thredds/esgcet/6/narccap.crcm.ccsm-current.table1.v1.xml";
    // private final String uri = "http://datagrid.ucar.edu/metadata/cgd/ccsm/thredds_new/ucar.cgd.ccsm.b30.004/pcmdi.ipcc.output.test.thredds.xml";

    // private final String uri = "http://pcmdi3.llnl.gov/thredds/esgcet/2/pcmdi.ipcc4.gfdl_cm2_0.20c3m.run1.monthly.xml";
    // private final String datasetId = "pcmdi.ipcc4.gfdl_cm2_0.20c3m.run1.monthly";
    // private final String parentDatasetId = "pcmdi.ipcc4.gfdl_cm2_0";

    // private final String uri = "http://datagrid.ucar.edu/metadata/cgd/ccsm/thredds_new/ucar.cgd.ccsm.b30.004/ucar.cgd.ccsm.b30.005.lnd.hist_small.thredds";
    private String datasetId = "ucar.cgd.ccsm.b30.009.atm.hist.monthly_ave";
    private String parentDatasetId = "ucar.cgd.ccsm.b30.004";

    // private final String uri = "http://tds.prototype.ucar.edu/thredds/esgcet/1/ucar.cgd.pcm.B07.57a.atm.hist.monthly.xml";
    // private final String datasetId = "ucar.cgd.pcm.B07.57a.atm.hist.monthly";
    // private final String parentDatasetId = "ucar.cgd.pcm.B07.57a";

    // private final String uri = "http://dataportal.ucar.edu/luca/jpl.airs.test3.xml";
    // private final String datasetId = "jpl.airs.test";
    // private final String parentDatasetId = "ucar.cgd.ccsm.output";

    private int recurseLevel = -1;
    private String changeMessage = "Publishing Dataset";

    private static final Log LOG = LogFactory.getLog(RemotePublishingServiceClient.class);

    public RemotePublishingServiceClient(RemotePublishingService remotePublishingService) {

        this.remotePublishingService = remotePublishingService;
    }

    public void createDataset() throws InterruptedException {

        //String id = service.createDataset(parentDatasetId, uri, recurseLevel, publishedState.getName());
        //String id = service.createDataset("ucar.mmm.amps.wrf_30", "http://tds.ucar.edu/thredds/esgcet/68/ucar.mmm.amps.wrf_30.201502.v1.xml", recurseLevel, publishedState.getName());
        this.createDataset("ucar.mmm.amps.wrf_30", "http://tds.ucar.edu/thredds/esgcet/68/ucar.mmm.amps.wrf_30.201502.v1.xml");
    }

    public void createDataset(String parentDatasetShortName, String datasetThreddsUri) {

        this.createDataset(parentDatasetShortName, datasetThreddsUri, -1, PublishedState.PUBLISHED);
    }

    public void createDataset(String parentDatasetShortName, String datasetThreddsUri, Integer recurseLevel, PublishedState initialPublishedState) {
        String id = remotePublishingService.createDataset(parentDatasetShortName, datasetThreddsUri, recurseLevel, initialPublishedState.getValue());
        System.out.println(id);
    }

    public void updateDataset() throws InterruptedException {

        throw new NotImplementedException();
    }

    public void deleteDataset() throws InterruptedException {
        //remotePublishingService.deleteDataset("ucar.mmm.amps.wrf_30.201301", true, changeMessage);
        remotePublishingService.deleteDataset("nmme.output1.UM-RSMAS.CCSM4.20150201.mon.atmos", true, changeMessage);
    }

    public void retractDataset() throws InterruptedException {
        remotePublishingService.retractDataset("narccap.crcm.ccsm-current.table1", changeMessage);
    }
}
