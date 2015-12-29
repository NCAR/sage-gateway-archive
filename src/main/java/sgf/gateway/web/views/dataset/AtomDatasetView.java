package sgf.gateway.web.views.dataset;

import com.rometools.rome.feed.atom.Content;
import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;
import com.rometools.rome.feed.atom.Link;
import org.springframework.web.servlet.view.feed.AbstractAtomFeedView;
import sgf.gateway.model.Gateway;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.utils.SanitizeHTML;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.*;

public class AtomDatasetView extends AbstractAtomFeedView {

    private final Gateway gateway;

    public AtomDatasetView(Gateway gateway) {

        this.gateway = gateway;
    }

    @Override
    protected void buildFeedMetadata(Map<String, Object> model, Feed feed, HttpServletRequest request) {

        super.buildFeedMetadata(model, feed, request);

        feed.setId(this.getId());

        feed.setTitle(this.gateway.getName() + " Dataset Update Feed");

        Collection<Dataset> datasets = (Collection<Dataset>) model.get("datasets");

        feed.setUpdated(this.getUpdated(datasets));

        Integer offset = (Integer) model.get("offset");
        Integer resultsPerPage = (Integer) model.get("resultsPerPage");
        Long totalDatasetCount = (Long) model.get("totalDatasetCount");

        feed.setOtherLinks(this.getOtherLinks(offset, resultsPerPage, totalDatasetCount));
    }

    public String getId() {

        URI id = URI.create(this.gateway.getBaseSecureURL() + "/dataset.atom");

        id = id.normalize();

        return id.toString();
    }

    public Date getUpdated(Collection<Dataset> datasets) {

        Date updated = null;

        for (Dataset dataset : datasets) {

            updated = dataset.getDateUpdated();
            break;
        }

        return updated;
    }

    public List<Link> getOtherLinks(Integer offset, Integer resultsPerPage, Long totalDatasetCount) {

        List<Link> links = new ArrayList<Link>();

        links.add(getSelfLink(offset, resultsPerPage));
        links.add(getFirstLink());

        if (offset < totalDatasetCount) {
            links.add(getNextLink(offset, resultsPerPage, totalDatasetCount));
        }

        if (offset >= resultsPerPage) {
            links.add(getPreviousLink(offset, resultsPerPage));
        }
        links.add(getLastLink(resultsPerPage, totalDatasetCount));

        return links;
    }

    public Link getSelfLink(Integer offset, Integer resultsPerPage) {

        Link self = new Link();
        self.setRel("self");
        self.setType("application/atom+xml");
        self.setHref(this.gateway.getBaseSecureURL() + "dataset.atom?offset=" + offset + "&rpp=" + resultsPerPage);

        return self;
    }

    public Link getFirstLink() {

        Link first = new Link();
        first.setRel("first");
        first.setType("application/atom+xml");
        first.setHref(this.gateway.getBaseSecureURL() + "dataset.atom");

        return first;
    }

    public Link getNextLink(Integer offset, Integer resultsPerPage, Long totalDatasetCount) {

        Link next = new Link();
        next.setRel("next");
        next.setType("application/atom+xml");
        next.setHref(this.gateway.getBaseSecureURL() + "dataset.atom?offset=" + (offset + resultsPerPage) + "&rpp=" + resultsPerPage);

        return next;
    }

    public Link getPreviousLink(Integer offset, Integer resultsPerPage) {

        Link previous = new Link();
        previous.setRel("previous");
        previous.setType("application/atom+xml");
        previous.setHref(this.gateway.getBaseSecureURL() + "dataset.atom?offset=" + (offset - resultsPerPage) + "&rpp=" + resultsPerPage);

        return previous;
    }

    public Link getLastLink(Integer resultsPerPage, Long totalDatasetCount) {

        Link last = new Link();
        last.setRel("last");
        last.setType("application/atom+xml");

        Integer lastOffset = (int) (Math.ceil(totalDatasetCount / resultsPerPage) * resultsPerPage);

        last.setHref(this.gateway.getBaseSecureURL() + "dataset.atom?offset=" + lastOffset + "&rpp=" + resultsPerPage);

        return last;
    }

    @Override
    protected List<Entry> buildFeedEntries(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Collection<Dataset> datasets = (Collection<Dataset>) model.get("datasets");

        List<Entry> entries = new ArrayList<Entry>();

        if (!datasets.isEmpty()) {

            for (Dataset dataset : datasets) {

                Entry entry = new Entry();

                URI id = URI.create(gateway.getBaseURL() + "/dataset/" + dataset.getShortName() + ".html");

                id = id.normalize();

                entry.setId(id.toString());
                entry.setTitle(dataset.getTitle());

                Date date = dataset.getDateUpdated();
                entry.setUpdated(date);


                Content summary = new Content();

                summary.setType(Content.TEXT);
                summary.setValue(SanitizeHTML.cleanHTML(dataset.getDescription()));

                entry.setSummary(summary);


                List<Link> alternateLinks = new ArrayList<Link>();

                Link htmlLink = new Link();

                URI htmlURI = URI.create(gateway.getBaseURL() + "/dataset/" + dataset.getShortName() + ".html");

                htmlURI = htmlURI.normalize();

                htmlLink.setHref(htmlURI.toString());
                htmlLink.setType("text/html");

                alternateLinks.add(htmlLink);

                entry.setAlternateLinks(alternateLinks);

                entries.add(entry);
            }
        }

        return entries;
    }
}
