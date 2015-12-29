package sgf.gateway.web.views.project;

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

public class AtomProjectDatasetView extends AbstractAtomFeedView {

    private final Gateway gateway;

    public AtomProjectDatasetView(Gateway gateway) {

        this.gateway = gateway;
    }

    @Override
    protected void buildFeedMetadata(Map<String, Object> model, Feed feed, HttpServletRequest request) {

        super.buildFeedMetadata(model, feed, request);

        Dataset dataset = (Dataset) model.get("projectDataset");

        URI id = URI.create(this.gateway.getBaseURL() + "/project/" + dataset.getShortName() + "/dataset.atom");

        id = id.normalize();

        feed.setId(id.toString());

        feed.setUpdated(dataset.getDateUpdated());
    }

    @Override
    protected List<Entry> buildFeedEntries(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {

        Dataset dataset = (Dataset) model.get("projectDataset");

        Collection<Dataset> datasets = dataset.getDirectlyNestedDatasets();

        List<Entry> entries = new ArrayList<Entry>();

        for (Dataset childDataset : datasets) {

            Entry entry = new Entry();

            URI id = URI.create(gateway.getBaseURL() + "/dataset/" + childDataset.getShortName() + ".html");

            id = id.normalize();

            entry.setId(id.toString());
            entry.setTitle(childDataset.getTitle());

            Date date = childDataset.getDateUpdated();
            entry.setUpdated(date);


            Content summary = new Content();

            summary.setType(Content.TEXT);
            summary.setValue(SanitizeHTML.cleanHTML(childDataset.getDescription()));

            entry.setSummary(summary);


            List<Link> alternateLinks = new ArrayList<Link>();

            Link htmlLink = new Link();
            htmlLink.setHref(id.toString());
            htmlLink.setType("text/html");

            alternateLinks.add(htmlLink);

            entry.setAlternateLinks(alternateLinks);

            entries.add(entry);
        }

        return entries;
    }
}
