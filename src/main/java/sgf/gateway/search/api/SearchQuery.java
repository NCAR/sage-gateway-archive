package sgf.gateway.search.api;

public interface SearchQuery {
    SearchResult execute(Criteria criteria);
}
