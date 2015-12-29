package sgf.gateway.search.index;


public interface SearchIndex {
    void purge();

    void commit();

    void add(SearchDocument document);

    void delete(SearchDocument document);

    void optimize();
}
