package sgf.gateway.integration.ade;

import sgf.gateway.integration.ade.opensearch.Entry;
import sgf.gateway.integration.ade.opensearch.Link;
import sgf.gateway.search.api.RemoteIndexable;

import java.net.URI;

public class ADEDatasetPayload implements RemoteIndexable {

    private final String dataCenter;
    private final Entry entry;

    public ADEDatasetPayload(String dataCenter, Entry entry) {
        super();
        this.dataCenter = dataCenter;
        this.entry = entry;
    }

    @Override
    public String getAuthoritativeIdentifier() {
        return entry.getDatasetId();
    }

    @Override
    public URI getAuthoritativeSourceURI() {
        URI sourceURI = this.extractLinkURI("describedBy", "application/atom+xml");
        return sourceURI;
    }

    @Override
    public URI getDetailsURI() {
        URI detailsURI = this.extractLinkURI("describedBy", "text/html");
        return detailsURI;
    }

    @Override
    public String getDataCenter() {
        return dataCenter;
    }

    @Override
    public String getTitle() {
        return entry.getTitle();
    }

    @Override
    public String getDescription() {
        return entry.getSummary();
    }

    private URI extractLinkURI(String rel, String type) {

        URI linkURI = null;

        for (Link link : this.entry.getLinks()) {
            if (link.getRel().equalsIgnoreCase(rel) && link.getType().equalsIgnoreCase(type)) {
                linkURI = URI.create(link.getHref());
            }
        }

        return linkURI;
    }
}
