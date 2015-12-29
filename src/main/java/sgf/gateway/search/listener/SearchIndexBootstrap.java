package sgf.gateway.search.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import sgf.gateway.search.api.SearchQuery;
import sgf.gateway.search.api.SearchResult;
import sgf.gateway.search.core.CriteriaImpl;
import sgf.gateway.search.service.SearchReindexer;

public class SearchIndexBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private static final Log LOG = LogFactory.getLog(SearchIndexBootstrap.class);

    private final SearchQuery searchQuery;
    private final SearchReindexer searchReindexer;

    public SearchIndexBootstrap(final SearchQuery searchQuery, final SearchReindexer searchReindexer) {
        super();
        this.searchQuery = searchQuery;
        this.searchReindexer = searchReindexer;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (isRootApplicationContext(event) && isSearchIndexEmpty()) {
            LOG.warn("Detected initial empty search index, reindexing search now.");
            this.searchReindexer.reindex();
        }
    }

    private Boolean isRootApplicationContext(ContextRefreshedEvent event) {

        Boolean isRoot = false;

        if (null == event.getApplicationContext().getParent()) {
            isRoot = true;
        }

        return isRoot;
    }

    private Boolean isSearchIndexEmpty() {

        Boolean isEmpty = false;

        SearchResult searchResult = this.searchQuery.execute(new CriteriaImpl());

        if (0 == searchResult.getResultCount()) {
            isEmpty = true;
        }

        return isEmpty;
    }
}
