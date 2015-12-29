package sgf.gateway.integration.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sgf.gateway.search.api.RemoteIndexable;
import sgf.gateway.search.service.SearchService;

public class RemoteIndexableLoadService {

    private final static Logger LOG = LoggerFactory.getLogger(RemoteIndexableLoadService.class);

    private final SearchService searchService;

    public RemoteIndexableLoadService(SearchService searchService) {
        super();
        this.searchService = searchService;
    }

    public void load(RemoteIndexable remote) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Datacenter:  " + remote.getDataCenter());
            LOG.debug("Identifier:  " + remote.getAuthoritativeIdentifier());
            LOG.debug("Source URI:  " + remote.getAuthoritativeSourceURI());
            LOG.debug("Details URI: " + remote.getDetailsURI());
            LOG.debug("Title:       " + remote.getTitle());
        }

        this.searchService.index(remote);
    }
}
