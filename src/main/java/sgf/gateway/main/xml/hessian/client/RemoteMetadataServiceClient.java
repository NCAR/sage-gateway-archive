package sgf.gateway.main.xml.hessian.client;

import sgf.gateway.service.xml.api.RemoteMetadataService;

/**
 * Superclass of RemoteMetadataService clients.
 */
public class RemoteMetadataServiceClient {

    private static final String DATASET_ID = "org.nsf.aon.cadis.Ocean_and_Sea_Ice.Beaufort_Gyre_System.Beaufort_Gyre_Exploration_Project_-_Mooring_A__2003-2004";

    /**
     * A proxy to a {@link RemoteMetadataService}.
     */
    private RemoteMetadataService remoteMetadataService;

    // private static final String DATASET_ID = "narccap.crcm.ncep.table1.sic.files";
    // private static final String DATASET_ID = "pcmdi.ipcc4.gfdl_cm2_0.20c3m.run1.monthly.cl.files";

    public RemoteMetadataServiceClient(RemoteMetadataService proxy) {
        remoteMetadataService = proxy;
    }

    /**
     * Method to execute a set of standard calls to exercise the RemoteMetadataService functionality.
     */
    public void execute() throws Exception {

        // retrieve dataset hierarchy
        String xml = remoteMetadataService.getDatasetHierarchy(DATASET_ID);
        print(xml);

        // retrieve dataset metadata
        xml = remoteMetadataService.getDatasetMetadata(DATASET_ID);
        print(xml);

        // retrieve dataset files
        xml = remoteMetadataService.getDatasetFiles(DATASET_ID);
        print(xml);

        // retrieve all experiments
        xml = remoteMetadataService.listExperiments();
        print(xml);
    }

    /**
     * Utility method to print out a well formatted XML document.
     *
     * @param xml
     * @throws Exception
     */
    private void print(String xml) throws Exception {

        System.out.println(xml);
    }

}
