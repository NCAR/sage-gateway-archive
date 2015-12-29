package sgf.gateway.search.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sgf.gateway.search.index.SearchDocument;
import sgf.gateway.search.service.indexer.SearchIndexer;

import java.util.Collection;
import java.util.Iterator;

public class SearchServiceImpl implements SearchService {

    private static final Log LOG = LogFactory.getLog(SearchService.class);

    private final SearchIndexer indexer;
    private final Collection<ReindexObjectSource> reindexObjectSources;

    public SearchServiceImpl(SearchIndexer indexer, Collection<ReindexObjectSource> reindexObjectSources) {
        super();
        this.indexer = indexer;
        this.reindexObjectSources = reindexObjectSources;
    }

    @Override
    public void index(Object object) {
        this.indexer.index(object);
        this.indexer.commit();
    }

    @Override
    public void index(Collection<Object> objects) {

        for (Object object : objects) {
            this.indexer.index(object);
        }

        this.indexer.commit();
    }

    @Override
    public void delete(Object object) {
        this.indexer.delete(object);
        this.indexer.commit();
    }

    @Override
    public void delete(Collection<Object> objects) {

        for (Object object : objects) {
            this.indexer.delete(object);
        }

        this.indexer.commit();
    }

    @Override
    public void delete(SearchDocument searchDocument) {

        LOG.info("Delete commencing...");
        this.indexer.delete(searchDocument);
        this.indexer.commit();
        LOG.info("Delete complete.");
    }

    @Override
    public void reindex() {

        this.indexer.purge();

        for (ReindexObjectSource source : this.reindexObjectSources) {
            this.index(source);
        }

        this.indexer.commit();
        this.indexer.optimize();
    }

    private void index(ReindexObjectSource source) {

        Iterator sourceIterator = source.getIterator();

        while (sourceIterator.hasNext()) {
            Object object = sourceIterator.next();
            this.indexer.index(object);
        }
    }
}
