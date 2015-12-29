package sgf.gateway.integration.thredds.transform;

import sgf.gateway.integration.node.Node;
import sgf.gateway.integration.thredds.ThreddsDatasetPayload;
import thredds.catalog.InvCatalogRef;
import thredds.catalog.InvDataset;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class NodeDatasetThreddsDatasetURIs implements Node<ThreddsDatasetPayload> {

    @Override
    public void process(ThreddsDatasetPayload payload) {

        InvDataset invDataset = payload.getInvDataset();
        List<URI> threddsDatasetURIs = extractThreddsDatasetURIs(invDataset);

        payload.setThreddsDatasetURIs(threddsDatasetURIs);
    }

    private List<URI> extractThreddsDatasetURIs(InvDataset invDataset) {

        List<URI> threddsDatasetURIs = new ArrayList<URI>();

        for (String threddsDatasetURL : extractDatasets(invDataset)) {
            threddsDatasetURIs.add(URI.create(threddsDatasetURL));
        }

        return threddsDatasetURIs;
    }

    /*
     * If the given InvDataset is from a "project-level" catalog, this method walks the chain of catalogRefs to identify and return the URIs of file-level catalogs which are used
     * to render the gateway's notion of a dataset.
     */
    private List<String> extractDatasets(InvDataset project) {

        // make sure we return non-null...
        List<String> fileDatasetUrls;
        fileDatasetUrls = processDatasets(project.getName(), project.getDatasets());
        return fileDatasetUrls;
    }

    private List<String> processDatasets(String projectName, List<InvDataset> datasets) {
        // make sure we return non-null...
        List<String> fileDatasetUrls = new ArrayList<String>();

        for (InvDataset dataset : datasets) {
            if (dataset instanceof InvCatalogRef) {
                InvCatalogRef catRef = (InvCatalogRef) dataset;
                String uri = catRef.getURI().toString();

                if (uri.contains(".dataset.")) {
                    /*
					 * NOTE: EOL catalogs originally were nested within ".dataset." catalogs down to a ".files." level catalog, which is what we were after. On 7/5/2012, EOL
					 * administrators suddenly and quietly announced these ".dataset." catalogs would no longer have file references. For now, we'll return the ".dataset." URI. Its
					 * unclear if it has everything we need, but we'll proceed this way until we know for certain.
					 * 
					 * List<InvDataset> fileRefs = dataset.getDatasets(); fileDatasetUrls.addAll(processFileRefs(fileRefs));
					 */
                    fileDatasetUrls.add(uri);
                }

                catRef.release(); // very important -- otherwise sits on memory!
            }
        }

        return fileDatasetUrls;
    }
}
