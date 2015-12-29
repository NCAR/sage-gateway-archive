package sgf.gateway.web.views.dataset;

import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;
import com.rometools.rome.feed.atom.Link;
import org.springframework.web.servlet.view.feed.AbstractAtomFeedView;
import sgf.gateway.model.Gateway;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.DatasetVersion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.*;

public class AtomDatasetVersionView extends AbstractAtomFeedView {

    private final Gateway gateway;

    public AtomDatasetVersionView(Gateway gateway) {

        this.gateway = gateway;
    }

    @Override
    protected void buildFeedMetadata(Map<String, Object> model, Feed feed, HttpServletRequest request) {

        super.buildFeedMetadata(model, feed, request);

        Dataset dataset = (Dataset) model.get("dataset");

        URI id = URI.create(this.gateway.getBaseURL() + "/dataset/" + dataset.getShortName() + "/version.atom");

        id = id.normalize();

        feed.setId(id.toString());

        feed.setTitle(dataset.getTitle() + " Version Feed");

        feed.setUpdated(dataset.getCurrentDatasetVersion().getDateCreated());
    }

    @Override
    protected List<Entry> buildFeedEntries(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Collection<DatasetVersion> datasetVersions = (Collection<DatasetVersion>) model.get("datasetVersions");

        List<Entry> entries = new ArrayList<Entry>();

        for (DatasetVersion datasetVersion : datasetVersions) {

            Entry entry = new Entry();

            entry.setId(datasetVersion.getVersionIdentifier());

            Date date = datasetVersion.getDateCreated();
            entry.setUpdated(date);

            entry.setTitle(datasetVersion.getVersionIdentifier());

            List<Link> alternateLinks = new ArrayList<Link>();

            entry.setAlternateLinks(alternateLinks);

            entries.add(entry);
        }

        return entries;
    }
}
