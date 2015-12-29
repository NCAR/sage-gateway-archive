package sgf.gateway.search.service.indexer;

import sgf.gateway.search.index.SearchDocument;


public interface SearchIndexer {
    void index(Object object);

    void delete(Object object);

    void delete(SearchDocument document);

    void commit();

    void optimize();

    void purge();
}
