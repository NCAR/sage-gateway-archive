package sgf.gateway.integration.ade.opensearch.receiver;

import org.springframework.web.client.RestTemplate;
import sgf.gateway.integration.ade.opensearch.Feed;
import sgf.gateway.integration.ade.opensearch.Link;

import java.net.URI;

public class Reader {

    private final RestTemplate restTemplate;
    private final URI initialPage;

    private URI page;
    private Boolean continuePaging = true;

    public Reader(URI initialPage, RestTemplate restTemplate) {
        super();
        this.initialPage = initialPage;
        this.page = initialPage;
        this.restTemplate = restTemplate;
    }

    public Feed read() {

        Feed feed = this.read(page);
        this.update(feed);

        return feed;
    }

    private Feed read(URI page) {
        Feed feed = restTemplate.getForObject(page, Feed.class);
        return feed;
    }

    private void update(Feed feed) {

        Link nextLink = this.getNextLink(feed);

        if (nextLink == null) {
            this.continuePaging = false;
        } else {
            this.page = URI.create(nextLink.getHref());
        }
    }

    private Link getNextLink(Feed feed) {

        for (Link link : feed.getLinks()) {
            if (link.getRel().equalsIgnoreCase("next")) {
                return link;
            }
        }

        return null;
    }

    public Boolean continuePaging() {
        return this.continuePaging;
    }

    public void reset() {
        this.continuePaging = true;
        this.page = this.initialPage;
    }
}
