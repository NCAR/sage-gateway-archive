package sgf.gateway.search.index;


public interface SearchDocument extends Iterable<SearchField> {
    void add(SearchField field);

    SearchField get(String name);
}
