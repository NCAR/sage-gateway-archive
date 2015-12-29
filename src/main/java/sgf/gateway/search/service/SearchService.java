package sgf.gateway.search.service;

import sgf.gateway.search.index.SearchDocument;

import java.util.Collection;

public interface SearchService {
    void index(Object object);

    void index(Collection<Object> objects);

    void delete(Object object);

    void delete(Collection<Object> objects);

    void delete(SearchDocument searchDocument);

    void reindex();
}
