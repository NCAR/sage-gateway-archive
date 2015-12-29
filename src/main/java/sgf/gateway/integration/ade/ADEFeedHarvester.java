package sgf.gateway.integration.ade;

import sgf.gateway.integration.ade.opensearch.Feed;

public interface ADEFeedHarvester {
    void harvest(Feed feed);
}
