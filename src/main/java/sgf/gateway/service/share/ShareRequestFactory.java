package sgf.gateway.service.share;

import org.safehaus.uuid.UUID;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.Gateway;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.RelatedLink;
import sgf.gateway.model.metadata.citation.ResponsibleParty;
import sgf.gateway.service.share.jackson.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ShareRequestFactory {

    private Gateway gateway;


    public ShareRequestFactory(Gateway gateway) {
        this.gateway = gateway;
    }

    public ShareRequest createShareRequest (Dataset dataset){

        ShareRequest shareRequest = new ShareRequest();

        JsonData jsonData = populateJsonData(dataset);

        shareRequest.setJsonData(jsonData);

        return shareRequest;
    }

    // TODO: Set the rest of the values (map shareRequest to Dataset)
    private JsonData populateJsonData (Dataset dataset){

        JsonData jsonData = new JsonData();

        jsonData.setTitle(dataset.getTitle());
        jsonData.setDescription(dataset.getDescription());
        jsonData.setProviderUpdatedDateTime(dataset.getDateUpdated().toString());
        jsonData.setContributors(getContributorsFromResponsibleParties(dataset));
        jsonData.setUris(getUris(dataset));
        jsonData.setShareProperties(getShareProperties(dataset));

        return jsonData;
    }

    private Uris getUris (Dataset dataset){

        Uris uris = new Uris();

        uris.setCanonicalUri(getDatasetUri(dataset));

        // If the first providerUri matches an existing SHARE record, the dataset will be updated on the SHARE site.
        // Else it will be created.
        List<String> providers = new ArrayList ();
        providers.add(getDatasetUri(dataset));
        uris.setProviderUris(providers);

        return uris;
    }

    private List<Contributor> getContributorsFromResponsibleParties(Dataset dataset) {

        List<ResponsibleParty> responsibleParties = dataset.getDescriptiveMetadata().getResponsibleParties();

        List<Contributor> contributorList = new ArrayList<>();

        // Lambda?
        for (ResponsibleParty rp : responsibleParties) {

            if (rp.getIndividualName() != null) {

                Contributor contributor = new Contributor();

                contributor.setName(rp.getIndividualName());

                contributorList.add(contributor);
            }
        }

        return contributorList;
    }

    private String getDatasetUri(Dataset dataset) {

        return gateway.getBaseSecureURL() + "dataset/id/" + dataset.getIdentifier() + ".html";

    }

    //TODO: This will be related links but how to map a List to the shareProperties JSON?
    // Needed because Share people suddenly made this required.
    private ShareProperties getShareProperties(Dataset dataset) {

        String source = "";
        String docID = "";

        Collection<RelatedLink> relatedLinks = dataset.getDescriptiveMetadata().getRelatedLinks();
        List<RelatedLink> listOfLinks = new ArrayList(relatedLinks);

        if (listOfLinks.size() > 0) {
            RelatedLink relatedLink = listOfLinks.get(0);
            source = relatedLink.getUri().toString();
            docID = relatedLink.getText();
        }

        ShareProperties shareProperties = new ShareProperties();
        shareProperties.setSource(source);
        shareProperties.setDocID(docID);

        return shareProperties;
    }
}
